package xyz.dushyant31.laundroapp;


import android.graphics.Bitmap;

public class MyDataModel {

    private String CompanyName;

    private String Date;

    private Bitmap Qrcode;



    public String getName() {
        return Date;
    }

    public void setName(String Date) {
        this.Date = Date;
    }

    public String getId() {
        return CompanyName;
    }

    public void setId(String CompanyName) {
        this.CompanyName=CompanyName;
    }

    public Bitmap getQrcode() {
        return Qrcode;
    }

    public void setQrcode(Bitmap Qrcode) {
        this.Qrcode=Qrcode;
    }



}