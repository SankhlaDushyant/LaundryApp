package xyz.dushyant31.laundroapp.authenticate;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class roleModel {

    public String Role;
    public String CompanyName;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public roleModel() {
    }

    public roleModel(String Role, String CompanyName)
    {
        this.Role = Role;
        this.CompanyName = CompanyName;

    }
}
