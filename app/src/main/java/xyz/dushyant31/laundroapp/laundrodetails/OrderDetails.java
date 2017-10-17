package xyz.dushyant31.laundroapp.laundrodetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import xyz.dushyant31.laundroapp.DeleteData;
import xyz.dushyant31.laundroapp.InsertData;
import xyz.dushyant31.laundroapp.InternetConnection;
import xyz.dushyant31.laundroapp.R;
import xyz.dushyant31.laundroapp.ReadSingleData;
import xyz.dushyant31.laundroapp.UpdateData;

public class OrderDetails extends AppCompatActivity {


    private Button read_detail, insert_detail, delete_detail, update_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_list);
        final Animation anim_translate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        //read_detail = (Button) findViewById(R.id.read_btn_d);
        insert_detail = (Button) findViewById(R.id.insert_btn_d);
//        update_detail = (Button) findViewById(R.id.update_btn_d);
//        delete_detail = (Button) findViewById(R.id.delete_btn_d);




        insert_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);


                if (InternetConnection.checkConnection(getApplicationContext())) {
                    Intent intent = new Intent(getApplicationContext(), InsertDetails.class);
                    startActivity(intent);


                } else {
                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                }

            }

        });

    }

}