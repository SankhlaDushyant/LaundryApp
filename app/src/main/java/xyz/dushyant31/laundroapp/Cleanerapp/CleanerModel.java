package xyz.dushyant31.laundroapp.Cleanerapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class CleanerModel {

    public String Date;
    public String DueDate;
    public String Noofbags;
    public String Weigth;
    public String SameDay;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public CleanerModel() {
    }

    public CleanerModel(String Date, String DueDate, String Noofbags,String SameDay, String Weigth) {
        this.Date = Date;
        this.DueDate = DueDate;
        this.Noofbags = Noofbags;
        this.Weigth = Weigth;
        this.SameDay = SameDay;

    }
}
