package com.example.delicious;

import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetFavourites {

    public List<Recipes> getFav(String clientNo) {

        List<String> favRec = new ArrayList<>();
        Database database = new Database();
        database.ConnectDB();
        ResultSet resultSet = database.RunSearch("select * from Favourite where client_no='" + clientNo + "'");
        try {
            while (resultSet.next()) {

                favRec.add(resultSet.getString(2));

                /**/
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        List<Recipes> favData = new ArrayList<>();
        Database database1 = new Database();
        database1.ConnectDB();
        for (int lop = 0; lop < favRec.size(); lop++) {
            ResultSet resultSet1 = database1.RunSearch("select * from Recipes where Recipes_No='" + favRec.get(lop) + "'");
            try {
                if (resultSet1.next()) {
                    Recipes recipes = new Recipes();
                    recipes.setRecipeNo(resultSet1.getString(1));
                    recipes.setRecipeName(resultSet1.getString(2));
                    recipes.setSubcategoryNo(resultSet1.getString(3));
                    recipes.setClientNo(resultSet1.getString(4));
                    recipes.setCategoryNo(resultSet1.getString(5));
                    recipes.setRecipeImg(resultSet1.getString(6));
                    recipes.setRatingBar(resultSet1.getInt(7));

                    favData.add(recipes);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

        return favData;
    }
}
