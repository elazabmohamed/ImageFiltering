package com.example.imagefiltering;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;

//package com.exclusive.original.whatsapp_photo_picker;

import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.camerakit.CameraKitView;

import java.io.IOException;
import java.net.URI;

public class KameraYadaGaleri extends AppCompatActivity {
    ImageView imgViewDisplay;
    ImageView tuneImage;
    Boolean childTemp=false;
    SeekBar sbBrightness;
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera_yada_galeri);
        imgViewDisplay = findViewById(R.id.imgViewDisplay);
        Uri uri = getIntent().getParcelableExtra("imageUri");
        imgViewDisplay.setImageURI(uri);
        tuneImage = findViewById(R.id.tuneImage);
        LinearLayout Parent =  findViewById(R.id.LayoutPop);
        View child = getLayoutInflater().inflate(R.layout.popup_menu,null);
        sbBrightness = (SeekBar)  child.findViewById(R.id.sbBrightness);
        sbBrightness.setProgress(0);
        message = child.findViewById(R.id.tvBrightnessVal);
        sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgViewDisplay.setColorFilter(setBrightness(progress));
                //imgViewDisplay.setColorFilter();
                message.setText(String.valueOf(progress));
                //ImageInfo(uri);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tuneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childTemp==false) {
                    Parent.addView(child);
                    childTemp=true;
                }
                else{
                    Parent.removeAllViews();
                    childTemp=false;
                }
            }
        });
        }
        
public void ImageInfo(Uri uri) throws IOException {

    ExifInterface exif = new ExifInterface(String.valueOf(uri));
    exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
    exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
    exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
    exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);

    Toast.makeText(KameraYadaGaleri.this,exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF) , Toast.LENGTH_SHORT);
}

    public static PorterDuffColorFilter setBrightness(int progress) {
        if (progress >=    0)
        {
            int value = (int) (progress-51) * 255 / 51;

            return new PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.SRC_OVER);

        }
        else
        {
            int value = (int) (50-progress) * 255 / 50;
            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);


        }
    }
    }
