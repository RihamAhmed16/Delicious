package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    EditText recName,recImg,Ingrs,Steps;
    Spinner cateName,subName;
    Button addbtn;

    String catNo="2",subNo="1",Rec_No="1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        recName=findViewById(R.id.rec_name);
        recImg=findViewById(R.id.rec_img);
        Ingrs=findViewById(R.id.ingrs);
        Steps=findViewById(R.id.steps);
        cateName=findViewById(R.id.catename);
        subName=findViewById(R.id.subname);
        addbtn=findViewById(R.id.btnAdd);



        //ArrayList<String> ingrArr;
        //ArrayList stepArr=new ArrayList<>(Steps.getText().toString().split("\n"));




        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String []ingrArr=Ingrs.getText().toString().split("\n");
                String []stepArr=Steps.getText().toString().split("\n");

                Database db=new Database();
                db.ConnectDB();

                ResultSet rs1=db.RunSearch("select * from category where category_name='"+cateName.getSelectedItem()+"'");
                try {
                    if(rs1.next()) {
                        catNo = rs1.getString(1);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

                String cat_sub_name=null;
                cat_sub_name=subName.getSelectedItem()+" "+cateName.getSelectedItem();

                ResultSet rs2=db.RunSearch("select * from Subcategory where name='"+cat_sub_name+"'");
                try {
                    if(rs2.next()) {
                        subNo = rs2.getString(1);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }




                String insRec=null;
                insRec=db.RUNDML("insert into Recipes values(N'"+recName.getText()+"','"+subNo+"','"+LoginActivity.id+"','"+catNo+"','"+recImg.getText()+"','"+"0"+"')");
                if(insRec.equals("Ok")){
                    ResultSet rs3=db.RunSearch("select max(Recipes_No) from Recipes");
                    try {
                        if(rs3.next()){

                            Rec_No=rs3.getString(1);
                            String ins_ing=null,ins_step=null;

                            for (int lop=0;lop<ingrArr.length;lop++){
                                ins_ing=db.RUNDML("insert into Recipes_Ingredients values(N'"+ingrArr[lop]+"','"+Rec_No+"')");
                                if(ins_ing.equals("Ok")){
                                }
                                else {
                                    Toast.makeText(AddActivity.this,"Error add ingredients"+ins_ing,Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }

                            for (int lop=0;lop<stepArr.length;lop++){
                                ins_step=db.RUNDML("insert into Recipes_steps values(N'"+Rec_No+"','"+stepArr[lop]+"')");
                                if(ins_step.equals("Ok")){
                                }
                                else {
                                    Toast.makeText(AddActivity.this,"Error add steps"+ins_step,Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }

                            Toast.makeText(AddActivity.this,"Recipe inserted successfully",Toast.LENGTH_SHORT).show();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(AddActivity.this,"Error add recipe"+insRec,Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}