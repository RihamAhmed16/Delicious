package com.example.delicious;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;

public class RegisterActivity extends AppCompatActivity {

    EditText txtname,txtemail,txtphone,txtpassword;
    private WifiManager wm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtname=findViewById(R.id.name);
        txtphone=findViewById(R.id.phone);
        txtemail=findViewById(R.id.email);
        txtpassword=findViewById(R.id.password);

        wm=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

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
                        Toast.makeText(RegisterActivity.this, "Must be 11 digit", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }



    public void Register(View view) {


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
                        if(wm.isWifiEnabled()){
                            Toast.makeText(this, "Check internet access", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            AlertDialog.Builder ad = new AlertDialog.Builder(RegisterActivity.this);
                            ad.setMessage("Do you want to open wifi?");
                            ad.setPositiveButton("Open wifi", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    wm.setWifiEnabled(true);
                                }
                            });
                            ad.setNegativeButton("No thanks",null);
                            ad.create().show();
                        }
                    }
                    else {
                        String don=null;
                        don=db.RUNDML("insert into client values(N'"+txtname.getText()+"','"+txtemail.getText()+"','"+txtphone.getText()+"','"+txtpassword.getText()+"')");
                        if(don.equals("Ok"))
                        {
                            AlertDialog.Builder ad=new AlertDialog.Builder(RegisterActivity.this);
                            ad.setTitle("Registeration...");
                            ad.setMessage("Your account has created :)");
                            ad.setIcon(R.drawable.new_icon);
                            ad.setPositiveButton("Go to Login", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                }
                            });
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

    public void Login(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}