package com.example.imagefiltering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.imagefiltering.model.User;
import com.example.imagefiltering.model.UserDB;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GirisYapActivity extends AppCompatActivity {
    Button btnKayitOl, btnGiris;
    User defaultNafiz = new User();
    UserDB db=new UserDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        //      Default User
        defaultNafiz.setUsername("nafiz");
        defaultNafiz.setEmail("nafiz@gmail.com");
        defaultNafiz.setPassword("123");
        defaultNafiz.setUserID(1);
        int result = db.AddUser(defaultNafiz);

        btnKayitOl=findViewById(R.id.btnKayit);
        btnGiris = findViewById(R.id.btnGiris);
        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisYapActivity.this, KayitOlActivity.class);
                startActivity(intent);
            }
        });
    }



    public void Login(View v) {
        EditText etUserName = (EditText) findViewById(R.id.etKullaniciAdi);
        EditText etPassword = (EditText) findViewById(R.id.etSifre);
        TextView tvMessage = (TextView) findViewById(R.id.tvMessage);

        String username = etUserName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (username.equals("")) {
            tvMessage.setText("Enter Your Username");
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setTextColor(Color.RED);
            etUserName.requestFocus();
            return;
        }

        if (password.equals("")) {
            tvMessage.setText("Enter Your Password");
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setTextColor(Color.RED);
            etPassword.requestFocus();
            return;
        }


        //database
//        if(username.equals("admin") && password.equals("12345")){
        if (UserDB.Login(username, password)) {

//            tvMessage.setText("Welcome to Android..");
//            tvMessage.setVisibility(View.VISIBLE);
//            tvMessage.setTextColor(Color.GREEN);

            // Intent homeactivity'ye username verisini text olarak gönderiyoruz

            Intent intent = new Intent(this,KameraYadaGaleri.class);
            //intent.putExtra("data", username);
            startActivity(intent);

        }
    }

}