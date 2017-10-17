package xyz.dushyant31.laundroapp.Washer;

/**
 * Created by dushman on 10/10/17.
 */
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class WasherModel {

    public String Status;
    public String MachineId;
    public String UserId;
    public String Weigth;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public WasherModel() {
    }

    public WasherModel(String MachineId,String Status,String UserId) {
        this.MachineId = MachineId;
        this.Status = Status;
        this.UserId = UserId;
    }
    public WasherModel(String Status) {
        this.Status = Status;
    }

}


