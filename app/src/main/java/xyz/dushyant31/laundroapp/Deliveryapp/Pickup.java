package xyz.dushyant31.laundroapp.Deliveryapp;
import android.app.Activity;

import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import xyz.dushyant31.laundroapp.R;

public class Pickup extends AppCompatActivity {

    private static final String TAG = Driver.class.getSimpleName();

    private Button Bagvalidate,Scan;
    private EditText TickitId, Weigth, Date, Duedate, Numbags, CompanyName;

    private String scannedData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);
        final Activity activity =this;

        TickitId = (EditText) findViewById(R.id.tickitid);
        Weigth = (EditText) findViewById(R.id.weight);
        Date = (EditText) findViewById(R.id.date);
        Duedate = (EditText) findViewById(R.id.duedate);
        Numbags = (EditText) findViewById(R.id.numbag);
        CompanyName = (EditText) findViewById(R.id.companyname);

        Bagvalidate = (Button) findViewById(R.id.bag_details);
        Scan = (Button) findViewById(R.id.scan_btn);

        Bagvalidate.setVisibility(View.GONE);
        Weigth.setVisibility(View.GONE);
        Date.setVisibility(View.GONE);
        Duedate.setVisibility(View.GONE);

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

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            scannedData = result.getContents();
            final String[] namesList = scannedData.split(",");
            TickitId.setText(namesList[0]);
            Numbags.setText(namesList[1]);
            CompanyName.setText(namesList[2]);
        }
    }
}





