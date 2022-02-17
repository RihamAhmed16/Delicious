package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.delicious.ui.home.HomeFragment;

import java.util.ArrayList;

public class ClientRecipesActivity extends AppCompatActivity {

    ListView myRec;
    Recipes model;
    GetClientRecipes gmr=new GetClientRecipes();
    AdapterClientRecipes adapterClientRecipes;
    ArrayList<Recipes> arrMyRec;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_recipes);

        myRec=findViewById(R.id.myRec);
        arrMyRec=new ArrayList<>(gmr.getMyRec(LoginActivity.id));
        adapterClientRecipes=new AdapterClientRecipes(this,arrMyRec);
        myRec.setAdapter(adapterClientRecipes);

        SwipeRefreshLayout swp4=findViewById(R.id.swp4);
        swp4.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrMyRec=new ArrayList<>(gmr.getMyRec(LoginActivity.id));
                adapterClientRecipes=new AdapterClientRecipes(ClientRecipesActivity.this,arrMyRec);
                myRec.setAdapter(adapterClientRecipes);
                swp4.setRefreshing(false);
            }
        });

        myRec.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model=arrMyRec.get(i);
                RecipesActivity.rec_No=model.getRecipeNo();
                RecipesActivity.rec_Name=model.getRecipeName();
                startActivity(new Intent(ClientRecipesActivity.this,RecipeDetails.class));
            }
        });


    }
}