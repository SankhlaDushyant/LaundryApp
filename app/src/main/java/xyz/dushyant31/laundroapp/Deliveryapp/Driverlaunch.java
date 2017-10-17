package xyz.dushyant31.laundroapp.Deliveryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import xyz.dushyant31.laundroapp.R;

public class Driverlaunch extends AppCompatActivity {

    private Button Route1, Route2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routes);
        final Animation anim_translate = AnimationUtils.loadAnimation(this, R.anim.anim_translate);

        Route1 = findViewById(R.id.Route1);
        Route2 = findViewById(R.id.Route2);


        Route1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);

                Intent intent = new Intent(getApplicationContext(), Route1.class);
                startActivity(intent);

            }


        });

        Route2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(anim_translate);

                Intent intent = new Intent(getApplicationContext(), Route2.class);
                startActivity(intent);
            }
        });
    }
}



