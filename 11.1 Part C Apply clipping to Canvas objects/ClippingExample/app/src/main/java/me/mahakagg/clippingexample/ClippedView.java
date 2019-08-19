package me.mahakagg.clippingexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class ClippedView extends View {
    private Paint mPaint;
    private Path mPath;
    // member variables for dimensions
    private int mClipRectRight = (int) getResources().getDimension(R.dimen.clipRectRight);
    private int mClipRectBottom = (int) getResources().getDimension(R.dimen.clipRectBottom);
    private int mClipRectTop = (int) getResources().getDimension(R.dimen.clipRectTop);
    private int mClipRectLeft = (int) getResources().getDimension(R.dimen.clipRectLeft);
    private int mRectInset = (int) getResources().getDimension(R.dimen.rectInset);
    private int mSmallRectOffset = (int) getResources().getDimension(R.dimen.smallRectOffset);
    private int mCircleRadius = (int) getResources().getDimension(R.dimen.circleRadius);
    private int mTextOffset = (int) getResources().getDimension(R.dimen.textOffset);
    private int mTextSize = (int) getResources().getDimension(R.dimen.textSize);
    // member variables for row and column coordinates
    private int mColumnOne = mRectInset;
    private int mColumnnTwo = mColumnOne + mRectInset + mClipRectRight;
    private int mRowOne = mRectInset;
    private int mRowTwo = mRowOne + mRectInset + mClipRectBottom;
    private int mRowThree = mRowTwo + mRectInset + mClipRectBottom;
    private int mRowFour = mRowThree + mRectInset + mClipRectBottom;
    private int mTextRow = mRowFour + (int) (1.5 * mClipRectBottom);
    // rectangle
    private final RectF mRectF;

    public ClippedView(Context context) {
        this(context, null);
    }

    public ClippedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        mPaint = new Paint();
        // Smooth out edges of what is drawn without affecting shape.
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth((int) getResources().getDimension(R.dimen.strokeWidth));
        mPaint.setTextSize((int) getResources().getDimension(R.dimen.textSize));
        mPath = new Path();
        mRectF = new RectF(new Rect(mRectInset, mRectInset, mClipRectRight - mRectInset, mClipRectBottom - mRectInset));
    }

    private void drawClippedRectangle(Canvas canvas) {
        // Set the boundaries of the clipping rectangle for whole picture.
        canvas.clipRect(mClipRectLeft, mClipRectTop, mClipRectRight, mClipRectBottom);
        // Fill the canvas with white.
        // With the clipped rectangle, this only draws inside the clipping rectangle.
        // The rest of the surface remains gray.
        canvas.drawColor(Color.WHITE);
        // Change the color to red and draw a line inside the clipping rectangle.
        mPaint.setColor(Color.RED);
        canvas.drawLine(mClipRectLeft, mClipRectTop, mClipRectRight, mClipRectBottom, mPaint);
        // Set the color to green and draw a circle inside the clipping rectangle.
        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(mCircleRadius, mClipRectBottom - mCircleRadius, mCircleRadius, mPaint);
        // Set the color to blue and draw text aligned with the right edge of the clipping rectangle.
        mPaint.setColor(Color.BLUE);
        // Align the RIGHT side of the text with the origin.
        mPaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(getContext().getString(R.string.clipping), mClipRectRight, mTextOffset, mPaint);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GRAY);
        drawClippedRectangleOne(canvas);
        drawClippedRectangleTwo(canvas);
        drawClippedRectangleThree(canvas);
        drawClippedRectangleFour(canvas);
        drawClippedRectangleFive(canvas);
        drawClippedRectangleSix(canvas);
        drawClippedRectangleSeven(canvas);
        drawSkewedText(canvas);
    }

    // rectangle with no additional clipping
    private void drawClippedRectangleOne(Canvas canvas) {
        canvas.save();
        canvas.translate(mColumnOne, mRowOne);
        drawClippedRectangle(canvas);
        canvas.restore();
    }

    // picture frame effect
    private void drawClippedRectangleTwo(Canvas canvas) {
        // Draw a rectangle that uses the difference between two clipping rectangles to create a picture frame effect.
        canvas.save();
        // Move the origin to the right for the next rectangle.
        canvas.translate(mColumnnTwo, mRowOne);
        // Use the subtraction of two clipping rectangles to create a frame.
        canvas.clipRect(2 * mRectInset, 2 * mRectInset, mClipRectRight - 2 * mRectInset, mClipRectBottom - 2 * mRectInset);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipRect(4 * mRectInset, 4 * mRectInset, mClipRectRight - 4 * mRectInset, mClipRectBottom - 4 * mRectInset, Region.Op.DIFFERENCE);
        } else {
            canvas.clipOutRect(4 * mRectInset, 4 * mRectInset, mClipRectRight - 4 * mRectInset, mClipRectBottom - 4 * mRectInset);
        }
        drawClippedRectangle(canvas);
        canvas.restore();
    }

    // circular clipping created from circular path
    private void drawClippedRectangleThree(Canvas canvas) {
        // Draw a rectangle that uses a circular clipping region created from a circular path.
        canvas.save();
        canvas.translate(mColumnOne, mRowTwo);
        // Clears any lines and curves from the path but unlike reset(), keeps the internal data structure for faster reuse.
        mPath.rewind();
        mPath.addCircle(mCircleRadius, mClipRectBottom - mCircleRadius, mCircleRadius, Path.Direction.CCW);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipPath(mPath, Region.Op.DIFFERENCE);
        } else {
            canvas.clipOutPath(mPath);
        }
        drawClippedRectangle(canvas);
        canvas.restore();
    }

    // intersection of two clipping rectangles
    private void drawClippedRectangleFour(Canvas canvas) {
        // Use the intersection of two rectangles as the clipping region.
        canvas.save();
        canvas.translate(mColumnnTwo, mRowTwo);
        canvas.clipRect(mClipRectLeft, mClipRectTop, mClipRectRight - mSmallRectOffset, mClipRectBottom - mSmallRectOffset);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            canvas.clipRect(mClipRectLeft + mSmallRectOffset, mClipRectTop + mSmallRectOffset, mClipRectRight, mClipRectBottom, Region.Op.INTERSECT);
        } else {
            canvas.clipRect(mClipRectLeft + mSmallRectOffset, mClipRectTop + mSmallRectOffset, mClipRectRight, mClipRectBottom);
        }
        drawClippedRectangle(canvas);
        canvas.restore();
    }

    // clipping region made of combined shapes
    private void drawClippedRectangleFive(Canvas canvas) {
        // You can combine shapes and draw any path to define a clipping region.
        canvas.save();
        canvas.translate(mColumnOne, mRowThree);
        mPath.rewind();
        mPath.addCircle(mClipRectLeft + mRectInset + mCircleRadius, mClipRectTop + mCircleRadius + mRectInset, mCircleRadius, Path.Direction.CCW);
        mPath.addRect(mClipRectRight / 2 - mCircleRadius, mClipRectTop + mCircleRadius + mRectInset, mClipRectRight / 2 + mCircleRadius, mClipRectBottom - mRectInset, Path.Direction.CCW);
        canvas.clipPath(mPath);
        drawClippedRectangle(canvas);
        canvas.restore();
    }

    // rounded rectangle
    private void drawClippedRectangleSix(Canvas canvas) {
        // Use a rounded rectangle. Use mClipRectRight/4 to draw a circle.
        canvas.save();
        canvas.translate(mColumnnTwo, mRowThree);
        mPath.rewind();
        mPath.addRoundRect(mRectF, (float) mClipRectRight / 4, (float) mClipRectRight / 4, Path.Direction.CCW);
        canvas.clipPath(mPath);
        drawClippedRectangle(canvas);
        canvas.restore();
    }

    // clip outside around the rectangle
    private void drawClippedRectangleSeven(Canvas canvas) {
        // Clip the outside around the rectangle.
        canvas.save();
        // Move the origin to the right for the next rectangle.
        canvas.translate(mColumnOne, mRowFour);
        canvas.clipRect(2 * mRectInset, 2 * mRectInset, mClipRectRight - 2 * mRectInset, mClipRectBottom - 2 * mRectInset);
        drawClippedRectangle(canvas);
        canvas.restore();
    }

    // skewed and transform text
    private void drawSkewedText(Canvas canvas) {
        // Draw text with a translate transformation applied.
        canvas.save();
        mPaint.setColor(Color.CYAN);
        // Align the RIGHT side of the text with the origin.
        mPaint.setTextAlign(Paint.Align.LEFT);
        // Apply transformation to canvas.
        canvas.translate(mColumnnTwo, mTextRow);
        // Draw text.
        canvas.drawText(getContext().getString(R.string.translated), 0, 0, mPaint);
        canvas.restore();
        // Draw text with a translate and skew transformations applied.
        canvas.save();
        mPaint.setTextSize(mTextSize);
        mPaint.setTextAlign(Paint.Align.RIGHT);
        // Position text.
        canvas.translate(mColumnnTwo, mTextRow);
        // Apply skew transformation.
        canvas.skew(0.2f, 0.3f);
        canvas.drawText(getContext().getString(R.string.skewed), 0, 0, mPaint);
        canvas.restore();
    }

}
