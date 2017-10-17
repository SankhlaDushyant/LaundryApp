package xyz.dushyant31.laundroapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADJ on 5/14/2017.
 */
public class InsertData extends AppCompatActivity {


    String bagid,timeslot,companyname,date,numbags,bagcolor,weight;
    String scannedData;

    private Button scanBtn;
    private Button insert;

    private EditText bagidValue,timeslotValue,companynameValue,dateValue,numbagsValue,bagcolorValue,weightValue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_data);
        insert = (Button) findViewById(R.id.insert_btn);
        scanBtn = (Button)findViewById(R.id.scan_btn);

        bagidValue = (EditText) findViewById(R.id.companyname);
        companynameValue = (EditText) findViewById(R.id.tickitid);
        dateValue = (EditText) findViewById(R.id.date);
        numbagsValue = (EditText) findViewById(R.id.numbag);
        weightValue = (EditText) findViewById(R.id.weight);



        final Activity activity =this;
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bagid = bagidValue.getText().toString();
                timeslot = timeslotValue.getText().toString();
                companyname = companynameValue.getText().toString();
                date = dateValue.getText().toString();
                numbags = numbagsValue.getText().toString();
                weight = weightValue.getText().toString();
                bagcolor = bagcolorValue.getText().toString();


                new InsertDataActivity().execute();
            }
        });

        scanBtn.setOnClickListener(new View.OnClickListener() {
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
            bagidValue.setText(namesList[0]);
            timeslotValue.setText(namesList[1]);
            companynameValue.setText(namesList[2]);
            dateValue.setText(namesList[3]);
            numbagsValue.setText(namesList[4]);
            bagcolorValue.setText(namesList[5]);
            weightValue.setText(namesList[6]);



            if (scannedData != null) {
                // Here we need to handle scanned data...
                insert.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        bagid = namesList[0];
                        timeslot = namesList[1];
                        companyname = namesList[2];
                        date = namesList[3];
                        numbags = namesList[4];
                        bagcolor = namesList[5];
                        weight = namesList[6];


                        new InsertDataActivity().execute();
                    }
                });


            } else {

            }
        }
    }

    class InsertDataActivity extends AsyncTask < Void, Void, Void > {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(InsertData.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("Inserting your values..");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void...params) {
            JSONObject jsonObject = Controller.insertData(bagid, timeslot,companyname,date,numbags,bagcolor,weight);
            Log.i(Controller.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result = jsonObject.getString("result");

                }
            } catch (JSONException je) {
                Log.i(Controller.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
        }
    }
}