package com.zohar_daniel.smartbuy;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceData {

    String storeName;
    String branchName;
    String date;
    BigDecimal invoiceAmount;

    public InvoiceData(String storeName, String branchName, String date, BigDecimal invoiceAmount) {
        this.storeName=storeName;
        this.branchName=branchName;
        this.date=date;
        this.invoiceAmount=invoiceAmount;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

}
