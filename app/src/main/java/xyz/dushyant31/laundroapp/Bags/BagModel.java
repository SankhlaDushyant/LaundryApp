package xyz.dushyant31.laundroapp.Bags;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class BagModel {

    public String Color;
    public String Weight;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public BagModel() {
    }

    public BagModel(String Color, String Weight) {
        this.Color = Color;
        this.Weight = Weight;
    }
}
