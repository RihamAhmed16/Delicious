package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CheckActivationActivity extends AppCompatActivity {

    TextView username;
    EditText txtactive,txtPasswordForg;
    Button btn_checkcode,btn_updatePass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_activation);

        username=findViewById(R.id.username);
        txtactive=findViewById(R.id.txtactive);
        txtPasswordForg=findViewById(R.id.txtPasswordForg);
        btn_checkcode=findViewById(R.id.btn_checkcode);
        btn_updatePass=findViewById(R.id.btn_updatePass);

        username.setText("Welcome : "+ForgetPWActivity.name);
    }

    public void updatePassword(View view) {
        Database db=new Database();
        db.ConnectDB();
        db.RUNDML("update Client set password='"+txtPasswordForg.getText()+"' where client_no='"+ForgetPWActivity.id+"'");
        Toast.makeText(this, "password updated successfully", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(CheckActivationActivity.this,LoginActivity.class));
    }

    public void checkActivation(View view) {
        if(Integer.parseInt(txtactive.getText().toString())==ForgetPWActivity.active)
        {
            txtPasswordForg.setVisibility(View.VISIBLE);
            btn_updatePass.setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(this, "Invaild activation code", Toast.LENGTH_SHORT).show();
    }
}