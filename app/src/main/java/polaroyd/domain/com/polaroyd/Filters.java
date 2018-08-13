package polaroyd.domain.com.polaroyd;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;


public class Filters extends Application {



    // INVERT EFFECT
    public static void  invertEffect(ImageView iv , Bitmap src) {

        // create new bitmap with the same settings as source bitmap
        Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
        // color info
        int A, R, G, B;
        int pixelColor;
        // image size
        int height = src.getHeight();
        int width = src.getWidth();

        // scan through every pixel
        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                // get one pixel
                pixelColor = src.getPixel(x, y);
                // saving alpha channel
                A = Color.alpha(pixelColor);
                // inverting byte for each R/G/B channel
                R = 255 - Color.red(pixelColor);
                G = 255 - Color.green(pixelColor);
                B = 255 - Color.blue(pixelColor);
                // set newly-inverted pixel to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

               iv.setImageBitmap(bmOut);
    }
     //Button background
     public static void  invertEffectForBtn(Button btn , Bitmap src,Context c){
         // create new bitmap with the same settings as source bitmap
         Bitmap bmOut = Bitmap.createBitmap(src.getWidth(), src.getHeight(), src.getConfig());
         // color info
         int A, R, G, B;
         int pixelColor;
         // image size
         int height = src.getHeight();
         int width = src.getWidth();

         // scan through every pixel
         for (int y = 0; y < height; y++)
         {
             for (int x = 0; x < width; x++)
             {
                 // get one pixel
                 pixelColor = src.getPixel(x, y);
                 // saving alpha channel
                 A = Color.alpha(pixelColor);
                 // inverting byte for each R/G/B channel
                 R = 255 - Color.red(pixelColor);
                 G = 255 - Color.green(pixelColor);
                 B = 255 - Color.blue(pixelColor);
                 // set newly-inverted pixel to output image
                 bmOut.setPixel(x, y, Color.argb(A, R, G, B));
             }
         }
         btn.setBackground(new BitmapDrawable(c.getResources(),bmOut));
     }


    // COLOR FILTER EFFECT
    public static void colorFilterEffect(ImageView iv, Bitmap src, double red, double green, double blue) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                // apply filtering on each channel R, G, B
                A = Color.alpha(pixel);
                R = (int)(Color.red(pixel) * red);
                G = (int)(Color.green(pixel) * green);
                B = (int)(Color.blue(pixel) * blue);
                // set new color pixel to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        iv.setImageBitmap(bmOut);
    }





    // CONTRAST EFFECT
    public static void contrastEffect(ImageView iv , Bitmap src, double value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

            iv.setImageBitmap(bmOut);


    }

    public static void contrastEffectForBtn(Button btn , Bitmap src, double value,Context c){
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;
        // get contrast value
        double contrast = Math.pow((100 + value) / 100, 2);

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                // apply filter contrast for every channel R, G, B
                R = Color.red(pixel);
                R = (int)(((((R / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(R < 0) { R = 0; }
                else if(R > 255) { R = 255; }

                G = Color.red(pixel);
                G = (int)(((((G / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(G < 0) { G = 0; }
                else if(G > 255) { G = 255; }

                B = Color.red(pixel);
                B = (int)(((((B / 255.0) - 0.5) * contrast) + 0.5) * 255.0);
                if(B < 0) { B = 0; }
                else if(B > 255) { B = 255; }

                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        btn.setBackground(new BitmapDrawable(c.getResources(),bmOut));
    }




    // GRAYSCALE EFFECT
    public static void grayscaleEffect(ImageView iv, Bitmap bm, double red, double green, double blue) {
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = bm.getWidth();
        int height = bm.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = bm.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(red * R + green * G + blue * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }


            iv.setImageBitmap(bmOut);


    }

    public static void grayscaleEffectForBtn(Button btn, Bitmap bm, double red, double green, double blue,Context c){
        Bitmap bmOut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        // pixel information
        int A, R, G, B;
        int pixel;

        // get image size
        int width = bm.getWidth();
        int height = bm.getHeight();

        // scan through every single pixel
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get one pixel color
                pixel = bm.getPixel(x, y);
                // retrieve color of all channels
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                // take conversion up to one single value
                R = G = B = (int)(red * R + green * G + blue * B);
                // set new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        btn.setBackground(new BitmapDrawable(c.getResources(),bmOut));
    }




    // SEPIA EFFECT
    public static void sepiaEffect(ImageView iv, Bitmap bm, int depth, double red, double green, double blue) {
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        // constant grayscale
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < bm.getWidth(); ++x) {
            for(int y = 0; y < bm.getHeight(); ++y) {
                // get pixel color
                pixel = bm.getPixel(x, y);
                // get color on each channel
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // apply grayscale sample
                B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                // apply intensity level for sepia-toning on each channel
                R += (depth * red);
                if(R > 255) { R = 255; }

                G += (depth * green);
                if(G > 255) { G = 255; }

                B += (depth * blue);
                if(B > 255) { B = 255; }

                // set new pixel color to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image

            iv.setImageBitmap(bmOut);


    }

    public static void sepiaEffectForBtn(Button btn, Bitmap bm, int depth, double red, double green, double blue,Context c){
        Bitmap bmOut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        // constant grayscale
        final double GS_RED = 0.3;
        final double GS_GREEN = 0.59;
        final double GS_BLUE = 0.11;
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < bm.getWidth(); ++x) {
            for(int y = 0; y < bm.getHeight(); ++y) {
                // get pixel color
                pixel = bm.getPixel(x, y);
                // get color on each channel
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // apply grayscale sample
                B = G = R = (int)(GS_RED * R + GS_GREEN * G + GS_BLUE * B);

                // apply intensity level for sepia-toning on each channel
                R += (depth * red);
                if(R > 255) { R = 255; }

                G += (depth * green);
                if(G > 255) { G = 255; }

                B += (depth * blue);
                if(B > 255) { B = 255; }

                // set new pixel color to output image
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        btn.setBackground(new BitmapDrawable(c.getResources(),bmOut));
    }



    // GAMMA EFFECT
    public static void gammaEffect(ImageView iv, Bitmap bm, double red, double green, double blue) {
        // create output image
        Bitmap bmOut = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // constant value curve
        final int    MAX_SIZE = 256;
        final double MAX_VALUE_DBL = 255.0;
        final int    MAX_VALUE_INT = 255;
        final double REVERSE = 1.0;

        // gamma arrays
        int[] gammaR = new int[MAX_SIZE];
        int[] gammaG = new int[MAX_SIZE];
        int[] gammaB = new int[MAX_SIZE];

        // setting values for every gamma channels
        for(int i = 0; i < MAX_SIZE; ++i) {
            gammaR[i] = (int)Math.min(MAX_VALUE_INT,
                    (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / red)) + 0.5));
            gammaG[i] = (int)Math.min(MAX_VALUE_INT,
                    (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / green)) + 0.5));
            gammaB[i] = (int)Math.min(MAX_VALUE_INT,
                    (int)((MAX_VALUE_DBL * Math.pow(i / MAX_VALUE_DBL, REVERSE / blue)) + 0.5));
        }

        // apply gamma table
        for(int x = 0; x < bm.getWidth(); ++x) {
            for(int y = 0; y < bm.getHeight(); ++y) {
                // get pixel color
                pixel = bm.getPixel(x, y);
                A = Color.alpha(pixel);
                // look up gamma
                R = gammaR[Color.red(pixel)];
                G = gammaG[Color.green(pixel)];
                B = gammaB[Color.blue(pixel)];
                // set new color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        iv.setImageBitmap(bmOut);
    }






    // POSTERIZE EFFECT
    public static void posterizeEffect(ImageView iv, Bitmap src, int bitOffset) {
        // get image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // round-off color offset
                R = ((R + (bitOffset / 2)) - ((R + (bitOffset / 2)) % bitOffset) - 1);
                if(R < 0) { R = 0; }
                G = ((G + (bitOffset / 2)) - ((G + (bitOffset / 2)) % bitOffset) - 1);
                if(G < 0) { G = 0; }
                B = ((B + (bitOffset / 2)) - ((B + (bitOffset / 2)) % bitOffset) - 1);
                if(B < 0) { B = 0; }

                // set pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        iv.setImageBitmap(bmOut);
    }





    // BRIGHTNESS EFFECT
    public static void brightnessEffect(ImageView iv, Bitmap src, int value) {
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        iv.setImageBitmap(bmOut);
    }

    public static void brightnessEffectForBtn(Button btn, Bitmap src, int value,Context c){
        // image size
        int width = src.getWidth();
        int height = src.getHeight();
        // create output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());
        // color information
        int A, R, G, B;
        int pixel;

        // scan through all pixels
        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                // get pixel color
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);

                // increase/decrease each channel
                R += value;
                if(R > 255) { R = 255; }
                else if(R < 0) { R = 0; }

                G += value;
                if(G > 255) { G = 255; }
                else if(G < 0) { G = 0; }

                B += value;
                if(B > 255) { B = 255; }
                else if(B < 0) { B = 0; }

                // apply new pixel color to output bitmap
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }
        btn.setBackground(new BitmapDrawable(c.getResources(),bmOut));
    }



    // BOOST EFFECT
    public static void boostColorEffect(ImageView iv, Bitmap src, int type, float percent) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap bmOut = Bitmap.createBitmap(width, height, src.getConfig());

        int A, R, G, B;
        int pixel;

        for(int x = 0; x < width; ++x) {
            for(int y = 0; y < height; ++y) {
                pixel = src.getPixel(x, y);
                A = Color.alpha(pixel);
                R = Color.red(pixel);
                G = Color.green(pixel);
                B = Color.blue(pixel);
                if(type == 1) {
                    R = (int)(R * (1 + percent));
                    if(R > 255) R = 255;
                }
                else if(type == 2) {
                    G = (int)(G * (1 + percent));
                    if(G > 255) G = 255;
                }
                else if(type == 3) {
                    B = (int)(B * (1 + percent));
                    if(B > 255) B = 255;
                }
                bmOut.setPixel(x, y, Color.argb(A, R, G, B));
            }
        }

        // return final image
        iv.setImageBitmap(bmOut);
    }





    // FLIP EFFECT
    public static final int FLIP_VERTICAL = 1;
    public static final int FLIP_HORIZONTAL = 2;

    public static void flipEffect(ImageView iv, Bitmap src, int type) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();
        // if vertical
        if(type == FLIP_VERTICAL) {
            // y = y * -1
            matrix.preScale(1.0f, -1.0f);
        }
        // if horizonal
        else if(type == FLIP_HORIZONTAL) {
            // x = x * -1
            matrix.preScale(-1.0f, 1.0f);
            // unknown type
        }

        Bitmap bmOut = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);

        // return final image
        iv.setImageBitmap(bmOut);
    }






    // GRAIN EFFECT
    public static final int COLOR_MIN = 0x00;
    public static final int COLOR_MAX = 0xFF;

    public static void grainEffect(ImageView iv, Bitmap source) {
        // get image size
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        // a random object
        Random random = new Random();

        int index = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get random color
                int randColor = Color.rgb(random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));
                // OR
                pixels[index] |= randColor;
            }
        }
        // output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, source.getConfig());
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);

        // return final image
        iv.setImageBitmap(bmOut);
    }







    // DIRTY EFFECT
    public static void dirtyEffect(ImageView iv, Bitmap source) {
        // get image size
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        // random object
        Random random = new Random();

        int R, G, B, index = 0, thresHold = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get color
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                // generate threshold
                thresHold = random.nextInt(COLOR_MAX);
                if(R < thresHold && G < thresHold && B < thresHold) {
                    pixels[index] = Color.rgb(COLOR_MIN, COLOR_MIN, COLOR_MIN);
                }
            }
        }
        // output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);

        // return final image
        iv.setImageBitmap(bmOut);
    }





    // SNOW EFFECT
    public static void showEffect(ImageView iv, Bitmap source) {
        // get image size
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        // random object
        Random random = new Random();

        int R, G, B, index = 0, thresHold = 50;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get color
                R = Color.red(pixels[index]);
                G = Color.green(pixels[index]);
                B = Color.blue(pixels[index]);
                // generate threshold
                thresHold = random.nextInt(COLOR_MAX);
                if(R > thresHold && G > thresHold && B > thresHold) {
                    pixels[index] = Color.rgb(COLOR_MAX, COLOR_MAX, COLOR_MAX);
                }
            }
        }
        // output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);

        // return final image
        iv.setImageBitmap(bmOut);
    }





    // REFLECTION EFFECT
    public static void reflectionEffect(ImageView iv, Bitmap originalImage) {
        // gap space between original and reflected
        final int reflectionGap = 4;
        // get image size
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // this will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        // create a Bitmap with the flip matrix applied to it.
        // we only want the bottom half of the image
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);

        // create a new bitmap with same width but taller to fit reflection
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height/2), Bitmap.Config.ARGB_8888);

        // create a new Canvas with the bitmap that's big enough for
        // the image plus gap plus reflection
        Canvas canvas = new Canvas(bitmapWithReflection);
        // draw in the original image
        canvas.drawBitmap(originalImage, 0, 0, null);
        // draw in the gap
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        // draw in the reflection
        canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);

        // create a shader that is a linear gradient that covers the reflection
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff,
                Shader.TileMode.CLAMP);
        // set the paint to use this shader (linear gradient)
        paint.setShader(shader);
        // set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);


        // return final image
        iv.setImageBitmap(bitmapWithReflection);
    }

 // what i add:
 public static void applyFleaEffect(Bitmap source,ImageView iv) {
     // get image size
     int width = source.getWidth();
     int height = source.getHeight();
     int[] pixels = new int[width * height];
     // get pixel array from source
     source.getPixels(pixels, 0, width, 0, 0, width, height);
     // a random object
     Random random = new Random();

     int index = 0;
     // iteration through pixels
     for(int y = 0; y < height; ++y) {
         for(int x = 0; x < width; ++x) {
             // get current index in 2D-matrix
             index = y * width + x;
             // get random color
             int randColor = Color.rgb(random.nextInt(COLOR_MAX),
                     random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));
             // OR
             pixels[index] |= randColor;
         }
     }
     // output bitmap
     Bitmap bmOut = Bitmap.createBitmap(width, height, source.getConfig());
     bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
     iv.setImageBitmap(bmOut);

 }

    public static void applyFleaEffectForBtn(Bitmap source,Button btn,Context c){
        int width = source.getWidth();
        int height = source.getHeight();
        int[] pixels = new int[width * height];
        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);
        // a random object
        Random random = new Random();

        int index = 0;
        // iteration through pixels
        for(int y = 0; y < height; ++y) {
            for(int x = 0; x < width; ++x) {
                // get current index in 2D-matrix
                index = y * width + x;
                // get random color
                int randColor = Color.rgb(random.nextInt(COLOR_MAX),
                        random.nextInt(COLOR_MAX), random.nextInt(COLOR_MAX));
                // OR
                pixels[index] |= randColor;
            }
        }
        // output bitmap
        Bitmap bmOut = Bitmap.createBitmap(width, height, source.getConfig());
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        btn.setBackground(new BitmapDrawable(c.getResources(),bmOut));
    }



}//@end
