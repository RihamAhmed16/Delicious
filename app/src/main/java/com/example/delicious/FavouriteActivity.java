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

public class FavouriteActivity extends AppCompatActivity {

    ListView flst;
    Recipes model;
    GetFavourites gf=new GetFavourites();
    AdapterFavorites adapterFavorites;

    ArrayList<Recipes> arrF;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        flst=findViewById(R.id.lst_fav);
        arrF=new ArrayList<>(gf.getFav(LoginActivity.id));
        adapterFavorites=new AdapterFavorites(this,arrF);
        flst.setAdapter(adapterFavorites);

        //Toast.makeText(this, ""+arrF.size()+" ", Toast.LENGTH_SHORT).show();


        /*for (int tst=0;tst<arrF.size();tst++){
            Recipes rec = new Recipes();
            rec=arrF.get(tst);
            Toast.makeText(this, ""+rec.getRecipeName()+" ", Toast.LENGTH_SHORT).show();
        }*/


        SwipeRefreshLayout swpfav=findViewById(R.id.swpfav);
        swpfav.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrF=new ArrayList<>(gf.getFav(LoginActivity.id));
                adapterFavorites=new AdapterFavorites(FavouriteActivity.this,arrF);
                flst.setAdapter(adapterFavorites);
                swpfav.setRefreshing(false);
            }
        });


        flst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                model=arrF.get(i);
                RecipesActivity.rec_No=model.getRecipeNo();
                RecipesActivity.rec_Name=model.getRecipeName();
                startActivity(new Intent(FavouriteActivity.this,RecipeDetails.class));
            }
        });

    }
}