package edu.ita.honorio.facerecognitionclient;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

@SuppressWarnings("CanBeFinal")
public class OverlayView extends FrameLayout {
    private RectF mRect = new RectF();
    private Paint mBackgroundPaint;
    private Paint mForegroundPaint;
    private int mColor;

    public OverlayView(@NonNull Context context) {
        super(context);
        init();
    }

    public OverlayView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OverlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public OverlayView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        setLayerType(LAYER_TYPE_HARDWARE, null);

        mColor = getResources().getColor(R.color.colorOverlay);

        mForegroundPaint = new Paint();
        mForegroundPaint.setStyle(Paint.Style.STROKE);
        mForegroundPaint.setColor(Color.WHITE);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        float overlayWidth = right - left;
        float overlayHeight = bottom - top;
        float height = overlayHeight * 0.8f;
        float width = height /  1.6f;
        float x = (overlayWidth - width) * 0.5f;
        float y = (overlayHeight - height) * 0.5f;
        mRect.set(x, y, width + x, height + y);
    }

    @Override
    public void onDraw(Canvas canvas) {
        canvas.drawColor(mColor);
        canvas.drawOval(mRect, mBackgroundPaint);
        canvas.drawOval(mRect, mForegroundPaint);
    }
}
