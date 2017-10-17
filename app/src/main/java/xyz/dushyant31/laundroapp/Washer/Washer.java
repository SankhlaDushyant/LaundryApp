package xyz.dushyant31.laundroapp.Washer;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import xyz.dushyant31.laundroapp.Bags.BagDetails;
import xyz.dushyant31.laundroapp.Deliveryapp.Driver;
import xyz.dushyant31.laundroapp.R;
import xyz.dushyant31.laundroapp.authenticate.roleModel;

public class Washer extends AppCompatActivity {

    private static final String TAG = Washer.class.getSimpleName();

    private Button Update,Scan,OrderCom;
    private EditText TickitId, Mid, BagId, Status;

    String scannedData;
    private String TickitIdValue, MachineNumber, BagIDValue, StatusValue;

    private int count = 0; // available globally
    private int totalweigth;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseUser currentFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.washer);
        final Activity activity =this;

        TickitId = (EditText) findViewById(R.id.Ticketid);
        Mid = (EditText) findViewById(R.id.Machineid);
        BagId = (EditText) findViewById(R.id.Bagid);
        Status = (EditText) findViewById(R.id.cycle);

        Update = (Button) findViewById(R.id.update_btn);
        Scan = (Button) findViewById(R.id.scanbag);
        OrderCom = (Button) findViewById(R.id.ordercomp);

        OrderCom.setVisibility(View.GONE);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Getrole(currentFirebaseUser.getUid());

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setBeepEnabled(false);
                integrator.setCameraId(0);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });


        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TickitIdValue = TickitId.getText().toString();
                MachineNumber = Mid.getText().toString();
                BagIDValue = BagId.getText().toString();
                StatusValue = Status.getText().toString();

                if (StatusValue.equals("C")) {
                    IncrementCounter();
                }

                IncrementCounterWeigth(totalweigth);

                UpdateDatabase(TickitIdValue,BagIDValue,MachineNumber,StatusValue);
            }
        });

        OrderCom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TickitIdValue = TickitId.getText().toString();
                BagIDValue = BagId.getText().toString();
                StatusValue = Status.getText().toString();
                UpdateOrder(TickitIdValue,BagIDValue,StatusValue);
            }
        });


    }

    public void Getrole(String userid){

        mFirebaseDatabase.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                roleModel role = dataSnapshot.getValue(roleModel.class);

                //Log.i(TAG,"helooo--------->"+driver.Date);

//                TickitId.setText(TickitIdValue);

                if(role.Role.equals("Folder")) {
                    OrderCom.setVisibility(View.VISIBLE);
                }
                else{
                    OrderCom.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            scannedData = result.getContents();
            final String[] namesList = scannedData.split(",");
            TickitId.setText(namesList[0]);
            BagId.setText(namesList[3]);

            totalweigth = Integer.parseInt(namesList[4]);


        }
    }


    private void UpdateDatabase(String TickitIdValue,String BagIDValue,String MachineNumber,String StatusValue) {

        WasherModel status = new WasherModel(MachineNumber,StatusValue,currentFirebaseUser.getUid());

        mFirebaseDatabase.child("Cycle").child(TickitIdValue).child(BagIDValue).setValue(status);
    }
    private void UpdateOrder(String TickitIdValue,String BagIDValue,String StatusValue) {

        WasherModel status = new WasherModel(StatusValue);

        mFirebaseDatabase.child("Delivery").child(TickitIdValue).child(BagIDValue).setValue(status);

    }
    public void IncrementCounter() {
        mFirebaseDatabase.child("Delivery").child("RushDelivery").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    currentData.setValue((Long) currentData.getValue() + 1);
                }

                return Transaction.success(currentData);
            }


            public void onComplete(DatabaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (firebaseError != null) {
                    Log.e("poop", "Firebase counter increment failed.");
                } else {
                    Log.e("poop", "Firebase counter increment succeeded.");
                }
            }
        });

    }

    public void IncrementCounterWeigth(final int weigth) {
        mFirebaseDatabase.child(currentFirebaseUser.getUid()).child("TotalBagWeigth").runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(weigth);
                } else {
                    currentData.setValue((Long) currentData.getValue() + weigth);
                }

                return Transaction.success(currentData);
            }


            public void onComplete(DatabaseError firebaseError, boolean committed, DataSnapshot currentData) {
                if (firebaseError != null) {
                    Log.e("poop", "Firebase counter increment failed.");
                } else {
                    Log.e("poop", "Firebase counter increment succeeded.");
                }
            }
        });

    }
}





