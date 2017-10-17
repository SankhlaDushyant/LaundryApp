package xyz.dushyant31.laundroapp.Bags;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseUser;

import xyz.dushyant31.laundroapp.Deliveryapp.Driver;
import xyz.dushyant31.laundroapp.R;

public class BagDetails extends AppCompatActivity {

    private static final String TAG = BagDetails.class.getSimpleName();

    private Button Bagbtn;
    private EditText BagID, Color, Weight;

    private String BagIDValue, ColorValue, WeightValue, TickitIdValue;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseUser currentFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bagdetails);

        BagID = (EditText) findViewById(R.id.bagid);
        Color = (EditText) findViewById(R.id.bagcolor);
        Weight = (EditText) findViewById(R.id.bagweight);

        Intent i = getIntent();
        TickitIdValue = i.getStringExtra("Tickitid");

        Bagbtn = (Button) findViewById(R.id.bag_btn);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();
        currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Bagbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BagIDValue = BagID.getText().toString();
                ColorValue = Color.getText().toString();
                WeightValue = Weight.getText().toString();

                InsertBagintoDatabase(TickitIdValue,BagIDValue, ColorValue, WeightValue);

                //ends after 1 click!!!!!
            }
        });

    }

    private void InsertBagintoDatabase(String TickitIdValue, String BagIDValue, String ColorValue, String WeightValue) {

        BagModel bags = new BagModel(ColorValue,WeightValue);

        mFirebaseDatabase.child(currentFirebaseUser.getUid()).child("Tickets").child(TickitIdValue).child("Bags").child(BagIDValue).setValue(bags);

        BagID.setText("");
        Color.setText("");
        Weight.setText("");
    }

}
