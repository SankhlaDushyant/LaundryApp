package xyz.dushyant31.laundroapp.Cleanerapp;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;

import xyz.dushyant31.laundroapp.Bags.BagDetails;
import xyz.dushyant31.laundroapp.Deliveryapp.Driver;
import xyz.dushyant31.laundroapp.R;

public class Cleaner extends AppCompatActivity {

    private static final String TAG = Cleaner.class.getSimpleName();

    private Button Openjob, Bagbutton;
    private EditText TickitId, Weigth, Date, Duedate, Numbags, SameDay;

    private String TickitIdValue, WeigthValue, DateValue, DuedateValue, NumbagsValue, SameDayValue;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseUser currentFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genqr);

        TickitId = (EditText) findViewById(R.id.TickitId);
        Weigth = (EditText) findViewById(R.id.weight);
        Date = (EditText) findViewById(R.id.Date);
        Duedate = (EditText) findViewById(R.id.Deliverydate);
        Numbags = (EditText) findViewById(R.id.numbags);
        SameDay = (EditText) findViewById(R.id.sameday);

        Openjob = (Button) findViewById(R.id.qr_btn);
        Bagbutton = (Button) findViewById(R.id.bag_btn);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Openjob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TickitIdValue = TickitId.getText().toString();
                WeigthValue = Weigth.getText().toString();
                DateValue = Date.getText().toString();
                DuedateValue = Duedate.getText().toString();
                NumbagsValue = Numbags.getText().toString();
                SameDayValue = SameDay.getText().toString();

                InsertintoDatabase(TickitIdValue, WeigthValue, DateValue, DuedateValue, NumbagsValue, SameDayValue);
            }
        });

        Bagbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TickitIdValue = TickitId.getText().toString();
                Intent i = new Intent(Cleaner.this, BagDetails.class);
                i.putExtra("Tickitid",TickitIdValue);
                startActivity(i);
            }
        });


    }

    private void InsertintoDatabase(String TickitIdValue, String WeigthValue, String DateValue, String DuedateValue, String NumbagsValue, String SameDayValue) {

        CleanerModel cleaner = new CleanerModel(DateValue,DuedateValue,NumbagsValue,SameDayValue,WeigthValue);

        mFirebaseDatabase.child(currentFirebaseUser.getUid()).child("Tickets").child(TickitIdValue).setValue(cleaner);

//        startActivity(new Intent(Cleaner.this, Driver.class));
//        finish();
    }

}
