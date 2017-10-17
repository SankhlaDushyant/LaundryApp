package xyz.dushyant31.laundroapp.Deliveryapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import xyz.dushyant31.laundroapp.R;

public class Route2 extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    //    String[] CompanyNames = {"VIP", "Elite", "Lee", "AAA", "JB", "Skyway", "OldCLassic", "Best", "NewClassic", "Cary", "MVP"};
//    String[] CompanyNames2 ={  "S&S","AAA_2","Gold", "Modern","Sara", "1040Park", "Friendly/Skye", "Apple", "Smart", "Tiffany", "Julie", "Doric"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driverlaunch);

        // data to populate the RecyclerView with
        String[] CompanyNamesRoute2 ={  "S&S","AAA_2","Gold", "Modern","Sara", "1040Park", "Friendly/Skye", "Apple", "Smart", "Tiffany", "Julie", "Doric"};

        //String[] data = {"VIP", "Elite", "Lee", "AAA", "JB", "Skyway", "OldCLassic", "Best", "NewClassic", "Cary", "MVP", "S&S", "AAA_2", "Gold", "Modern","Sara", "1040Park", "Friendly/Skye", "Apple", "Smart", "Tiffany", "Julie", "Doric"};

        // set up the RecyclerView

        RecyclerView recyclerView1 = findViewById(R.id.route1);
        RecyclerView recyclerView2 = findViewById(R.id.route2);


        recyclerView1.setVisibility(View.GONE);
        TextView r1 = findViewById(R.id.r1);
        r1.setVisibility(View.GONE);

        int numberOfColumns2 = 3;
        recyclerView2.setLayoutManager(new GridLayoutManager(this, numberOfColumns2));
        adapter = new MyRecyclerViewAdapter(this, CompanyNamesRoute2);
        adapter.setClickListener(this);
        recyclerView2.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {

        String route2company = adapter.getItem(position);

        Intent i = new Intent(Route2.this, Driver.class);
        i.putExtra("routecompany", route2company);
        startActivity(i);
        finish();
    }





}