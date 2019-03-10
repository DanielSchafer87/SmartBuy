package com.zohar_daniel.smartbuy.Services;

import android.util.SparseIntArray;
import android.view.Surface;

public class Constants {

    //Clicked list in ListsActivity
    public final static String LIST_ID = "listID";

    //Chain and store codes that are passed to PhotoPreviewActivity from CreateListActivity.
    public final static String CHAIN_AND_STORE_CODE = "chainAndStoreCode";
    public final static String NEW_LIST_ID = "newListID";

    //Chains codes
    public final static String RAMILEVI = "7290058140886";
    public final static String SHUFERSAL = "7290027600007";

    public final static int MAX_BARCODE_SIZE = 13;
    public final static int MIN_BARCODE_SIZE = 3;

    public final static int DISPLAY_CREATELIST_PROGRESS_BAR = 1;
    public final static int HIDE_CREATELIST_PROGRESS_BAR = 0;
    public final static int DISPLAY_LOADING_ITEMS_PROGRESS_BAR = 2;
    public final static int HIDE_LOADING_ITEMS_PROGRESS_BAR = 3;


    public static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }
}
