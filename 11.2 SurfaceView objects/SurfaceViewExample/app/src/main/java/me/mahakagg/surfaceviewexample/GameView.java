package me.mahakagg.surfaceviewexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private boolean mRunning;
    private Thread mGameThread = null;
    private Path mPath;
    private Context mContext;
    private FlashlightCone mFlashlightCone;
    private Paint mPaint;
    private Bitmap mBitmap;
    private RectF mWinnerRect;
    private int mBitmapX;
    private int mBitmapY;
    private int mViewWidth;
    private int mViewHeight;
    private SurfaceHolder mSurfaceHolder;

    @Override
    public void run() {
        Canvas canvas;
        while (mRunning){
            // check if valid surface is available
            if (mSurfaceHolder.getSurface().isValid()) {
                int x = mFlashlightCone.getX();
                int y = mFlashlightCone.getY();
                int radius = mFlashlightCone.getRadius();
                // Lock the canvas. Note that in a more complex app, with more threads, you need to put this into a try/catch block to make sure only one thread is drawing to the surface.
                // Starting with O, you can request a hardware surface with lockHardwareCanvas().
                canvas = mSurfaceHolder.lockCanvas();
                // Fill the canvas with white and draw the bitmap.
                canvas.save();
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(mBitmap, mBitmapX, mBitmapY, mPaint);
                // circle that is the size of flashlight cone to mPath
                mPath.addCircle(x, y, radius, Path.Direction.CCW);
                // Set the circle as the clipping path using the DIFFERENCE operator, so that's what's inside the circle is clipped (not drawn).
                if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    canvas.clipPath(mPath, Region.Op.DIFFERENCE);
                } else {
                    canvas.clipOutPath(mPath);
                }
                // fill everything outside canvas with black
                canvas.drawColor(Color.BLACK);
                // Check whether the the center of the flashlight circle is inside the winning rectangle. If so, color the canvas white, redraw the Android image, and draw the winning message.
                if (x > mWinnerRect.left && x < mWinnerRect.right && y > mWinnerRect.top && y < mWinnerRect.bottom) {
                    canvas.drawColor(Color.WHITE);
                    canvas.drawBitmap(mBitmap, mBitmapX, mBitmapY, mPaint);
                    canvas.drawText("WIN!", mViewWidth / 3, mViewHeight / 2, mPaint);
                }
                // Drawing is finished, so you need to rewind the path, restore the canvas, and release the lock on the canvas.
                mPath.rewind();
                canvas.restore();
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mPaint.setColor(Color.DKGRAY);
        mPath = new Path();
    }

    public void resume() {
        mRunning = true;
        mGameThread = new Thread(this);
        mGameThread.start();
    }

    public void pause() {
        mRunning = false;
        try {
            // Stop the thread (rejoin the main thread)
            mGameThread.join();
        }
        catch (InterruptedException e) {
        }
    }

    // calculates a random location on the screen for the Android image that the user has to find.
    private void setUpBitmap() {
        mBitmapX = (int) Math.floor(Math.random() * (mViewWidth - mBitmap.getWidth()));
        mBitmapY = (int) Math.floor(Math.random() * (mViewHeight - mBitmap.getHeight()));
        mWinnerRect = new RectF(mBitmapX, mBitmapY, mBitmapX + mBitmap.getWidth(), mBitmapY + mBitmap.getHeight());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // store height and width in member variables
        mViewWidth = w;
        mViewHeight = h;
        //create new flashlightCone object
        mFlashlightCone = new FlashlightCone(mViewWidth, mViewHeight);
        // set font size, create bitmap and set it up
        mPaint.setTextSize(mViewHeight / 5);
        mBitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.android);
        setUpBitmap();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        // Invalidate() is inside the case statements because there are
        // many other motion events, and we don't want to invalidate
        // the view for those.
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setUpBitmap();
                updateFrame((int) x, (int) y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                updateFrame((int) x, (int) y);
                invalidate();
                break;
            default:
                // Do nothing.
        }
        return true;
    }

    private void updateFrame(int newX, int newY) {
        mFlashlightCone.update(newX, newY);
    }
}
