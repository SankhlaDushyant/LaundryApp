package xyz.dushyant31.laundroapp;
//MainActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private Button read, readAll, driver, delete, drywash, cleaner, detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cleanerdriver);
        final Animation anim_translate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        //read = (Button) findViewById(R.id.read_btn);
       // readAll = (Button) findViewById(R.id.read_all_btn);
        driver = (Button) findViewById(R.id.driver_btn);
        drywash = (Button) findViewById(R.id.washdry_btn);
        //delete = (Button) findViewById(R.id.delete_btn);
        cleaner = (Button) findViewById(R.id.cleaner_btn);
        //detail = (Button) findViewById(R.id.orderdetails_uid);

//        readAll.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.startAnimation(anim_translate);
//
//                if (InternetConnection.checkConnection(getApplicationContext())) {
//                    Intent intent = new Intent(getApplicationContext(), ReadAllData.class);
//                    startActivity(intent);
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
//                }
//
//
//
//
//
//            }
//        });


        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);


                if (InternetConnection.checkConnection(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), InsertData.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }

            }

        });

        cleaner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);
                if (InternetConnection.checkConnection(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), QrGenerator.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();

                }


            }
        });

        drywash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);


                if (InternetConnection.checkConnection(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), UpdateData.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }




            }
        });
//
//
//        read.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.startAnimation(anim_translate);
//
//                if (InternetConnection.checkConnection(getApplicationContext())) {
//                    Intent intent = new Intent(getApplicationContext(), ReadSingleData.class);
//                    startActivity(intent);
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
//                }
//
//
//
//
//            }
//        });
//
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.startAnimation(anim_translate);
//
//                if (InternetConnection.checkConnection(getApplicationContext())) {
//                    Intent intent = new Intent(getApplicationContext(), DeleteData.class);
//                    startActivity(intent);
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//        });



//        detail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                view.startAnimation(anim_translate);
//                if (InternetConnection.checkConnection(getApplicationContext())) {
//                    Intent intent = new Intent(getApplicationContext(), xyz.dushyant31.laundroapp.laundrodetails.OrderDetails.class);
//                    startActivity(intent);
//
//
//                } else {
//                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
//
//                }
//
//
//            }
//        });




    }

}