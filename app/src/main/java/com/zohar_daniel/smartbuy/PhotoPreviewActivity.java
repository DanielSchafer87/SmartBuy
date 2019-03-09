package com.zohar_daniel.smartbuy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.HandlerThread;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.zohar_daniel.smartbuy.Models.ShoppingListItem;
import com.zohar_daniel.smartbuy.Services.Constants;
import com.zohar_daniel.smartbuy.Services.DatabaseHelper;
import com.zohar_daniel.smartbuy.Services.GetXmlItems;
import com.zohar_daniel.smartbuy.Services.ShoppingListsSchema;
import com.zohar_daniel.smartbuy.Threads.UIHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class PhotoPreviewActivity extends AppCompatActivity {

    static final int REQUEST_TAKE_PHOTO = 100;
    String mCurrentPhotoPath;
    ImageView imageView;
    ArrayList<String> photosPaths = new ArrayList<>();
    ArrayList<String> barcodes = new ArrayList<>();
    Object syncLock = new Object();
    int threadsCompleteCounter = 0;
    String storeAndChainCode = "";
    long newListID;
    UIHandler uiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storeAndChainCode = getIntent().getStringExtra(Constants.CHAIN_AND_STORE_CODE);
        newListID = getIntent().getLongExtra(Constants.LIST_ID,0);
        setContentView(R.layout.activity_photo_preview);
        imageView = findViewById(R.id.imageView);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        dispatchTakePictureIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                //do something if permission granted.
            }
        }
    }

    public void TakePhotoOrDone(View view)
    {
        Intent intent = null;

        switch(view.getId()) {
            case R.id.btnTakeAnotherPhoto:
                dispatchTakePictureIntent();
                break;
            case R.id.btnDone:
                threadsCompleteCounter = 0;
                HandlerThread uiThread = new HandlerThread("UIHandler");
                uiThread.start();
                uiHandler = new UIHandler(uiThread.getLooper(),PhotoPreviewActivity.this);
                handleUIRequest(Constants.DISPLAY_CREATELIST_PROGRESS_BAR);
                for (String path : photosPaths) {
                    AnalyzePhotos(path);
                }
                break;
        }

        if(intent !=null)
            startActivity(intent);
    }

    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        photosPaths.add(mCurrentPhotoPath);
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            final Bitmap photo = BitmapFactory.decodeFile(mCurrentPhotoPath);
            imageView.setImageBitmap(photo);

            /*
            //FOR DEBBUG ONLY (includes rects around recognized barcodes)
            FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(photo);

            FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

            detector.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                        @Override
                        public void onSuccess(FirebaseVisionText firebaseVisionText) {
                            // Task completed successfully
                            ArrayList<Rect> rects = ExtractProductCodes(firebaseVisionText);
                            DrawOnImage(photo, imageView, rects);
                        }
                    })
                    .addOnFailureListener(
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Task failed with an exception
                                    // ...
                                }
                            });
            */
        }
    }

    private void DoneAnalyzingPhotos(){
        GetXmlItems xmlItems = new GetXmlItems();
        Intent intent = null;
        ArrayList<String> storeChainCode = new ArrayList<>();
        storeChainCode.add(storeAndChainCode);

        for(String photo: photosPaths){
            File f = new File(photo);
            f.delete();
        }

        photosPaths.clear();

        if(barcodes.size() == 0){
            new AlertDialog.Builder(PhotoPreviewActivity.this)
                    .setTitle("לא נמצאו ברקודים")
                    .setMessage("אנא צלם שוב את הקבלה")
                    .setNeutralButton("צלם שוב", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            imageView.setImageDrawable(null);
                        }
                    }).show();
            handleUIRequest(Constants.HIDE_CREATELIST_PROGRESS_BAR);
            return;
        }
        else {
            try {
                ShoppingListItem[] items = xmlItems.execute(storeChainCode, barcodes).get();
                DatabaseHelper h = new DatabaseHelper(getApplicationContext(), ShoppingListsSchema.databaseName, null, 1);
                for (ShoppingListItem item : items) {
                    item.setListId(newListID);
                    h.addItem(item);
                }
                intent = new Intent(this, ShoppingListActivity.class);
                intent.putExtra(Constants.LIST_ID, newListID);
                startActivity(intent);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        handleUIRequest(Constants.HIDE_CREATELIST_PROGRESS_BAR);
    }

    private void AnalyzePhotos(String photoPath){
        final Bitmap photo = BitmapFactory.decodeFile(photoPath);

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(photo);

        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        detector.processImage(image)
                .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
                    @Override
                    public void onSuccess(FirebaseVisionText firebaseVisionText) {
                        // Task completed successfully
                        ExtractProductCodes(firebaseVisionText);
                        threadsCompleteCounter++;
                        if(threadsCompleteCounter == photosPaths.size()){
                            DoneAnalyzingPhotos();
                        }
                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                            }
                        });
    }

    private ArrayList<Rect> ExtractProductCodes(FirebaseVisionText result){
        ArrayList<Rect> rects = new ArrayList<>();
        int minimum = Integer.MIN_VALUE;
        int maximum = 0;

        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
            for (FirebaseVisionText.Line line : block.getLines()) {
                minimum = Integer.MIN_VALUE;
                for (int i =0; i < line.getElements().size(); i++) {
                    String elementText = line.getElements().get(i).getText();
                    if(elementText.length() >= minimum){
                        minimum = elementText.length();
                        maximum = i;
                    }
                }
                String text = line.getElements().get(maximum).getText();
                Rect frame = line.getElements().get(maximum).getBoundingBox();
                if(TextUtils.isDigitsOnly(text)){
                    synchronized(syncLock) {
                        if(text.length() >= Constants.MIN_BARCODE_SIZE && text.length() < Constants.MAX_BARCODE_SIZE){
                            int diff = Constants.MAX_BARCODE_SIZE - text.length();
                            String barcodeBegin = "729";
                            for(int i=0; i < (diff-barcodeBegin.length()); i++){
                                text = "0"+text;
                            }
                            text = barcodeBegin+text;
                        }

                        if (!barcodes.contains(text)) {
                            barcodes.add(text);
                        }
                        rects.add(frame);
                    }
                }
            }
        }

        return rects;
    }

    protected void handleUIRequest(int message)
    {
        Message msg = null;

        switch (message){
            case Constants.DISPLAY_CREATELIST_PROGRESS_BAR:{
                msg = uiHandler.obtainMessage(message);
                break;
            }
            case Constants.HIDE_CREATELIST_PROGRESS_BAR:{
                msg = uiHandler.obtainMessage(message);
                break;
            }
        }

        if(msg != null)
            uiHandler.sendMessage(msg);
    }

    public void DrawOnImage(Bitmap photo, ImageView imageView, ArrayList<Rect> rects){
        //Create a new image bitmap and attach a brand new canvas to it
        Bitmap tempBitmap = Bitmap.createBitmap(photo.getWidth(), photo.getHeight(), Bitmap.Config.RGB_565);
        Canvas tempCanvas = new Canvas(tempBitmap);

        //Draw the image bitmap into the cavas
        tempCanvas.drawBitmap(photo, 0, 0, null);

        Paint myPaint = new Paint();
        myPaint.setColor(Color.rgb(0, 255, 0));
        myPaint.setStrokeWidth(3);
        myPaint.setStyle(Paint.Style.STROKE);

        for (Rect rect : rects) {
            tempCanvas.drawRect(rect,myPaint);
        }

        imageView.setImageDrawable(new BitmapDrawable(getResources(), tempBitmap));
    }

    @Override
    public void onBackPressed() {
        //TODO delete the list.
    }

}
