package me.mahakagg.memorygamev2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.Nullable;

import java.util.Random;

public class MemoryGameView extends SurfaceView implements SurfaceHolder.Callback {
    private int colorNumber; // if 0 then tile is yellow; if 1 then tile is red
    private int viewWidth, viewHeight; // width and height of the entire view
    Paint paint; // paint for styling
    Rect rect, rect1; // 2 rectangles - one for border and other for inside fill
    private boolean isOnClick = false; // is the view clicked once
    private Random random;
    private SurfaceHolder surfaceHolder;
    private Canvas mCanvas;

    public MemoryGameView(Context context) {
        this(context, null);
    }

    public MemoryGameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // initialize member variables
        random = new Random();
        paint = new Paint();
        rect = new Rect();
        rect1 = new Rect();
        mCanvas = new Canvas();
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    // return color number to implement the game in MainActivity
    public int getTileColor() {
        return colorNumber;
    }

    // reset the view
    public void reset() {
        isOnClick = false;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        if (surfaceHolder.getSurface().isValid()) {
            // generate random number 0 or 1
            colorNumber = random.nextInt(2);

            // setup rectangles
            rect.set(0, 0, viewWidth, viewHeight);
            rect1.set(10, 10, viewWidth - 10, viewHeight - 10); // set smaller rectangle
            mCanvas.save();

            // if tile is clicked, remove clipping
            if (!isOnClick) {
                // clip rectangle here
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    mCanvas.clipRect(rect1, Region.Op.DIFFERENCE);
                } else {
                    mCanvas.clipOutRect(rect1);
                }
            }
        }
        // setup black border
        // fill entire screen first (border), then fill contents with color
        // entire screen filled
        paint.setColor(Color.BLACK);
        mCanvas.drawRect(rect, paint);
        if (colorNumber == 0) {
            paint.setColor(Color.YELLOW);
        } else {
            paint.setColor(Color.RED);
        }
        mCanvas.drawRect(rect1, paint);
        mCanvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // get view width and height
        viewWidth = w;
        viewHeight = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        // when clicked, set isOnClick to true and invalidate() view to force redraw
        if (event.getAction() == MotionEvent.ACTION_UP) {
            isOnClick = true;
            invalidate();
        }
        return true;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        super.setOnClickListener(l);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        // useful for resetting canvas
        setWillNotDraw(false);
        mCanvas = surfaceHolder.lockCanvas();
        draw(mCanvas);
        surfaceHolder.unlockCanvasAndPost(mCanvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
