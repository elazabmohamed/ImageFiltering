package com.example.imagefiltering;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.imagefiltering.model.User;
import com.example.imagefiltering.model.UserDB;

public class KayitOlActivity extends AppCompatActivity {
    EditText etEmailKayit, etKullaniciAdiKayit, etSifreKayit,  etSifreTekrarKayit;
    TextView tvMesajKayit;
    Button btnKayitOlKayit,btnGeri;
    UserDB db=new UserDB();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);
        LoadComponents();
        btnKayitOlKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etKullaniciAdiKayit.getText().toString().trim();
                String email = etEmailKayit.getText().toString().trim();
                String password = etSifreKayit.getText().toString().trim();
                String  passwordreenter = etSifreTekrarKayit.getText().toString().trim();


                if(name.isEmpty()){
                    tvMesajKayit.setText("Enter Name");
                    tvMesajKayit.setTextColor(Color.RED);
                    tvMesajKayit.setVisibility(View.VISIBLE);
                    etKullaniciAdiKayit.requestFocus();
                    return;
                }
                if(email.isEmpty()){
                    tvMesajKayit.setText("Enter Email");
                    tvMesajKayit.setTextColor(Color.RED);
                    tvMesajKayit.setVisibility(View.VISIBLE);
                    etEmailKayit.requestFocus();
                    return;
                }

                if (password.isEmpty()  || passwordreenter.isEmpty() || !password.equals(passwordreenter)){
                    tvMesajKayit.setText("Password and re-entered password does not match");
                    tvMesajKayit.setTextColor(Color.RED);
                    tvMesajKayit.setVisibility(View.VISIBLE);
                    etSifreKayit.requestFocus();
                    return;
                }


                User user = new User();
                user.setUsername(name);
                user.setPassword(password);
                user.setEmail(email);

                user.setUserID(db.GetAllUsers().size()+1);

                int result = db.AddUser(user);
                if(result==1){
                    tvMesajKayit.setText("User Added to DB");
                    tvMesajKayit.setTextColor(Color.GREEN);
                    tvMesajKayit.setVisibility(View.VISIBLE);
                }
                else{
                    tvMesajKayit.setText("Failed to Add user to DB");
                    tvMesajKayit.setTextColor(Color.RED);
                    tvMesajKayit.setVisibility(View.VISIBLE);
                }
            }
        });

        btnGeri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(ActivityRegister.this,MainActivity.class);
//                startActivity(intent);
                onBackPressed();
            }
        });

    }

    private void LoadComponents(){
        etEmailKayit=(EditText) findViewById(R.id.etEmailKayit);
        etKullaniciAdiKayit=(EditText) findViewById(R.id.etKullaniciAdiKayit);
        etSifreKayit=(EditText) findViewById(R.id.etSifreKayit);
        etSifreTekrarKayit=(EditText) findViewById(R.id.etSifreTekrarKayit);
        tvMesajKayit=(TextView) findViewById(R.id.tvMesajKayit);
        btnKayitOlKayit=(Button) findViewById(R.id.btnKayitOlKayit);
        btnGeri = (Button) findViewById(R.id.btnGeri);
    }
}