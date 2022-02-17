package com.example.delicious;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ForgetPWActivity extends AppCompatActivity {

    EditText txtemail;
    public static String name,id;
    public static int active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_p_w);

        txtemail=findViewById(R.id.chk_email);
    }

    public void checkemail(View view) {
        Database db=new Database();
        db.ConnectDB();
        ResultSet rs=db.RunSearch("select * from Client where email='"+txtemail.getText()+"'");
        try {
            if(rs.next()){
                Random r=new Random();
                active=r.nextInt(99999-11111+1)+11111;
                String email=rs.getString(3).toString();
                name=rs.getString(2).toString();
                id=rs.getString(1).toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            final String username = "d954472@gmail.com";
                            final String password = "Abb_123456";
                            Properties props = new Properties();
                            props.put("mail.smtp.auth", "true");
                            props.put("mail.smtp.starttls.enable", "true");
                            props.put("mail.smtp.host", "smtp.gmail.com");
                            props.put("mail.smtp.port", "587");

                            Session session = Session.getInstance(props,
                                    new Authenticator() {
                                        protected PasswordAuthentication getPasswordAuthentication() {
                                            return new PasswordAuthentication(username, password);
                                        }
                                    });

                            try {
                                Message message = new MimeMessage(session);
                                message.setFrom(new InternetAddress("d954472@gmail.com"));
                                message.setRecipients(Message.RecipientType.TO,
                                        InternetAddress.parse(txtemail.getText().toString()));

                                message.setSubject("Forget Password  - Delicious App-");
                                message.setText("Dear Mr/Miss : "+rs.getString(2)+"\nYour Activation code is : "+active);
                                Transport.send(message);

                            } catch (MessagingException e) {
                                Toast.makeText(getApplication(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                                throw new RuntimeException(e);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
                Toast.makeText(getApplication(), "Activation code has been sent Check your Email", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ForgetPWActivity.this,CheckActivationActivity.class));

            }
            else
                Toast.makeText(this, "this email not exist!", Toast.LENGTH_SHORT).show();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}