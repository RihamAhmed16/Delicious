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

public class RecipesActivity extends AppCompatActivity {

    public static String rec_No,rec_Name;

    ListView lst;
    Recipes model;
    GetRecipes gr=new GetRecipes();
    AdapterRecipes adapterRecipes;
    ArrayList<Recipes> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        lst=findViewById(R.id.lst_rec);
        arr=new ArrayList<>(gr.getRec(HomeFragment.Cno,SubcategoryActivity.subno));
        adapterRecipes=new AdapterRecipes(this,arr);
        lst.setAdapter(adapterRecipes);

        SwipeRefreshLayout swp2=findViewById(R.id.swp2);
        swp2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arr=new ArrayList<>(gr.getRec(HomeFragment.Cno,SubcategoryActivity.subno));
                adapterRecipes=new AdapterRecipes(RecipesActivity.this,arr);
                lst.setAdapter(adapterRecipes);
                swp2.setRefreshing(false);
            }
        });


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model=arr.get(i);
                rec_No=model.getRecipeNo();
                rec_Name=model.getRecipeName();
                startActivity(new Intent(RecipesActivity.this,RecipeDetails.class));
            }
        });


    }
}