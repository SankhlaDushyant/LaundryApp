package xyz.dushyant31.laundroapp.laundrodetails;

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
import android.widget.ImageButton;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;

import xyz.dushyant31.laundroapp.R;

/**
 * Created by ADJ on 5/14/2017.
 */
public class InsertDetails extends AppCompatActivity {

    private Button detailbtn;
    String id;
    String shirts;
    String pants;
    ImageButton shirtButton;
    ImageButton pantButton;

    private EditText shirtcount,Uid,pantcount;
    private int countshirt = 0;
    private int countpant =0;
    private String scount,pcount;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectpage);
        detailbtn = (Button) findViewById(R.id.details_btn);
        shirtButton = (ImageButton) findViewById(R.id.shirtButton);
        pantButton = (ImageButton) findViewById(R.id.pantButton);

        pantcount = (EditText) findViewById(R.id.pantcount);
        Uid = (EditText) findViewById(R.id.Uid);
        shirtcount = (EditText) findViewById(R.id.shirtcount);

        shirtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countshirt=(countshirt+1);
                scount = Integer.toString(countshirt);
                shirtcount.setText(scount);
            }
        });

        pantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countpant=(countpant+1);
                pcount = Integer.toString(countpant);
                pantcount.setText(pcount);
            }
        });

        detailbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                id = Uid.getText().toString();
                shirts = shirtcount.getText().toString();
                pants = pantcount.getText().toString();
                new InsertDataActivity().execute();
            }
        });

    }

    private class InsertDataActivity extends AsyncTask < Void, Void, Void > {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(InsertDetails.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("Inserting your values..");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void...params) {
            JSONObject jsonObject = ControllerDetails.insertData(id, shirts, pants);
            Log.i(ControllerDetails.TAG, "Json obj ");

            try {
                /**
                 * Check Whether Its NULL???
                 */
                if (jsonObject != null) {

                    result = jsonObject.getString("result");

                }
            } catch (JSONException je) {
                Log.i(ControllerDetails.TAG, "" + je.getLocalizedMessage());
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