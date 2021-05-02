package com.example.imagefiltering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

//package com.exclusive.original.whatsapp_photo_picker;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mukesh.image_processing.ImageProcessor;

import java.io.IOException;
public class KameraYadaGaleri extends AppCompatActivity {
    ImageView  tuneImage, saveImage, infoImage, filterImage;
    ImageFilterView imgViewDisplay;
    Boolean childTemp=false;
    SeekBar sbBrightness,sbSaturation,sbContrast;
    TextView messageBr, messageSa, messageCo;
    Uri uri;
    int title=0;
    Bitmap BitMap, bitmap, b, zeroBitmap, oneBitMap, twoBitMap, threeBitMap;
    ImageProcessor processor = new ImageProcessor();


    ImageView ivOrg, ivGrayScale, ivTint, ivSnow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera_yada_galeri);
        imgViewDisplay = findViewById(R.id.imgViewDisplay);
        saveImage = findViewById(R.id.ivSaveImage);
        infoImage = findViewById(R.id.ivInfoImage);
        filterImage = findViewById(R.id.filterImage);

        uri = getIntent().getParcelableExtra("imageUri");
        imgViewDisplay.setImageURI(uri);
        tuneImage = findViewById(R.id.ivTint);
        //bitmap = BitmapFactory.decodeResource(getResources(), R.id.imgViewDisplay);
        try {
            bitmap = MediaStore.Images.Media.getBitmap(KameraYadaGaleri.this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }


        LinearLayout ParentFilter =  findViewById(R.id.LayoutPop);
        View childFilter = getLayoutInflater().inflate(R.layout.popup_menu_filter,null);

        ivOrg = childFilter.findViewById(R.id.ivOrg);
        ivOrg.setImageBitmap(bitmap);

        ivTint = childFilter.findViewById(R.id.ivTint);
        oneBitMap = processor.tintImage(bitmap, 90);
        ivTint.setImageBitmap(oneBitMap);


        ivGrayScale = childFilter.findViewById(R.id.ivGrayScale);
        twoBitMap = processor.doGreyScale(bitmap);
        ivGrayScale.setImageBitmap(twoBitMap);

        ivSnow = childFilter.findViewById(R.id.ivSnow);
        threeBitMap = processor.applySnowEffect(bitmap);
        ivSnow.setImageBitmap(threeBitMap);




        LinearLayout Parent =  findViewById(R.id.LayoutPop);
        View child = getLayoutInflater().inflate(R.layout.popup_menu,null);
        sbBrightness = (SeekBar)  child.findViewById(R.id.sbBrightness);
       // sbBrightness.setProgress(0);
        messageBr = child.findViewById(R.id.tvBrightnessVal);

        sbSaturation = (SeekBar)  child.findViewById(R.id.sbSaturation);
       // sbSaturation.setProgress(0);
        messageSa = child.findViewById(R.id.tvSaturationVal);

        sbContrast = (SeekBar)  child.findViewById(R.id.sbContrast);
       // sbContrast.setProgress(0);
        messageCo = child.findViewById(R.id.tvContrastVal);


        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ImageInfo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveImageToGallery();
                    Toast.makeText(KameraYadaGaleri.this,"Galeriye Kaydedildi.", Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    Toast.makeText(KameraYadaGaleri.this,"Galeriye Kaydedemedik.", Toast.LENGTH_LONG).show();
                }
            }
        });
        sbBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               // imgViewDisplay.setColorFilter(setBrightness(progress));
                float percentage = (progress / 100.0f);
                imgViewDisplay.setBrightness(percentage+1);
                messageBr.setText(String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float percentage = (progress / 100.0f);
                imgViewDisplay.setContrast(percentage+1);
                messageCo.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float percentage = (progress / 100.0f);
                imgViewDisplay.setSaturation(percentage+1);
                messageSa.setText(String.valueOf(progress));
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

        filterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childTemp==false) {
                    ParentFilter.addView(childFilter);
                    childTemp=true;
                }
                else{
                    ParentFilter.removeAllViews();
                    childTemp=false;
                }
            }
        });
        ivOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgViewDisplay.setImageBitmap(bitmap);
            }
        });
        ivTint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //imgViewDisplay.setDrawingCacheEnabled(true);
                //b = imgViewDisplay.getDrawingCache();
                BitMap = processor.tintImage(bitmap, 90);
                imgViewDisplay.setImageBitmap(BitMap);

            }
        });
        ivGrayScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               //imgViewDisplay.setDrawingCacheEnabled(true);
                //b = imgViewDisplay.getDrawingCache();
                BitMap = processor.doGreyScale(bitmap);
                imgViewDisplay.setImageBitmap(BitMap);

            }
        });

        ivSnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // imgViewDisplay.setDrawingCacheEnabled(true);
              //  b = imgViewDisplay.getDrawingCache();
                BitMap = processor.applySnowEffect(bitmap);
                imgViewDisplay.setImageBitmap(BitMap);
            }
        });
        }



    public void ImageInfo() throws IOException {
        String mimeType = getContentResolver().getType(uri);

        Cursor returnCursor =
                getContentResolver().query(uri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(KameraYadaGaleri.this);

        builder.setMessage("Dosya İsmi: "+returnCursor.getString(nameIndex)+'\n'+
                            "Dosya Boyutu:"+Long.toString(returnCursor.getLong(sizeIndex)/1024)+"KB");
        builder.setTitle("Özellikler:");
        builder.show();
        builder.setCancelable(false);
    }

//    public static PorterDuffColorFilter setBrightness(int progress) {
//        if (progress >=    0)
//        {
//            int value = (int) (progress-51) * 255 / 51;
//
//            return new PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.SRC_OVER);
//
//        }
//        else
//        {
//            int value = (int) (50-progress) * 255 / 50;
//            return new PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP);
//
//
//        }
//    }



    private void saveImageToGallery(){
        imgViewDisplay.buildDrawingCache();
        imgViewDisplay.setDrawingCacheEnabled(true);
        Bitmap b = imgViewDisplay.getDrawingCache();
        title++;
        MediaStore.Images.Media.insertImage(KameraYadaGaleri.this.getContentResolver(), b,String.valueOf(title), "imageimage");
        imgViewDisplay.destroyDrawingCache();
    }
    }
