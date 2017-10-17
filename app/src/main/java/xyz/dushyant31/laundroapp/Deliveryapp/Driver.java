package xyz.dushyant31.laundroapp.Deliveryapp;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xyz.dushyant31.laundroapp.Bags.BagDetails;
import xyz.dushyant31.laundroapp.Bags.ReadBags;
import xyz.dushyant31.laundroapp.Cleanerapp.Cleaner;
import xyz.dushyant31.laundroapp.R;

public class Driver extends AppCompatActivity {

    private static final String TAG = Driver.class.getSimpleName();

    private Button Populate,Bagvalidate;
    private EditText TickitId, Weigth, Date, Duedate, Numbags, CompanyName;

    private String TickitIdValue, WeigthValue, DateValue, DuedateValue, NumbagsValue, TimeslotValue, SameDayValue;

    private View someView;
    public String UID ;
    private List<String> qrdata;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);

        someView = findViewById(R.id.screen);

        TickitId = (EditText) findViewById(R.id.tickitid);
        Weigth = (EditText) findViewById(R.id.weight);
        Date = (EditText) findViewById(R.id.date);
        Duedate = (EditText) findViewById(R.id.duedate);
        Numbags = (EditText) findViewById(R.id.numbag);
        CompanyName = (EditText) findViewById(R.id.companyname);

        Populate = (Button) findViewById(R.id.scan_btn);
        Bagvalidate = (Button) findViewById(R.id.bag_details);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        Intent i = getIntent();
        CompanyName.setText(i.getStringExtra("routecompany"));

        qrdata = new ArrayList<String>();

        //TickitIdValue = "T123";

        Populate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUID(CompanyName.getText().toString());

            }
        });

        Bagvalidate.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                //String CompanyNameValue = CompanyName.getText().toString();
                qrdata.add(TickitId.getText().toString());
                qrdata.add(Numbags.getText().toString());
                qrdata.add(CompanyName.getText().toString());

                Intent i = new Intent(Driver.this, ReadBags.class);
                i.putStringArrayListExtra("QrcodeData",(ArrayList<String>) qrdata);
                startActivity(i);
            }
        });



    }

    public void getUID(String company){

        //listener.onStart();

        mFirebaseDatabase.child(company).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UIDModel uid = dataSnapshot.getValue(UIDModel.class);
                UID = uid.Userid;
                setUID(UID);
                System.out.println(UID);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }

    private void setUID(String userid){

        mFirebaseDatabase.child(userid).child("Tickets").child(TickitId.getText().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DriverModel driver = dataSnapshot.getValue(DriverModel.class);

                //Log.i(TAG,"helooo--------->"+driver.Date);

//                TickitId.setText(TickitIdValue);
                Weigth.setText(driver.Weigth);
                Date.setText(driver.Date);
                Duedate.setText(driver.DueDate);
                Numbags.setText(driver.Noofbags);
                SameDayValue = driver.SameDay;
                if(SameDayValue.equals("Y")) {
                    someView.setBackgroundColor(Color.parseColor("#ff9894"));
                }
                if(SameDayValue.equals("N")) {
                    someView.setBackgroundColor(Color.parseColor("#7fff8a"));
                }


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}
