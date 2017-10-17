package xyz.dushyant31.laundroapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ADJ on 5/14/2017.
 */
public class UpdateData extends AppCompatActivity{

    private Button update,insert;

    String ticketid;
    String numbags;
    String weigth;
    String status;

    private Spinner statussel;

    private EditText ticketidValue,numbagsValue,weigthValue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);

        update=(Button)findViewById(R.id.update_btn);
        insert=(Button)findViewById(R.id.dryinsert_btn);

        statussel = (Spinner) findViewById(R.id.washdryspin);

        ticketidValue=(EditText)findViewById(R.id.companyname);
        numbagsValue=(EditText)findViewById(R.id.bagscomp);
        weigthValue = (EditText)findViewById(R.id.fweight);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ticketid=ticketidValue.getText().toString();
                numbags=numbagsValue.getText().toString();
                weigth=weigthValue.getText().toString();
                status=String.valueOf(statussel.getSelectedItem());
                new UpdateDataActivity().execute();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ticketid=ticketidValue.getText().toString();
                numbags=numbagsValue.getText().toString();
                weigth=weigthValue.getText().toString();
                status=String.valueOf(statussel.getSelectedItem());
                new InsertDataActivity().execute();
            }
        });
    }

    class InsertDataActivity extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result=null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(UpdateData.this);
            dialog.setTitle("Hey Wait Please..."+x);
            dialog.setMessage("Inserting your order");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = Controller.insertdrywashData(ticketid,weigth,numbags,status);
            Log.i(Controller.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result=jsonObject.getString("result");

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
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }

    class UpdateDataActivity extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result=null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(UpdateData.this);
            dialog.setTitle("Hey Wait Please..."+x);
            dialog.setMessage("I am getting your JSON");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = Controller.updateData(ticketid,weigth,numbags,status);
            Log.i(Controller.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result=jsonObject.getString("result");

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
            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_LONG).show();
        }
    }
}