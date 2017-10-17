package xyz.dushyant31.laundroapp.Deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import xyz.dushyant31.laundroapp.R;

/**
 * Created by dushman on 10/15/17.
 */

public class PickDel extends AppCompatActivity {

    private Button Pickup, Delivery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pickdelivery);
        final Animation anim_translate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        Pickup = findViewById(R.id.pickup);
        Delivery = findViewById(R.id.delivery);


        Pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);

                Intent intent = new Intent(getApplicationContext(), Pickup.class);
                startActivity(intent);

            }


        });

        Delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);

                Intent intent = new Intent(getApplicationContext(), Driverlaunch.class);
                startActivity(intent);
            }
        });
    }
}





