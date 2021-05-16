package com.example.imagefiltering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;

//package com.exclusive.original.whatsapp_photo_picker;

import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mukesh.image_processing.ImageProcessor;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;


public class KameraYadaGaleri extends AppCompatActivity {
    ImageView  tuneImage, saveImage, infoImage, filterImage, cropImage, textImage,
               drawImage, ivOrg, ivGrayScale, ivTint, ivSnow, ivEngrave, ivShadow, ivFlea;
    ImageFilterView imgViewDisplay;
    LinearLayout Parent;
    View childFilter, child,childText;
    EditText etAddText;
    Boolean childTemp=false;
    SeekBar sbBrightness,sbSaturation,sbContrast;
    TextView messageBr, messageSa, messageCo;
    Uri uri, uriCrop, resultUri;
    int title=0;
    float someGlobalXvariable, someGlobalYvariable;
    float downx = 0, downy = 0, upx = 0, upy = 0;
    ImageProcessor processor = new ImageProcessor();

    Bitmap BitMap;
    Bitmap bitmap;
    Bitmap oneBitMap;
    Bitmap twoBitMap;
    Bitmap threeBitMap;
    Bitmap fourthBitmap;
    Bitmap fifthBitmap;
    Bitmap sixthBitmap;
    Bitmap mutableBitmap;
    Bitmap BitmapText;
    Bitmap resized;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamera_yada_galeri);

        imgViewDisplay = findViewById(R.id.imgViewDisplay);
        saveImage = findViewById(R.id.ivSaveImage);
        infoImage = findViewById(R.id.ivInfoImage);
        filterImage = findViewById(R.id.filterImage);
        cropImage = findViewById(R.id.cropImage);
        textImage = findViewById(R.id.textImage);
        drawImage = findViewById(R.id.drawImage);
        tuneImage = findViewById(R.id.ivTint);

        uri = getIntent().getParcelableExtra("imageUri");
        imgViewDisplay.setImageURI(uri);
        try {
            mutableBitmap = MediaStore.Images.Media.getBitmap(KameraYadaGaleri.this.getContentResolver(), uri);
            bitmap = mutableBitmap.copy(Bitmap.Config.ARGB_8888, true);
            resized = Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.8), (int)(bitmap.getHeight()*0.8), true);
            BitMap = Bitmap.createBitmap(bitmap);
            BitmapText = Bitmap.createBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }



        Parent =  findViewById(R.id.LayoutPop);

        childFilter = getLayoutInflater().inflate(R.layout.popup_menu_filter,null);
        child = getLayoutInflater().inflate(R.layout.popup_menu,null);

        childText = getLayoutInflater().inflate(R.layout.popup_menu_text,null);
        etAddText = childText.findViewById(R.id.etAddText);

        sbBrightness = (SeekBar)  child.findViewById(R.id.sbBrightness);
        messageBr = child.findViewById(R.id.tvBrightnessVal);

        sbSaturation = (SeekBar)  child.findViewById(R.id.sbSaturation);
        messageSa = child.findViewById(R.id.tvSaturationVal);

        sbContrast = (SeekBar)  child.findViewById(R.id.sbContrast);
        messageCo = child.findViewById(R.id.tvContrastVal);

        LoadComponents();

        infoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgViewDisplay.setOnTouchListener(null);
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
                imgViewDisplay.setOnTouchListener(null);
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
                imgViewDisplay.setOnTouchListener(null);
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

        sbSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgViewDisplay.setOnTouchListener(null);
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

        sbContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imgViewDisplay.setOnTouchListener(null);
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

        tuneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgViewDisplay.setOnTouchListener(null);
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
                imgViewDisplay.setOnTouchListener(null);
                if(childTemp==false) {
                    Parent.addView(childFilter);
                    childTemp=true;
                }
                else{
                    Parent.removeAllViews();
                    childTemp=false;
                }
            }

        });

        cropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgViewDisplay.setOnTouchListener(null);


                if(childTemp==false) {
                    uriCrop = getImageUri(getApplicationContext(),BitMap);
                    CropImage.activity(uriCrop)
                            .start(KameraYadaGaleri.this);
                    childTemp=true;
                }
                else{
                    Parent.removeAllViews();
                    childTemp=false;
                }
            }
        });

        textImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgViewDisplay.setOnTouchListener(null);
                if(childTemp==false) {
                    Parent.addView(childText);
                    TextListener();

                    childTemp=true;
                }
                else{
                    Parent.removeAllViews();
                    childTemp=false;
                }

            }
        });

        drawImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                if(childTemp==false) {
                    DrawListener();
                    childTemp=true;
                }
                else{
                    Parent.removeAllViews();
                    childTemp=false;
                }
            }
        });
    }

    private void TextListener(){
        imgViewDisplay.setOnTouchListener( new View.OnTouchListener(){
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                BitmapText = BitMap.copy(Bitmap.Config.ARGB_8888, true);
                Canvas canvas = new Canvas(BitmapText);
                Paint paint = new Paint();

                paint.setColor(Color.TRANSPARENT);
                paint.setStyle(Paint.Style.FILL);
                canvas.drawPaint(paint);

                someGlobalXvariable = e.getX();
                someGlobalYvariable = e.getY();

                paint.setColor(Color.BLACK);
                paint.setTextSize(100);
                canvas.drawText(String.valueOf(etAddText.getText()), someGlobalXvariable, someGlobalYvariable, paint);

                imgViewDisplay.setImageBitmap(BitmapText);

                return true;
            }
        });
    }

    private void DrawListener(){
        imgViewDisplay.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Canvas canvas = new Canvas(BitmapText);
                Paint paint = new Paint();
                paint.setColor(Color.GREEN);

                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        downx = event.getX();
                        downy = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        break;
                    case MotionEvent.ACTION_UP:
                        upx = event.getX();
                        upy = event.getY();
                        canvas.drawLine(downx, downy, upx, upy, paint);
                        imgViewDisplay.invalidate();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                    default:
                        break;
                }
                imgViewDisplay.setImageBitmap(BitmapText);
                return true;
            }
        });
    }

    @Override
    protected void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();
//        intentLoading= new Intent(KameraYadaGaleri.this, LoadingActivity.class);;
//        startActivity(intentLoading);

//        new Thread(new Runnable() {
//            public void run(){
//                LoadComponents();
//            }
//        }).start();


//        LoadComponents();

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                imgViewDisplay.setImageURI(resultUri);
                //      Function to update bitmap thumbnails

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }





    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

    private void saveImageToGallery(){
        imgViewDisplay.buildDrawingCache();
        imgViewDisplay.setDrawingCacheEnabled(true);
        Bitmap b = imgViewDisplay.getDrawingCache();
        title++;
        MediaStore.Images.Media.insertImage(KameraYadaGaleri.this.getContentResolver(), b,String.valueOf(title), "imageimage");
        imgViewDisplay.destroyDrawingCache();
    }


    private void LoadComponents() {

        ivOrg = childFilter.findViewById(R.id.ivOrg);
        ivOrg.setImageBitmap(resized);

        ivTint = childFilter.findViewById(R.id.ivTint);
        oneBitMap = processor.tintImage(resized, 90);
        ivTint.setImageBitmap(oneBitMap);

        ivGrayScale = childFilter.findViewById(R.id.ivGrayScale);
        twoBitMap = processor.doGreyScale(resized);
        ivGrayScale.setImageBitmap(twoBitMap);

        ivSnow = childFilter.findViewById(R.id.ivSnow);
        threeBitMap = processor.applySnowEffect(resized);
        ivSnow.setImageBitmap(threeBitMap);

        ivEngrave = childFilter.findViewById(R.id.ivEngrave);
        fourthBitmap = processor.engrave(resized);
        ivEngrave.setImageBitmap(fourthBitmap);

        ivShadow = childFilter.findViewById(R.id.ivShadow);
        fifthBitmap = processor.createShadow(resized);
        ivShadow.setImageBitmap(fifthBitmap);

        ivFlea = childFilter.findViewById(R.id.ivFlea);
        sixthBitmap = processor.applyFleaEffect(resized);
        ivFlea.setImageBitmap(sixthBitmap);
        setOnclickListeners();

    }

    private void setOnclickListeners(){

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
        ivEngrave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitMap = processor.engrave(bitmap);
                imgViewDisplay.setImageBitmap(BitMap);
            }
        });
        ivShadow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitMap = processor.createShadow(bitmap);
                imgViewDisplay.setImageBitmap(BitMap);
            }
        });
        ivFlea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitMap = processor.applyFleaEffect(bitmap);
                imgViewDisplay.setImageBitmap(BitMap);
            }
        });
    }
}