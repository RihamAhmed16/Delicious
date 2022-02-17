package com.example.delicious;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyProfileActivity extends AppCompatActivity {

    EditText txtname,txtemail,txtphone,txtpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        txtname=findViewById(R.id.nameP);
        txtphone=findViewById(R.id.phoneP);
        txtemail=findViewById(R.id.emailP);
        txtpassword=findViewById(R.id.passwordP);

        Database db =new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("select * from Client where client_no='"+LoginActivity.id+"'");
        try {
            if(rs.next()){
                txtname.setText(rs.getString(2));
                txtemail.setText(rs.getString(3));
                txtphone.setText(rs.getString(4));
                txtpassword.setText(rs.getString(5));
                //PicassoClient.downloadImage(this,rs.getString(6),imgprof);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        txtemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String regExp= "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
                if(txtemail.getText().toString().matches(regExp))
                {
                    ;
                }
                else {
                    txtemail.setError("Invaild Email Formate (example@domain)");
                }

            }
        });

        txtphone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b==true)
                {
                    ;
                }
                else
                {
                    if(txtphone.getText().toString().length()!=11) {
                        Toast.makeText(MyProfileActivity.this, "Must be 11 digit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void updateProfile(View view) {
        if (txtemail.getText().toString().isEmpty()) {
            txtemail.setError("Please enter Email");
            txtemail.requestFocus();
        } else {
            if (txtphone.getText().toString().isEmpty()) {
                txtphone.setError("Please enter Phone");
                txtphone.requestFocus();
            } else {
                if (txtpassword.getText().toString().isEmpty()) {
                    txtpassword.setError("Please enter password");
                    txtpassword.requestFocus();
                } else {
                    Database db=new Database();
                    Connection conn=db.ConnectDB();
                    if(conn==null)
                    {
                        Toast.makeText(this, "Check internet access", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String don=null;
                        don=db.RUNDML("update Client set client_name=N'"+txtname.getText()+"',email='"+txtemail.getText()+"',phone='"+txtphone.getText()+"',password='"+txtpassword.getText()+"' where client_no='"+LoginActivity.id+"'");
                        if(don.equals("Ok"))
                        {
                            AlertDialog.Builder ad=new AlertDialog.Builder(MyProfileActivity.this);
                            ad.setTitle("updation...");
                            ad.setMessage("Your account has updated :)");
                            ad.setIcon(R.drawable.new_icon);

                            ad.setNegativeButton("Thanks",null);
                            ad.create().show();
                        }
                        else if(don.contains("uqemail"))
                        {
                            Toast.makeText(this, "This email is used", Toast.LENGTH_LONG).show();
                        }
                        else if(don.contains("uqphone"))
                        {
                            Toast.makeText(this, "This phone is used", Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(this,"Error is "+don,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
    }

    public void getFav(View view) {
        startActivity(new Intent(MyProfileActivity.this,FavouriteActivity.class));
    }

    public void myRecipes(View view) {
        startActivity(new Intent(MyProfileActivity.this,ClientRecipesActivity.class));
    }

}