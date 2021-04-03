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

public class MainActivity extends AppCompatActivity {
    Button btnKayitOl;
    Button btnGiris;
    Button btnDevam;
    FloatingActionButton btnGaleri, btnCamera;
    User defaultNafiz = new User();
    UserDB db=new UserDB();
    private static final int IMAGE_PICK_CODE = 1000;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String currentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //      Default User
        defaultNafiz.setUsername("nafiz");
        defaultNafiz.setEmail("nafiz@gmail.com");
        defaultNafiz.setPassword("123");
        defaultNafiz.setUserID(1);
        int result = db.AddUser(defaultNafiz);

        btnKayitOl=findViewById(R.id.btnKayit);
        btnGiris = findViewById(R.id.btnGiris);
        btnCamera = findViewById(R.id.btnCamera);
        btnGaleri = findViewById(R.id.btnGaleri);

        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, KayitOlActivity.class);
                startActivity(intent);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,KameraYadaGaleri.class);
                //startActivity(intent);
                //pickImage();
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    createImageFile();
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    dispatchTakePictureIntent();
                    galleryAddPic();

                } catch (ActivityNotFoundException | IOException e) {
                    // display error state to the user
                }

            }
        });

        btnGaleri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this,KameraYadaGaleri.class);
                //startActivity(intent);
                pickImage();

            }
        });



    }

    private  void pickImage(){

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    private  void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 1);
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
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