package xyz.dushyant31.laundroapp;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class QrGenerator extends AppCompatActivity {
    static final int DATE_DIALOG_ID = 0;

    //ImageView QR;
    Button button, qrcodes;
    EditText CompanyName,BagID,Numbag,Date,Bagweight;
    TextClock Timeslot;
    String CompanyNameValue,BagIDValue, NumbagValue,DateValue,TimeslotValue,BagweightValue,ColorValue;
    String qrcode;
    private int mYear,mMonth,mDay;
    Spinner colorchoice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.genqr);

        //QR = (ImageView) findViewById(R.id.imageView);
        //CompanyName = (EditText) findViewById(R.id.CName);
        BagID = (EditText) findViewById(R.id.TickitId);
        Numbag = (EditText) findViewById(R.id.numbags);
        button = (Button) findViewById(R.id.qr_btn);

        //qrcodes = (Button) findViewById(R.id.qrcodeslist);


        Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        Date = (EditText) findViewById(R.id.Date);

        //Timeslot = (TextClock) findViewById(R.id.timeslot);
        Bagweight = (EditText) findViewById(R.id.weight);
        //colorchoice = (Spinner) findViewById(R.id.colorchoice);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date.setText(sdf.format(c.getTime()));

        Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showDialog(DATE_DIALOG_ID);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CompanyNameValue = CompanyName.getText().toString();
                BagIDValue = BagID.getText().toString();
                NumbagValue = Numbag.getText().toString();
                DateValue = Date.getText().toString();
                TimeslotValue = Timeslot.getText().toString();
                BagweightValue = Bagweight.getText().toString();
                ColorValue = String.valueOf(colorchoice.getSelectedItem());
                qrcode = CompanyNameValue+','+BagIDValue +','  + NumbagValue +',' + DateValue +','  + TimeslotValue +','  +BagweightValue+ ',' +ColorValue;
                new InsertQrcode().execute();

            }
        });

        qrcodes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ReadAllQr.class);
                startActivity(intent);

            }
        });




    }
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        mDateSetListener,
                        mYear, mMonth, mDay);

        }

        return null;

    }
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            Date.setText(new StringBuilder().append(mDay).append("/").append(mMonth+1).append("/").append(mYear));

        }

    };


    class InsertQrcode extends AsyncTask< Void, Void, Void > {

        ProgressDialog dialog;
        int jIndex;
        int x;

        String result = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(QrGenerator.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("Inserting your values..");
            dialog.show();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void...params) {
            JSONObject jsonObject = Controller.insertqrcode(qrcode);
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

//    Bitmap TextToImageEncode(String Value) throws WriterException {
//        BitMatrix bitMatrix;
//        try {
//            bitMatrix = new MultiFormatWriter().encode(
//                    Value,
//                    BarcodeFormat.DATA_MATRIX.QR_CODE,
//                    QRcodeWidth, QRcodeWidth, null
//            );
//
//        } catch (IllegalArgumentException Illegalargumentexception) {
//
//            return null;
//        }
//        int bitMatrixWidth = bitMatrix.getWidth();
//
//        int bitMatrixHeight = bitMatrix.getHeight();
//
//        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
//
//        for (int y = 0; y < bitMatrixHeight; y++) {
//            int offset = y * bitMatrixWidth;
//
//            for (int x = 0; x < bitMatrixWidth; x++) {
//
//                pixels[offset + x] = bitMatrix.get(x, y) ?
//                        getResources().getColor(R.color.QRCodeBlackColor):getResources().getColor(R.color.QRCodeWhiteColor);
//            }
//        }
//        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);
//
//        bitmap.setPixels(pixels, 0, 400, 0, 0, bitMatrixWidth, bitMatrixHeight);
//        return bitmap;
//    }
}