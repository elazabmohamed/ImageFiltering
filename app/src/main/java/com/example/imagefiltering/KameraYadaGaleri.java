package com.example.imagefiltering;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.RelativeLayout;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
//package com.exclusive.original.whatsapp_photo_picker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.camerakit.CameraKitView;
//import com.exclusive.original.imagefiltering.Adapters.HomeAdapter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KameraYadaGaleri extends AppCompatActivity {
    CameraKitView cameraKitView;
    RecyclerView btnCapture;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Button btnKamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera_yada_galeri);
        btnCapture= findViewById(R.id.peekRecyclerView);
        cameraKitView = findViewById(R.id.camera);
        btnKamera = findViewById(R.id.btnKamera);

        btnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                try {
//                    createImageFile();
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//                    dispatchTakePictureIntent();
//                    galleryAddPic();
//
//                } catch (ActivityNotFoundException | IOException e) {
//                    // display error state to the user
//                }
            }
        });
    }



//
//
//    @Override
//    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data ) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100) {
//            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
//        }
//    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        cameraKitView.onStart();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        cameraKitView.onResume();
//    }
//
//    @Override
//    protected void onPause() {
//        cameraKitView.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        cameraKitView.onStop();
//        super.onStop();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        cameraKitView.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }



}