package polaroyd.domain.com.polaroyd;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.xiaopo.flying.sticker.BitmapStickerIcon;
import com.xiaopo.flying.sticker.DeleteIconEvent;
import com.xiaopo.flying.sticker.DrawableSticker;
import com.xiaopo.flying.sticker.FlipHorizontallyEvent;
import com.xiaopo.flying.sticker.Sticker;
import com.xiaopo.flying.sticker.StickerIconEvent;
import com.xiaopo.flying.sticker.StickerView;
import com.xiaopo.flying.sticker.TextSticker;
import com.xiaopo.flying.sticker.ZoomIconEvent;


public class ImageEditor extends AppCompatActivity implements View.OnClickListener {

    /* Views */
    ImageView imgView;
    ImageView frameImg;
    Bitmap originalBm;
    RelativeLayout cropView;
    ProgressDialog progDialog;
    TextView captionTxt;
    EditText captionEditText;
    Bitmap adjBm;
   StickerView stv;

    /* Variables*/

    // Array of frame images
    int[] framesList = new int[]{

            R.drawable.fram7,R.drawable.fram8,
            R.drawable.fram9,R.drawable.fram10,
            R.drawable.fram11,
            R.drawable.frame00, R.drawable.frame1,
            R.drawable.frame2, R.drawable.frame3,
            R.drawable.frame4,R.drawable.frame5,
            R.drawable.frame6,R.drawable.frame0
            // Add new frames here...
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_editor);
        // Lock to Portrait orientation
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        /*ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Prossecing..");
        dialog.show();*/
        // Set back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Set Title
        getSupportActionBar().setTitle("Photo Editor");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#051563")));

        // Init AdMob banner
        AdView mAdView = (AdView) findViewById(R.id.adMobBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // Init Views
        imgView = (ImageView)findViewById(R.id.finalImage);
        frameImg = (ImageView)findViewById(R.id.frameImg);
        cropView = (RelativeLayout)findViewById(R.id.cropView);
        captionTxt = (TextView) findViewById(R.id.captionTxt);
        captionEditText = (EditText)findViewById(R.id.captionEditText);


        final HorizontalScrollView filtersView = (HorizontalScrollView)findViewById(R.id.filtersView);
        final RelativeLayout adjustView = (RelativeLayout)findViewById(R.id.adjustView);
        final HorizontalScrollView framesView = (HorizontalScrollView)findViewById(R.id.framesView);

        final Button filtersButt = (Button)findViewById(R.id.filtersButt);
        final Button adjButt = (Button)findViewById(R.id.adjustButt);
        final Button framesButt = (Button)findViewById(R.id.framesButt);
        final Button captionButt = (Button)findViewById(R.id.captionButt);




        // Get image passed from Home.java
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(ImageEditor.this.openFileInput("imagePassed"));
            imgView.setImageBitmap(bitmap);

            // Set original bitmap
            originalBm = bitmap;
        } catch (FileNotFoundException e) { e.printStackTrace(); }





//////////////////////////////////////
            stv=(StickerView)findViewById(R.id.sticker_view) ;

            stv.setOnStickerOperationListener(new StickerView.OnStickerOperationListener(){
                 boolean a=true;
                @Override
                public void onStickerAdded(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerClicked(@NonNull Sticker sticker) {
                         if (sticker instanceof TextSticker){
                            if(a){
                                ((TextSticker) sticker).setTextColor(Color.WHITE);a=false;
                            }else{
                                ((TextSticker) sticker).setTextColor(Color.BLACK);a=true;
                            }
                         }
                }

                @Override
                public void onStickerDeleted(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerDragFinished(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerZoomFinished(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerFlipped(@NonNull Sticker sticker) {

                }

                @Override
                public void onStickerDoubleTapped(@NonNull Sticker sticker1) {
                     stv.remove(sticker1);
                }
            });





        // MARK: - TOOLBAR BUTTONS ------------------------------------------------

        // Filters Button
        filtersButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtersView.setVisibility(View.VISIBLE);
                filtersButt.setBackgroundResource(R.drawable.filters_on);

                adjustView.setVisibility(View.INVISIBLE);
                adjButt.setBackgroundResource(R.drawable.adjust_butt);
                framesView.setVisibility(View.INVISIBLE);
                framesButt.setBackgroundResource(R.drawable.frames_butt);
                captionButt.setBackgroundResource(R.drawable.caption_butt);
                captionEditText.setVisibility(View.INVISIBLE);
            }
        });


        // Adjustment Button
        adjButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adjBm = ((BitmapDrawable)imgView.getDrawable()).getBitmap();

                adjustView.setVisibility(View.VISIBLE);
                adjButt.setBackgroundResource(R.drawable.adjust_on);

                filtersView.setVisibility(View.INVISIBLE);
                filtersButt.setBackgroundResource(R.drawable.filters_butt);
                framesView.setVisibility(View.INVISIBLE);
                framesButt.setBackgroundResource(R.drawable.frames_butt);
                captionButt.setBackgroundResource(R.drawable.caption_butt);
                captionEditText.setVisibility(View.INVISIBLE);
            }
        });

        // Frames Button
        framesButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                framesView.setVisibility(View.VISIBLE);
                framesButt.setBackgroundResource(R.drawable.frames_on);

                adjustView.setVisibility(View.INVISIBLE);
                adjButt.setBackgroundResource(R.drawable.adjust_butt);
                filtersView.setVisibility(View.INVISIBLE);
                filtersButt.setBackgroundResource(R.drawable.filters_butt);
                captionButt.setBackgroundResource(R.drawable.caption_butt);
                captionEditText.setVisibility(View.INVISIBLE);
            }
        });


        // Caption button
        captionButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captionButt.setBackgroundResource(R.drawable.caption_on);
                captionEditText.setVisibility(View.VISIBLE);
                captionEditText.setFocusable(true);

                // Tap Enter on keyboard
                captionEditText.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            /// Hide captionEditTxt
                           // captionTxt.setText(captionEditText.getText().toString());
                            captionEditText.setVisibility(View.INVISIBLE);

                            TextSticker txt=new TextSticker(ImageEditor.this);
                            txt.setTextColor(Color.BLACK);txt.setText(captionEditText.getText().toString());
                            txt.resizeText();
                            stv.addSticker(txt);

                            // Dismiss keyboard
                            InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            in.hideSoftInputFromWindow(captionEditText.getWindowToken(), 0);

                            return true;
                        }
                        return false;
                    }
                });

                framesView.setVisibility(View.INVISIBLE);
                framesButt.setBackgroundResource(R.drawable.frames_butt);
                adjustView.setVisibility(View.INVISIBLE);
                adjButt.setBackgroundResource(R.drawable.adjust_butt);
                filtersView.setVisibility(View.INVISIBLE);
                filtersButt.setBackgroundResource(R.drawable.filters_butt);
            }
        });









        // MARK: - BRIGHTNESS SLIDER ------------------------------------------------------------
        final SeekBar brightnessSeek = (SeekBar)findViewById(R.id.brightnessSeek);
        brightnessSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int intensity = seekBar.getProgress() + -50;
                Filters.brightnessEffect(imgView, adjBm, intensity);
            }
        });





        // MARK: - GENERATE FRAME BUTTONS INTO SCROLLVIEW --------------------------------
        for (int i = 0; i<framesList.length; i++) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.framesLayout);

            // Setup the Buttons
            Button btnTag = new Button(this);
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
            layoutParams.setMargins(5, 0, 0, 0);
            btnTag.setLayoutParams(layoutParams);
            btnTag.setId(i);
            btnTag.setBackgroundResource(framesList[i]);
            btnTag.setOnClickListener(this);

            //add button to the layout
            layout.addView(btnTag);
        }







        // MARK: - PHOTO FILTER BUTTONS --------------------------------------------



        // END PHOTO FILTER BUTTONS ------------------------------------------------

      //  dialog.dismiss();

    }// end onCreate()







    // TAKE SCREENSHOT OF THE cropView (RelativeLayout)
    public void takeScreenshotOfCropView() {
        View v = cropView;
        v.setDrawingCacheEnabled(true);
        Bitmap bmp = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir(R.string.app_name + "", Context.MODE_PRIVATE);
        File filePath = new File(directory,"image.jpg");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(filePath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            // Call Load image
            loadImageFromStorage(filePath.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // LOAD IMAGE FOR SHARING
    private void loadImageFromStorage(String path) {
        try {
            File f = new File(path);
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            ImageView shareImg = (ImageView)findViewById(R.id.shareImg);
            shareImg.setImageBitmap(b);

            // Call shareImage method
           // shareImage();
        }
        catch (FileNotFoundException e)  {
            e.printStackTrace();
        }
    }



    // MARK: - SHARE THE EDITED IMAGE
    public void shareImage() {
        progDialog.dismiss();

        ImageView img = (ImageView) findViewById(R.id.shareImg);
        Bitmap bitmap = ((BitmapDrawable)img.getDrawable()).getBitmap();
        Uri uri = getImageUri(ImageEditor.this, bitmap);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share image on..."));
    }


    // Method to get URI of the eventImage
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "MyMeme", null);
        return Uri.parse(path);
    }












    // MENU ON ACTION BAR
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_editor, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            // DEFAULT BACK BUTTON
            case android.R.id.home:
                this.finish();
                return true;


            // SHARE BUTTON
            case R.id.shareButt:
                // Init a ProgressDialog
                progDialog = new ProgressDialog(this);
                progDialog.setTitle(R.string.app_name);
                progDialog.setMessage("Preparing image for sharing...");
                progDialog.setIndeterminate(false);
                progDialog.setIcon(R.drawable.icon);
                progDialog.show();
                cropView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        takeScreenshotOfCropView();
                        Toast.makeText(ImageEditor.this,"Image saved :)",Toast.LENGTH_SHORT).show();
                        shareImage();
                    }
                }, 2000);


                return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }





    // MARK: SET FRAME IMAGE ------------------------------------------------
    @Override
    public void onClick(View v) {
        frameImg.setImageResource(framesList[v.getId()]);
    }

public void setSticker(int a){
    Drawable drawable =
            ContextCompat.getDrawable(this, a);
    TextSticker txt=new TextSticker(this);
    txt.setTextColor(Color.TRANSPARENT);txt.setText(" ");
    txt.setDrawable(drawable);
    txt.resizeText();
    stv.addSticker(txt);
}

    public void add(View view){
       switch (view.getId()){
           case R.id.e1:{setSticker(R.drawable.e1);break;}
           case R.id.e2:{setSticker(R.drawable.e2);break;}
           case R.id.e3:{setSticker(R.drawable.e3);break;}
           case R.id.e4:{setSticker(R.drawable.e4);break;}
           case R.id.e5:{setSticker(R.drawable.e5);break;}
           case R.id.e6:{setSticker(R.drawable.e6);break;}
           case R.id.e7:{setSticker(R.drawable.e7);break;}
           case R.id.e8:{setSticker(R.drawable.e8);break;}
           case R.id.e9:{setSticker(R.drawable.e9);break;}
           case R.id.e10:{setSticker(R.drawable.e10);break;}
           case R.id.e11:{setSticker(R.drawable.e11);break;}
           case R.id.e12:{setSticker(R.drawable.e12);break;}
           case R.id.e13:{setSticker(R.drawable.e13);break;}
           case R.id.e14:{setSticker(R.drawable.e14);break;}
           case R.id.e15:{setSticker(R.drawable.e15);break;}
           case R.id.e16:{setSticker(R.drawable.e16);break;}
           case R.id.e17:{setSticker(R.drawable.e17);break;}
           case R.id.e18:{setSticker(R.drawable.e18);break;}
           case R.id.e19:{setSticker(R.drawable.e19);break;}
           case R.id.e20:{setSticker(R.drawable.e20);break;}
           case R.id.e21:{setSticker(R.drawable.e21);break;}
           case R.id.e22:{setSticker(R.drawable.e22);break;}
           case R.id.e23:{setSticker(R.drawable.e23);break;}
           case R.id.e24:{setSticker(R.drawable.e24);break;}
           case R.id.e25:{setSticker(R.drawable.e25);break;}
           case R.id.e26:{setSticker(R.drawable.e26);break;}
           case R.id.e27:{setSticker(R.drawable.e27);break;}
           case R.id.e28:{setSticker(R.drawable.e28);break;}
           case R.id.e29:{setSticker(R.drawable.e30);break;}
           case R.id.e31:{setSticker(R.drawable.e31);break;}
           case R.id.f1:{setSticker(R.drawable.f1);break;}
           case R.id.f2:{setSticker(R.drawable.f2);break;}
           case R.id.f3:{setSticker(R.drawable.f3);break;}
           case R.id.f4:{setSticker(R.drawable.f4);break;}
           case R.id.f5:{setSticker(R.drawable.f5);break;}
           case R.id.f6:{setSticker(R.drawable.f6);break;}
           case R.id.f7:{setSticker(R.drawable.f7);break;}
           case R.id.f8:{setSticker(R.drawable.f8);break;}
           case R.id.f9:{setSticker(R.drawable.f9);break;}
           case R.id.f10:{setSticker(R.drawable.f10);break;}
           case R.id.f11:{setSticker(R.drawable.f11);break;}
           case R.id.f12:{setSticker(R.drawable.f12);break;}
           case R.id.f13:{setSticker(R.drawable.f13);break;}
           case R.id.f14:{setSticker(R.drawable.f14);break;}
           case R.id.f15:{setSticker(R.drawable.f15);break;}
           case R.id.f16:{setSticker(R.drawable.f16);break;}
           case R.id.f17:{setSticker(R.drawable.f17);break;}
           case R.id.f18:{setSticker(R.drawable.f18);break;}
           case R.id.f19:{setSticker(R.drawable.f19);break;}
           case R.id.f20:{setSticker(R.drawable.f20);break;}
       }
    }





}//@end
