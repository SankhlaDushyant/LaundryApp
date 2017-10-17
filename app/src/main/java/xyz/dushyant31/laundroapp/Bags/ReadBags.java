package xyz.dushyant31.laundroapp.Bags;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.List;

import xyz.dushyant31.laundroapp.Deliveryapp.Driver;
import xyz.dushyant31.laundroapp.Deliveryapp.UIDModel;
import xyz.dushyant31.laundroapp.R;

/**
 * Created by dushman on 10/10/17.
 */

public class ReadBags extends AppCompatActivity {

    private static final String TAG = BagDetails.class.getSimpleName();

    private EditText Color, Weight;
    private Button Scanbags,QrGen;

    private String BagIDValue, ColorValue, WeightValue, TickitIdValue, CompanyNameValue;

    private Spinner bagsSpinner;
    private Bitmap bitmap;
    Context context;

   public String spinnerValue;

    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private DatabaseReference bagidref;

    public final static int QRcodeWidth = 400 ;
    public ArrayList<String> qrdata;

    private String Qrcode;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bagslist);

        Scanbags = (Button) findViewById(R.id.scan_btn);
        QrGen = (Button) findViewById(R.id.qr_btn);
        Color = (EditText) findViewById(R.id.bagcolor);
        Weight = (EditText) findViewById(R.id.bagweight);


        Intent i = getIntent();
        //CompanyNameValue = i.getStringExtra("CompanyName");
        qrdata = i.getStringArrayListExtra("QrcodeData");

        mFirebaseInstance = FirebaseDatabase.getInstance();
        mFirebaseDatabase = mFirebaseInstance.getReference();

        getUID(qrdata.get(2));

        Scanbags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerValue = String.valueOf(bagsSpinner.getSelectedItem());
                qrdata.add(spinnerValue);

                setbags(spinnerValue);
            }
        });

        QrGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try

                {
                    bitmap = TextToImageEncode(qrdata.get(0)+','+qrdata.get(1)+','+qrdata.get(2)+','+qrdata.get(3)+','+qrdata.get(4)+','+qrdata.get(5));
                    System.out.println("heloooooooooo------------->" );
                    doPhotoPrint(bitmap);


                } catch (WriterException e)

                {
                    e.printStackTrace();
                }


            }
        });

    }


    public void getUID(String company) {

        //listener.onStart();

        mFirebaseDatabase.child(company).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                UIDModel uid = dataSnapshot.getValue(UIDModel.class);
                String UID = uid.Userid;
                System.out.println("heloooooooooo------------->" + UID);
                setUID(UID);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });

    }


    public void setUID(final String userid) {

//fix login updating userid...............
        mFirebaseDatabase.child(userid).child("Tickets").child(qrdata.get(0)).child("Bags").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> bags = new ArrayList<>();

                for (DataSnapshot bagSnapshot : dataSnapshot.getChildren()) {
                    String Bagid = bagSnapshot.getKey();
                    bags.add(Bagid);
                    System.out.println("heloooooooooo------------->" + bags);
                }

                bagsSpinner = (Spinner) findViewById(R.id.bagspinner);
                ArrayAdapter<String> bagsAdapter = new ArrayAdapter<>(ReadBags.this, android.R.layout.simple_spinner_item, bags);
                bagsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                bagsSpinner.setAdapter(bagsAdapter);
                bagidref = mFirebaseInstance.getReference(userid+"/Tickets/"+qrdata.get(0)+"/Bags");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }

        });
    }

    public void setbags(String Bagid) {

        bagidref.child(Bagid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                BagModel bagdetails = dataSnapshot.getValue(BagModel.class);

                //Log.i(TAG,"helooo--------->"+driver.Date);

                Color.setText(bagdetails.Color);
                Weight.setText(bagdetails.Weight);
                qrdata.add(Weight.getText().toString());
                qrdata.add(Color.getText().toString());
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE, QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ? getResources().getColor(R.color.QRCodeBlackColor) : getResources().getColor(R.color.QRCodeWhiteColor);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 400, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    private void doPhotoPrint(Bitmap bitmap) {
        PrintHelper photoPrinter = new PrintHelper(ReadBags.this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("droids.jpg - test print", bitmap);
    }

}





