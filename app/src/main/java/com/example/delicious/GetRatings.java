package com.example.delicious;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetRatings {

    public List<RateClass> getRate(String rec_num) {

        List<RateClass> dataRate = new ArrayList<>();
        Database database = new Database();
        database.ConnectDB();
        ResultSet resultSet = database.RunSearch("select * from Rating where recipe_No='" + rec_num + "'");
        try {
            while (resultSet.next()) {
                RateClass rate = new RateClass();
                rate.setComment(resultSet.getString(5));
                rate.setDate(resultSet.getString(3));
                rate.setValue(resultSet.getFloat(4));

                dataRate.add(rate);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return dataRate;
    }
}