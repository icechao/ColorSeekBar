package com.ice.chao.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.math.BigDecimal;

/*************************************************************************
 * 文件说明        :
 *
 * @version V 1.0: ${Version}
 * @FileName     : SlideProgressBar
 * @PackageName  :com.ice.chao.demo
 * @Author       : icechao
 * @Date         : 2018/11/28
 * @Email        :icechliu@gmail.com
 *************************************************************************/
public class ColorSeekBar extends View {

    private BigDecimal spliteNumber = BigDecimal.ZERO;
    private float progress = 0;
    private float max = 100;
    private float pliceNumber = 100;


    public static int STATE_UNUSDED = 0;
    public static int STATE_NORMAL = 1;

    private int unableColor = Color.parseColor("#FAD7D0");
    private int bgColor = Color.parseColor("#FAD7D0");
    private int progressColor = Color.parseColor("#EC5E45");
    private int pliceColor = Color.parseColor("#FAB213");
    private int fillColor = Color.WHITE;

    private int standerdRadius = 12;
    private int paddingLeft;
    private int paddingRight;
    private int bgWidth = 10;
    private int dividingCount = 4;
    private int indexBigRadius = 60;
    private float width;
    private float height;
    private int indexCircleWidth = 2;
    private float contentWidth;
    private Context context;
    private Bitmap cursorNormal;
    private Bitmap cursorUnused;
    private Bitmap cursorPlice;
    private int useable = STATE_NORMAL;
    private float splitMax;


    public int getUnableColor() {
        return unableColor;
    }

    public void setUnableColor(int unableColor) {
        this.unableColor = unableColor;
        invalidate();
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
        invalidate();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        invalidate();

    }

    public int getPliceColor() {
        return pliceColor;
    }

    public void setPliceColor(int pliceColor) {
        this.pliceColor = pliceColor;
        invalidate();
    }

    public int getFillColor() {
        return fillColor;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
        invalidate();
    }

    public int getStanderdRadius() {
        return standerdRadius;
    }

    public void setStanderdRadius(int standerdRadius) {
        this.standerdRadius = standerdRadius;
        invalidate();
    }

    public int getBgWidth() {
        return bgWidth;
    }

    public void setBgWidth(int bgWidth) {
        this.bgWidth = bgWidth;
        invalidate();
    }

    public int getDividingCount() {
        return dividingCount;
    }

    public void setDividingCount(int dividingCount) {
        this.dividingCount = dividingCount;
        invalidate();
    }

    public int getIndexBigRadius() {
        return indexBigRadius;
    }

    public void setIndexBigRadius(int indexBigRadius) {
        this.indexBigRadius = indexBigRadius;
        invalidate();
    }

    public int getIndexCircleWidth() {
        return indexCircleWidth;
    }

    public void setIndexCircleWidth(int indexCircleWidth) {
        this.indexCircleWidth = indexCircleWidth;
        invalidate();
    }

    public Bitmap getCursorNormal() {
        return cursorNormal;
    }

    public void setCursorNormal(Bitmap cursorNormal) {
        this.cursorNormal = cursorNormal;
        invalidate();
    }

    public Bitmap getCursorUnused() {
        return cursorUnused;
    }

    public void setCursorUnused(Bitmap cursorUnused) {
        this.cursorUnused = cursorUnused;
        invalidate();
    }

    public Bitmap getCursorPlice() {
        return cursorPlice;
    }

    public void setCursorPlice(Bitmap cursorPlice) {
        this.cursorPlice = cursorPlice;
        invalidate();
    }

    public int getUseable() {
        return useable;
    }

    public void setUseable(int useable) {
        this.useable = useable;
        invalidate();
    }

    public void setProgress(String string) {
        try {
            progress = new BigDecimal(string).divide(spliteNumber, 0, BigDecimal.ROUND_HALF_UP).floatValue();
        } catch (Exception e) {
            progress = 0f;
        }
        if (progress > 100) {
            progress = 100;
        }
        if (progress < 0) {
            progress = 0;
        }
        invalidate();
        if (null != listener && spliteNumber.compareTo(BigDecimal.ZERO) != 0) {
            listener.setProgress(progress / 100);
        }
    }

    public void setMax(String string) {

        try {
            spliteNumber = new BigDecimal(string).divide(new BigDecimal(100));
        } catch (Exception e) {
            spliteNumber = BigDecimal.ONE;
        }
        invalidate();
    }

    public void setListener(ProgressChangeListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColorSeekBar(Context context) {
        super(context);
        initView(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColorSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ColorSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView(Context context, AttributeSet attrs) {
        this.context = context;
        if (null == attrs) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorSeekBar);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int type = typedArray.getIndex(i);
            switch (type) {
                case R.styleable.ColorSeekBar_bgColor:
                    bgColor = typedArray.getColor(type, bgColor);
                    break;
                case R.styleable.ColorSeekBar_bgWidth:
                    bgWidth = typedArray.getDimensionPixelSize(type, bgWidth);
                    break;
                case R.styleable.ColorSeekBar_standerdRadius:
                    standerdRadius = typedArray.getDimensionPixelSize(type, standerdRadius);
                    break;
                case R.styleable.ColorSeekBar_indexCircleWidth:
                    indexCircleWidth = typedArray.getDimensionPixelSize(type, indexCircleWidth);
                    break;
                case R.styleable.ColorSeekBar_indexBigRadius:
                    indexBigRadius = typedArray.getDimensionPixelSize(type, indexBigRadius);
                    break;
                case R.styleable.ColorSeekBar_progressColor:
                    progressColor = typedArray.getColor(type, progressColor);
                    break;
                case R.styleable.ColorSeekBar_unableColor:
                    unableColor = typedArray.getColor(type, unableColor);
                    break;
                case R.styleable.ColorSeekBar_pliceColor:
                    pliceColor = typedArray.getColor(type, pliceColor);
                    break;
                case R.styleable.ColorSeekBar_pliceNumber:
                    pliceNumber = typedArray.getFloat(type, pliceNumber);
                    break;
                case R.styleable.ColorSeekBar_useable:
                    useable = typedArray.getInt(type, useable);
                    break;
                case R.styleable.ColorSeekBar_dividingCount:
                    dividingCount = typedArray.getInt(type, dividingCount);
                    break;
                case R.styleable.ColorSeekBar_cursorNormal:
                    cursorNormal = BitmapFactory.decodeResource(
                            getResources(), typedArray.getResourceId(type, 0));
                    break;
                case R.styleable.ColorSeekBar_cursorUnused:
                    cursorUnused = BitmapFactory.decodeResource(
                            getResources(), typedArray.getResourceId(type, 0));
                    break;
                case R.styleable.ColorSeekBar_cursorPlice:
                    cursorPlice = BitmapFactory.decodeResource(
                            getResources(), typedArray.getResourceId(type, 0));
                    break;
                case R.styleable.ColorSeekBar_max:
                    try {
                        spliteNumber = new BigDecimal(typedArray.getString(type)).divide(new BigDecimal(100));
                    } catch (Exception e) {
                        spliteNumber = BigDecimal.ONE;
                    }
                    break;
                case R.styleable.ColorSeekBar_progress:
                    try {
                        progress = new BigDecimal(typedArray.getString(type)).divide(spliteNumber, 0, BigDecimal.ROUND_DOWN).floatValue();
                    } catch (Exception e) {
                        progress = 0f;
                    }
                    break;

            }


        }
        typedArray.recycle();
        splitMax = max / 100;
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                float change = progress;
                if (0 == max || spliteNumber.compareTo(BigDecimal.ZERO) == 0) {
                    return true;
                }
                if (useable == STATE_UNUSDED) {
                    return true;
                }
                float downX;
                downX = event.getX();
                if (downX <= paddingLeft) {
                    while (progress > 0) {
                        progress -= splitMax;
                        invalidate();
                    }

                } else if (downX >= (width - paddingRight)) {
                    while (progress < max) {
                        progress += splitMax;
                        invalidate();
                    }
                } else {
                    float v = (downX - paddingLeft) / contentWidth;
                    int newProgress = (int) (v * 100f + 0.5f);
                    while (progress < newProgress) {
                        progress += splitMax;
                        invalidate();
                    }
                    while (progress > newProgress) {
                        progress -= splitMax;
                        invalidate();
                    }
                }

                if (null != listener && progress != change) {
                    listener.change(progress / 100);
                }
                invalidate();
                return true;
            }
        });
        setPadding(getPaddingLeft() + indexBigRadius + indexCircleWidth / 2, getPaddingTop() + indexBigRadius,
                getPaddingRight() + indexBigRadius + indexCircleWidth / 2, getPaddingBottom() + indexBigRadius);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(indexBigRadius * 2 + indexCircleWidth, MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        paddingLeft = getPaddingLeft();
        paddingRight = getPaddingRight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = canvas.getWidth();
        height = canvas.getHeight();
        contentWidth = width - paddingLeft - paddingRight;
        @SuppressLint("DrawAllocation")
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        float dividingWidth = (canvas.getWidth() - paddingLeft - paddingRight) / dividingCount;
        paint.setColor(bgColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(bgWidth);
        drawBackground(canvas, paint, dividingWidth);
        drawProgress(canvas, paint, dividingWidth);
    }

    /**
     * draw progress
     *
     * @param canvas
     * @param paint
     * @param dividingWidth
     */
    private void drawProgress(Canvas canvas, Paint paint, float dividingWidth) {

        if (useable == STATE_UNUSDED) {
            drawCurrent(canvas, paint, 0, cursorUnused);
            return;
        }
        if (max == 0) {
            drawCurrent(canvas, paint, 0, cursorUnused);
            return;
        }
        if (progress > max) {
            progress = max;
        }
        if (pliceNumber > max) {
            pliceNumber = max;
        }
        float progressWidth = contentWidth / max * progress;
        paint.setColor(progressColor);
        if (progress <= pliceNumber) {
            drawProgressNormal(canvas, paint, dividingWidth, progressWidth);
            drawCurrent(canvas, paint, progressWidth, cursorNormal);
            return;
        }
        float pliceWidth = contentWidth / max * pliceNumber;
        Path path = new Path();
        path.moveTo(paddingLeft + standerdRadius, height / 2);
        int plicePointCount = (int) (pliceWidth / dividingWidth);
        int progressPointCount = (int) (progressWidth / dividingWidth);
        float tempWidth = drawPartOne(canvas, paint, dividingWidth, pliceWidth, path, plicePointCount);
        if (plicePointCount == progressPointCount) {
            drawPartTwo(canvas, paint, progressWidth, pliceWidth, tempWidth);
            return;
        }
        drawPartThree(canvas, paint, dividingWidth, progressWidth, plicePointCount, progressPointCount, tempWidth);
    }

    /**
     * @param canvas
     * @param paint
     * @param dividingWidth
     * @param progressWidth
     * @param plicePointCount
     * @param progressPointCount
     * @param tempWidth
     */
    private void drawPartThree(Canvas canvas, Paint paint, float dividingWidth, float progressWidth, int plicePointCount, int progressPointCount, float tempWidth) {
        Path path;
        paint.setColor(pliceColor);
        path = new Path();
        path.moveTo(tempWidth + paddingLeft, height / 2);
        for (int i = plicePointCount + 1; i <= progressPointCount; i++) {
            float currentLeft = dividingWidth * i;
            path.lineTo(currentLeft + paddingLeft - standerdRadius, height / 2);
            path.moveTo(currentLeft + paddingLeft + standerdRadius, height / 2);
            canvas.drawArc(new RectF(currentLeft + paddingLeft - standerdRadius, height / 2 - standerdRadius,
                            paddingLeft + standerdRadius + currentLeft, height / 2 + standerdRadius),
                    0, 360, true, paint);
        }
        tempWidth = dividingWidth * progressPointCount;
        if (progressWidth - tempWidth > indexBigRadius) {
            path.lineTo(progressWidth + paddingLeft - indexCircleWidth / 2, height / 2);
        }
        canvas.drawPath(path, paint);
        drawCurrent(canvas, paint, progressWidth, cursorPlice);
    }

    /**
     * @param canvas
     * @param paint
     * @param progressWidth
     * @param pliceWidth
     * @param tempWidth
     */
    private void drawPartTwo(Canvas canvas, Paint paint, float progressWidth, float pliceWidth, float tempWidth) {
        Path path;
        paint.setColor(pliceColor);
        if (progressWidth - pliceWidth > indexBigRadius) {
            path = new Path();
            path.moveTo(tempWidth + paddingLeft, height / 2);
            path.lineTo(paddingLeft + progressWidth - indexCircleWidth / 2, height / 2);
            canvas.drawPath(path, paint);
        }
        drawCurrent(canvas, paint, progressWidth, cursorPlice);
    }

    /**
     * @param canvas
     * @param paint
     * @param dividingWidth
     * @param pliceWidth
     * @param path
     * @param plicePointCount
     * @return
     */
    private float drawPartOne(Canvas canvas, Paint paint, float dividingWidth, float pliceWidth, Path path, int plicePointCount) {
        for (int i = 0; i <= plicePointCount; i++) {
            float currentLeft = dividingWidth * i;
            if (i != 0) {
                path.lineTo(currentLeft + paddingLeft - standerdRadius, height / 2);
            }
            path.moveTo(currentLeft + paddingLeft + standerdRadius, height / 2);
            canvas.drawArc(new RectF(currentLeft + paddingLeft - standerdRadius, height / 2 - standerdRadius,
                            paddingLeft + standerdRadius + currentLeft, height / 2 + standerdRadius),
                    0, 360, true, paint);
        }
        float tempWidth = dividingWidth * plicePointCount;
        if (pliceWidth - tempWidth > standerdRadius) {
            float nextWidth = tempWidth + dividingWidth;
            if (nextWidth - standerdRadius < pliceWidth) {
                path.lineTo((nextWidth + paddingLeft - standerdRadius), height / 2);
            } else {
                path.lineTo((pliceWidth + paddingLeft), height / 2);
            }
            tempWidth = pliceWidth;
        } else {
            tempWidth += (standerdRadius) + bgWidth / 2;
        }
        paint.setStrokeWidth(bgWidth);
        canvas.drawPath(path, paint);
        return tempWidth;
    }

    /**
     * draw normal progress which smaller the pliceline
     *
     * @param canvas
     * @param paint
     * @param dividingWidth
     * @param width
     */
    private void drawProgressNormal(Canvas canvas, Paint paint, float dividingWidth, double width) {
        Path path = new Path();
        path.moveTo(paddingLeft + standerdRadius, height / 2);
        for (int i = 0; i < width / dividingWidth; i++) {
            float currentLeft = dividingWidth * i;
            if (i != 0) {
                path.lineTo(currentLeft + paddingLeft - standerdRadius, height / 2);
            }
            path.moveTo(currentLeft + paddingLeft + standerdRadius, height / 2);
            canvas.drawArc(new RectF(currentLeft + paddingLeft - standerdRadius, height / 2 - standerdRadius,
                            paddingLeft + standerdRadius + currentLeft, height / 2 + standerdRadius),
                    0, 360, true, paint);
        }
        path.lineTo((float) ((paddingLeft + width) - standerdRadius - indexCircleWidth / 2), height / 2);
        paint.setStrokeWidth(bgWidth);
        canvas.drawPath(path, paint);
    }

    /**
     * draw current pogress point
     *
     * @param canvas
     * @param paint
     * @param progressWidth
     */
    private void drawCurrent(Canvas canvas, Paint paint, float progressWidth, Bitmap cursor) {

        if (null == cursor) {
            if (useable == STATE_UNUSDED) {
                paint.setColor(bgColor);
            }
            drawCircleFill(canvas, paint, paddingLeft + progressWidth - (standerdRadius + (bgWidth / 2)), height / 2 - (standerdRadius + (bgWidth / 2)),
                    paddingLeft + progressWidth + (standerdRadius + (bgWidth / 2)), height / 2 + (standerdRadius + (bgWidth / 2)));
//        RadialGradient radialGradient = new RadialGradient(paddingLeft + progressWidth, height / 2, 50, paint.getColor(), Color.GRAY, Shader.TileMode.CLAMP);
            drawCircleStroke(canvas, paint, indexCircleWidth, paddingLeft + progressWidth - indexBigRadius, height / 2 - indexBigRadius,
                    paddingLeft + progressWidth + indexBigRadius, height / 2 + indexBigRadius);
            if (fillColor != 0) {
                paint.setColor(fillColor);
                int width = indexBigRadius - standerdRadius - indexCircleWidth / 2 - (bgWidth / 2);
                int radius = ((indexBigRadius) + standerdRadius) / 2 + indexCircleWidth;
                drawCircleStroke(canvas, paint, width, paddingLeft + progressWidth - radius, height / 2 - radius,
                        paddingLeft + progressWidth + radius, height / 2 + radius);
            }
        } else {
            canvas.drawBitmap(cursor, null, new RectF(paddingLeft + progressWidth - indexBigRadius, height / 2 - indexBigRadius,
                    paddingLeft + progressWidth + indexBigRadius, height / 2 + indexBigRadius), paint);
        }
    }

    /**
     * draw seek bar backgroud
     *
     * @param canvas
     * @param paint
     * @param dividingWidth
     */
    private void drawBackground(Canvas canvas, Paint paint, float dividingWidth) {
        Path path = new Path();
        for (int i = 0; i < dividingCount; i++) {
            float currentLeft = dividingWidth * i;
            canvas.drawArc(new RectF(currentLeft + paddingLeft - standerdRadius, height / 2 - standerdRadius,
                            paddingLeft + standerdRadius + currentLeft, height / 2 + standerdRadius),
                    0, 360, true, paint);
            path.moveTo(currentLeft + paddingLeft + standerdRadius, height / 2);
            path.lineTo((dividingWidth * (i + 1)) + paddingLeft - standerdRadius, height / 2);
        }
        canvas.drawPath(path, paint);
        canvas.drawArc(new RectF(width - paddingRight - standerdRadius, height / 2 - standerdRadius,
                        width - paddingRight + standerdRadius, height / 2 + standerdRadius),
                0, 360, true, paint);
    }

    /**
     * draw a circle on point witch fill
     *
     * @param canvas
     * @param paint
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private void drawCircleFill(Canvas canvas, Paint paint, float left, float top, float right, float bottom) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawArc(new RectF(left, top, right, bottom),
                0, 360, true, paint);
    }

    /**
     * draw a circle on point which stroke with given width
     *
     * @param canvas
     * @param paint
     * @param width
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    private void drawCircleStroke(Canvas canvas, Paint paint, int width, float left, float top, float right, float bottom) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(width);
        canvas.drawArc(new RectF(left, top, right, bottom),
                0, 360, true, paint);
    }


    private ProgressChangeListener listener;

    public void addProgressChangeListenter(ProgressChangeListener listener) {
        this.listener = listener;
    }


    public interface ProgressChangeListener {
        void change(float precent);

        void setProgress(float progress);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }
}
