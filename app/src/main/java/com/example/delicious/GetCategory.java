package com.example.delicious;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetCategory {

    public List<Category> getData(){
        List<Category> data=new ArrayList<>();
        Database db=new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("SELECT * FROM [category]");
        try {
            while (rs.next()){
                Category cat=new Category();
                cat.setCategoryNo(rs.getString(1));
                cat.setName(rs.getString(2));
                cat.setLogo(rs.getString(3));
                data.add(cat);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }

}
