package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.delicious.ui.home.HomeFragment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

public class RecipeDetails extends AppCompatActivity {

    TextView recName;
    ImageView recImg;
    ListView ingredient,step;
    ArrayList<String> ingrs=new ArrayList<>();
    ArrayList<String> steps=new ArrayList<>();
    AdapterItem adapter1;
    AdapterItem adapter2;

    ImageButton btnfav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        recName=findViewById(R.id.txtrecname);
        recImg=findViewById(R.id.imgrec);
        ingredient=findViewById(R.id.theingr);
        step=findViewById(R.id.thesteps);

        btnfav=findViewById(R.id.btnfav);


        Database db =new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("select * from Recipes where Recipes_No='"+RecipesActivity.rec_No+"'");
        try {
            if(rs.next()){
                recName.setText(rs.getString(2));
                PicassoClient.downloadImage(this,rs.getString(6),recImg);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Database db1 =new Database();
        db1.ConnectDB();
        ResultSet rs1=db1.RunSearch("select * from Recipes_Ingredients where recipe_No='"+RecipesActivity.rec_No+"'");
        try {
            while (rs1.next()){
                ingrs.add(rs1.getString(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        adapter1=new AdapterItem(this, ingrs);
        ingredient.setAdapter(adapter1);

        Database db2 =new Database();
        db2.ConnectDB();
        ResultSet rs2=db2.RunSearch("select * from Recipes_steps where recipe_No='"+RecipesActivity.rec_No+"'");
        try {
            while (rs2.next()){
                steps.add(rs2.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        adapter2=new AdapterItem(this,steps);
        step.setAdapter(adapter2);

        Database db3=new Database();
        db3.ConnectDB();

        ResultSet isFav = db3.RunSearch("select * from Favourite where client_no='" + LoginActivity.id + "' and Recipes_No='" + RecipesActivity.rec_No + "'");
        try {
            if(isFav.next()){
                btnfav.setImageResource(R.drawable.favorite);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Database dbfav=new Database();
                dbfav.ConnectDB();

                ResultSet rsFav = db.RunSearch("select * from Favourite where client_no='" + LoginActivity.id + "' and Recipes_No='" + RecipesActivity.rec_No + "'");
                try {
                    if (rsFav.next()) {
                        btnfav.setImageResource(R.drawable.favorite_border);
                        String delet=null;
                        delet=dbfav.RUNDML("delete from  Favourite where client_no='" + LoginActivity.id + "' and Recipes_No='" + RecipesActivity.rec_No + "'");
                        if(delet.equals("Ok"))
                        {
                            Toast.makeText(RecipeDetails.this, "Recipe removed from your favourites successfully", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(RecipeDetails.this,"Error is "+delet,Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        btnfav.setImageResource(R.drawable.favorite);
                        String conc=null;
                        conc=dbfav.RUNDML("insert into  Favourite values(N'"+LoginActivity.id+"','"+RecipesActivity.rec_No+"')");
                        if(conc.equals("Ok"))
                        {
                            Toast.makeText(RecipeDetails.this, "Recipe added to your favourites successfully", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(RecipeDetails.this,"Error is "+conc,Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }



            }
        });


        //Toast.makeText(this, ""+ HomeFragment.Cno+" "+SubcategoryActivity.subno+" "+RecipesActivity.rec_No, Toast.LENGTH_SHORT).show();

    }

    public void rate_rec(View view) {
        //float cnt=0.0f,summ=0.0f;

        LayoutInflater user_rate = LayoutInflater.from(RecipeDetails.this);
        View showInView = user_rate.inflate(R.layout.rate_layout, null);

        AlertDialog.Builder showInAlert = new AlertDialog.Builder(RecipeDetails.this);
        showInAlert.setView(showInView);

        EditText comment=showInView.findViewById(R.id.comment);
        RatingBar rate_value = showInView.findViewById(R.id.rateing);

        showInAlert.setPositiveButton("Save Rate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Database db=new Database();
                db.ConnectDB();
                String insRate=db.RUNDML("insert into Rating values('"+RecipesActivity.rec_No+"','"+ Calendar.getInstance().getTime() +"','"+rate_value.getRating()+"','"+comment.getText()+"')");
                if(insRate.equals("Ok")){

                    Toast.makeText(RecipeDetails.this, "your Rate added successfully", Toast.LENGTH_SHORT).show();

                    /*ResultSet rs_sum=db.RunSearch("select sum(value) from Rating where recipe_No='"+RecipesActivity.rec_No+"'");
                    try {
                        if(rs_sum.next()){

                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }*/

                }
                else{
                    Toast.makeText(RecipeDetails.this, "Error in "+insRate, Toast.LENGTH_SHORT).show();
                }

            }
        });
        showInAlert.setNegativeButton("cancel", null);

        showInAlert.create().show();
    }

    public void rate_view(View view) {

        startActivity(new Intent(RecipeDetails.this,RatingsActivity.class));

    }
}