package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.delicious.ui.home.HomeFragment;

import java.util.ArrayList;

public class SubcategoryActivity extends AppCompatActivity {

    public static String subno;

    GridView gv;
    Subcategory subcate;
    GetSubcategory gs=new GetSubcategory();
    AdapterSubcategory adapterS;
    ArrayList<Subcategory> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);

        gv=findViewById(R.id.subcate_GV);
        data=new ArrayList<>(gs.getSubData(HomeFragment.Cno));
        adapterS=new AdapterSubcategory(this,data);
        gv.setAdapter(adapterS);

        SwipeRefreshLayout swp1=findViewById(R.id.swp1);
        swp1.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data=new ArrayList<>(gs.getSubData(HomeFragment.Cno));
                adapterS=new AdapterSubcategory(SubcategoryActivity.this,data);
                gv.setAdapter(adapterS);
                swp1.setRefreshing(false);
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                subcate=data.get(i);
                subno=subcate.getSubcategoryNo();
                startActivity(new Intent(SubcategoryActivity.this, RecipesActivity.class));

            }
        });


    }
}