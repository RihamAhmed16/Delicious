package com.example.delicious;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetClientRecipes {

    public List<Recipes> getMyRec(String CNo) {

        List<Recipes> data = new ArrayList<>();
        Database database = new Database();
        database.ConnectDB();

        ResultSet resultSet = database.RunSearch("select * from Recipes where client_No='"+CNo+"'");
        try {
            while (resultSet.next())           //not if , but use while To More than one recipe
            {
                Recipes recipes = new Recipes();
                recipes.setRecipeNo(resultSet.getString(1));
                recipes.setRecipeName(resultSet.getString(2));
                recipes.setSubcategoryNo(resultSet.getString(3));
                recipes.setClientNo(resultSet.getString(4));
                recipes.setCategoryNo(resultSet.getString(5));
                recipes.setRecipeImg(resultSet.getString(6));
                recipes.setRatingBar(resultSet.getInt(7));

                data.add(recipes);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;


    }

}
