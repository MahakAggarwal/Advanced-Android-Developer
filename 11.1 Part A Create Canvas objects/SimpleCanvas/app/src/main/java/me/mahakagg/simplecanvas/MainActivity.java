package me.mahakagg.simplecanvas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private Canvas mCanvas; //canvas has info on what to draw on bitmap
    private Paint mPaint = new Paint(); // info on how to draw
    private Paint mPaintText = new Paint(Paint.UNDERLINE_TEXT_FLAG); //paint for underlined text
    private Bitmap mBitmap; // represents pixels displayed
    private ImageView mImageView; // imageView is the container for the bitmap
    private Rect mRect = new Rect(); //rectangle
    private Rect mBounds = new Rect(); //rectangle
    private static final int OFFSET = 120; // offset is the distance of rectangle from edge of canvas
    private int mOffset = OFFSET;
    private static final int MULTIPLIER = 100; // for randomizing colors
    // member variables for colors
    private int mColorBackground;
    private int mColorRectangle;
    private int mColorAccent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // get color resources and assign them to member variables
        mColorBackground = ResourcesCompat.getColor(getResources(), R.color.colorBackground, null);
        mColorRectangle = ResourcesCompat.getColor(getResources(), R.color.colorRectangle, null);
        mColorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);

        mPaint.setColor(mColorBackground);
        // set mPaintText color to primary dark
        mPaintText.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
        mPaintText.setTextSize(70);
        mImageView = findViewById(R.id.ImageView);
    }

    // in this method, all the interactions with the user and drawing on canvas are implemented

    // steps to always follow - create bitmap, associate it with a view, create canvas with bitmap, draw on canvas, and invalidate to force redraw
    public void drawSomething(View view) {
        int vWidth = view.getWidth();
        int vHeight = view.getHeight();
        int halfWidth = vWidth /2;
        int halfHeight = vHeight /2;

        if (mOffset == OFFSET){
            // when user taps the first time
            //create bitmap, associate it with view, create canvas, fill background, draw text, increase offset
            mBitmap = Bitmap.createBitmap(vWidth, vHeight, Bitmap.Config.ARGB_8888);
            mImageView.setImageBitmap(mBitmap);
            mCanvas = new Canvas(mBitmap); // associate canvas with bitmap, so drawing on canvas draws on bitmap
            mCanvas.drawColor(mColorBackground); //fill canvas with background color
            mCanvas.drawText(getString(R.string.keep_tapping), 100, 100, mPaintText);
            mOffset += OFFSET;
        }
        else{
            if (mOffset < halfWidth && mOffset < halfHeight){
                // offset is smaller than half screen size
                // draw rectangle with computed color and increase the offset

                // change color by subtracting an integer
                mPaint.setColor(mColorRectangle - MULTIPLIER*mOffset);
                mRect.set(mOffset, mOffset, vWidth - mOffset, vHeight - mOffset);
                mCanvas.drawRect(mRect, mPaint);
                // increase offset
                mOffset += OFFSET;
            }
            else{
                // offset => half screen size, draw circle with text 'Done'

                /*
                * get 'done' string, calculate bounding box, calculate x y to draw text at the center of circle
                * */
                mPaint.setColor(mColorAccent);
                mCanvas.drawCircle(halfWidth, halfHeight, halfWidth /3, mPaint);
                String text = getString(R.string.done);
                // get bounding box for text to calculate where to draw it
                mPaintText.getTextBounds(text, 0, text.length(), mBounds);
                // calculate X and Y for text so its centered
                int x = halfWidth - mBounds.centerX();
                int y = halfHeight - mBounds.centerY();
                mCanvas.drawText(text, x, y, mPaintText);
            }
        }
        view.invalidate();
    }
}
