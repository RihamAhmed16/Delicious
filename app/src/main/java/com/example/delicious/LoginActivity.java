package com.example.delicious;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {

    EditText txtuser,txtpass;
    CheckBox chk;
    public static String id,name;
    private WifiManager wm;

    /*private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field connectivityManagerField = conmanClass.getDeclaredField("mService");
        connectivityManagerField.setAccessible(true);
        final Object connectivityManager = connectivityManagerField.get(conman);
        final Class connectivityManagerClass =  Class.forName(connectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = connectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(connectivityManager, enabled);*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtuser=findViewById(R.id.email);
        txtpass=findViewById(R.id.password);
        chk=findViewById(R.id.remember);

        wm=(WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        SharedPreferences sh=getSharedPreferences("Shlogin",MODE_PRIVATE);
        id=sh.getString("id",null);
        name=sh.getString("name",null);
        if((id!=null)||(name!=null))
        {
            startActivity(new Intent(LoginActivity.this,MainUserActivity.class));
        }

    }

    public void Register(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    public void Login(View view) {
        if(txtuser.getText().toString().isEmpty())
        {
            txtuser.setError("Enter Phone / Email");
            txtuser.requestFocus();
        }
        else {
            if (txtpass.getText().toString().isEmpty()) {
                txtpass.setError("Please enter Password");
                txtpass.requestFocus();
            } else {
                Database db = new Database();
                Connection conn = db.ConnectDB();
                if (conn == null) {
                    // Show alert Buttons ( Open Wifi , Open Mobile Data )
                    if(wm.isWifiEnabled()) {
                        Toast.makeText(this, "Internet connection missing", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        AlertDialog.Builder ad = new AlertDialog.Builder(LoginActivity.this);
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

                } else {
                    ResultSet rs = db.RunSearch("select * from client where (phone='" + txtuser.getText() + "' or email='" + txtuser.getText() + "') and password='" + txtpass.getText() + "'");
                    try {
                        if (rs.next()) {
                            if (chk.isChecked()) {
                                getSharedPreferences("Shlogin", MODE_PRIVATE)
                                        .edit()
                                        .putString("id", rs.getString(1))
                                        .putString("name", rs.getString(2))
                                        .commit();
                            }
                            id=rs.getString(1).toString();
                            name=rs.getString(2).toString();
                            startActivity(new Intent(LoginActivity.this, MainUserActivity.class));
                        } else {
                            AlertDialog.Builder ad=new AlertDialog.Builder(LoginActivity.this);
                            ad.setTitle("Login...");
                            ad.setMessage("Invaild user / password :)");
                            ad.setIcon(R.drawable.new_icon);
                            ad.setPositiveButton("Go to Register", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                                }
                            });
                            ad.setNegativeButton("Try again",null);
                            ad.create().show();
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        }
    }

    public void ForgetPassword(View view) {
        startActivity(new Intent(LoginActivity.this,ForgetPWActivity.class));
    }
}