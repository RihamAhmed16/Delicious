package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delicious.ui.home.HomeFragment;

import java.util.ArrayList;

public class RatingsActivity extends AppCompatActivity {

    ListView lstRate;
    TextView recnameRate;
    RateClass model;
    GetRatings gr=new GetRatings();
    AdapterRatings adapterRatings;
    ArrayList<RateClass> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        lstRate=findViewById(R.id.lst_rate);
        recnameRate=findViewById(R.id.recname_rate);

        arr=new ArrayList<>(gr.getRate(RecipesActivity.rec_No));
        adapterRatings=new AdapterRatings(this,arr);
        lstRate.setAdapter(adapterRatings);

        recnameRate.setText(RecipesActivity.rec_Name);

        SwipeRefreshLayout swp5=findViewById(R.id.swp5);
        swp5.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arr=new ArrayList<>(gr.getRate(RecipesActivity.rec_No));
                adapterRatings=new AdapterRatings(RatingsActivity.this,arr);
                lstRate.setAdapter(adapterRatings);

                recnameRate.setText(RecipesActivity.rec_Name);

                swp5.setRefreshing(false);
            }
        });


    }
}