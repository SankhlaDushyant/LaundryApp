package xyz.dushyant31.laundroapp.authenticate;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class RoledefineModel {

    public String Role;


    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public RoledefineModel() {
    }

    public RoledefineModel(String Role)
    {
        this.Role = Role;

    }
}
