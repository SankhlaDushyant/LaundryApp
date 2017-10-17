package xyz.dushyant31.laundroapp.Deliveryapp;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class UIDModel {

    public String Userid;



    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public UIDModel() {
    }

    public UIDModel(String Userid) {
        this.Userid = Userid;

    }
}
