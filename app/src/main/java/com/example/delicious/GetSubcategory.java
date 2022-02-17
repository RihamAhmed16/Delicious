package com.example.delicious;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetSubcategory {

    public List<Subcategory> getSubData(String cateNo){
        List<Subcategory> data=new ArrayList<>();
        Database db=new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("SELECT * FROM Subcategory where Category_No='"+cateNo+"'");
        try {
            while (rs.next()){
                Subcategory sub=new Subcategory();
                sub.setSubcategoryNo(rs.getString(1));
                sub.setName(rs.getString(3));
                sub.setLogo(rs.getString(4));
                data.add(sub);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return data;
    }


}
