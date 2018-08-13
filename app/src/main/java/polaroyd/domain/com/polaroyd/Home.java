package polaroyd.domain.com.polaroyd;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {


    /* Variables */
    MarshMallowPermission mmp = new MarshMallowPermission(this);

//D2CBBB


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        // Lock to Portrait orientation
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
              // logo : w->300  h->250

        /////////////////////////////



        ///////////////////////////////

        // Hide ActionBar
        getSupportActionBar().hide();


        // MARK: - GALLERY BUTTON
        LinearLayout galButt = (LinearLayout)findViewById(R.id.galleryButt);
        galButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mmp.checkPermissionForReadExternalStorage()) {
                    mmp.requestPermissionForReadExternalStorage();
                } else {
                    openGallery();
        }}});


        // MARK: - CAMERA BUTTON
        LinearLayout camButt = (LinearLayout) findViewById(R.id.camButt);
        camButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mmp.checkPermissionForCamera()) {
                    mmp.requestPermissionForCamera();
                } else {
                    openCamera();
                }
            }});





    }// end onCreate()



    Uri outPutfileUri;

    // IMAGE HANDLING METHODS ------------------------------------------------------------------------
    int CAMERA = 0;
    int GALLERY = 1;


    // OPEN CAMERA
    public void openCamera() {
        //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(intent, CAMERA);

        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),
                "MyPhoto.jpg");
        outPutfileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutfileUri);
        startActivityForResult(intent, CAMERA);
    }



    // OPEN GALLERY
    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY);
    }



    // IMAGE PICKED DELEGATE
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bm = null;

            // Camera
            if (requestCode == CAMERA) {
                String uri = outPutfileUri.toString();
                try {
                    bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), outPutfileUri);
                } catch (IOException e) { e.printStackTrace(); }


            // Gallery
            } else if (requestCode == GALLERY) {
                try { bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                } catch (IOException e) { e.printStackTrace(); }
            }


            // Call method to pass image to Other Activity
            Bitmap scaledBitmap = scaleBitmapToMaxSize(800, bm);
            passBitmapToOtherActivity(scaledBitmap);
        }

    }

    //END  IMAGE HANDLING METHODS -------------------------------------------------------------------------------------







    // MARK: - PASS BITMAP TO OTHER ACTIVITY -------------------------------------------------
    public String passBitmapToOtherActivity(Bitmap bitmap) {

        // Save Bitmap into the Device (to pass it to the other Activity)
        String fileName = "imagePassed";
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            FileOutputStream fo = openFileOutput(fileName, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();

            // Go to CreateMeme
            Intent i = new Intent(Home.this, ImageEditor.class);
            startActivity(i);

        } catch (Exception e) {
            e.printStackTrace();
            fileName = null;
        }
        return fileName;
    }





    // MARK: - SCALE BITMAP TO MAX SIZE -------------------------------------------------
    public static Bitmap scaleBitmapToMaxSize(int maxSize, Bitmap bm) {
        int outWidth;
        int outHeight;
        int inWidth = bm.getWidth();
        int inHeight = bm.getHeight();
        if(inWidth > inHeight){
            outWidth = maxSize;
            outHeight = (inHeight * maxSize) / inWidth;
        } else {
            outHeight = maxSize;
            outWidth = (inWidth * maxSize) / inHeight;
        }
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, outWidth, outHeight, false);
        return resizedBitmap;
    }


}//@end
