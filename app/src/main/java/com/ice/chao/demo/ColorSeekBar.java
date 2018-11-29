package com.ice.chao.demo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

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

    private int precent = 0;
    private float progress = 0;
    private float max = 100;
    private float plice = 49;


    public static int STATE_UNUSDED = 0;
    public static int STATE_NORMAL = 1;

    private int backgroudColor = Color.parseColor("#FAD7D0");
    private int progressColor = Color.parseColor("#EC5E45");
    private int pliceColor = Color.parseColor("#FAB213");
    private int fillColor = Color.WHITE;

    private int standerdRadius = 12;
    private int paddingLeft;
    private int paddingRight;
    private int standerdCircleWidth = 10;
    private int dividingCount = 4;
    private int progressRadius = 60;
    private float width;
    private float height;
    private int progressBigCircleWidth = 2;
    private float contentWidth;
    private Context context;
    private Bitmap cursorNormal;
    private Bitmap cursorUnused;
    private Bitmap cursorPlice;
    private int state;
    private float splitMax;

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setPlice(float plice) {
        this.plice = plice;
    }

    public void setBackgroudColor(int backgroudColor) {
        this.backgroudColor = backgroudColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public void setStanderdRadius(int standerdRadius) {
        this.standerdRadius = standerdRadius;
    }

    public void setPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public void setPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }

    public void setStanderdCircleWidth(int standerdCircleWidth) {
        this.standerdCircleWidth = standerdCircleWidth;
    }

    public void setProgressRadius(int progressRadius) {
        this.progressRadius = progressRadius;
    }

    public void setFillColor(int fillColor) {
        this.fillColor = fillColor;
    }

    public void setPliceColor(int pliceColor) {
        this.pliceColor = pliceColor;
    }

    public void setProgressBigCircleWidth(int progressBigCircleWidth) {
        this.progressBigCircleWidth = progressBigCircleWidth;
    }

    public void setCursorNormal(int cursorNormal) {
        this.cursorNormal = BitmapFactory.decodeResource(context.getResources(), cursorNormal);
    }

    public void setCursorUnused(int cursorUnused) {
        this.cursorUnused = BitmapFactory.decodeResource(context.getResources(), cursorUnused);
    }

    public void setCursorPlice(int cursorPlice) {
        this.cursorPlice = BitmapFactory.decodeResource(context.getResources(), cursorPlice);
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setListener(ProgressChangeListener listener) {
        this.listener = listener;
    }

    public ColorSeekBar(Context context) {
        super(context);
        initView(context);
    }

    public ColorSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ColorSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context;
        setPadding(getPaddingLeft() + progressRadius + progressBigCircleWidth / 2, getPaddingTop() + progressRadius,
                getPaddingRight() + progressRadius + progressBigCircleWidth / 2, getPaddingBottom() + progressRadius);
//        cursorUnused = BitmapFactory.decodeResource(context.getResources(), R.drawable.corner_move);
//        cursorNormal = BitmapFactory.decodeResource(context.getResources(), R.drawable.corner_move);
//        cursorPlice = BitmapFactory.decodeResource(context.getResources(), R.drawable.corner_move);
        state = STATE_NORMAL;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST) {
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(progressRadius * 2 + progressBigCircleWidth, MeasureSpec.EXACTLY);
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
        paint.setColor(backgroudColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(standerdCircleWidth);
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

        if (state == STATE_UNUSDED) {
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
        if (plice > max) {
            plice = max;
        }
        float progressWidth = contentWidth / max * progress;
        paint.setColor(progressColor);
        if (progress <= plice) {
            drawProgressNormal(canvas, paint, dividingWidth, progressWidth);
            drawCurrent(canvas, paint, progressWidth, cursorNormal);
            return;
        }
        float pliceWidth = contentWidth / max * plice;
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
        if (progressWidth - tempWidth > progressRadius) {
            path.lineTo(progressWidth + paddingLeft - progressBigCircleWidth / 2, height / 2);
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
        if (progressWidth - pliceWidth > progressRadius) {
            path = new Path();
            path.moveTo(tempWidth + paddingLeft, height / 2);
            path.lineTo(paddingLeft + progressWidth - progressBigCircleWidth / 2, height / 2);
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
            path.lineTo((pliceWidth + paddingLeft), height / 2);
            tempWidth = pliceWidth;
        } else {
            tempWidth += (standerdRadius) + standerdCircleWidth / 2;
        }
        paint.setStrokeWidth(standerdCircleWidth);
        canvas.drawPath(path, paint);
        return tempWidth;
    }

    /**
     * draw normal progress which smaller the plice line
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
        path.lineTo((float) ((paddingLeft + width) - standerdRadius - progressBigCircleWidth / 2), height / 2);
        paint.setStrokeWidth(standerdCircleWidth);
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
            if (state == STATE_UNUSDED) {
                paint.setColor(backgroudColor);
            }
            drawCircleFill(canvas, paint, paddingLeft + progressWidth - (standerdRadius + (standerdCircleWidth / 2)), height / 2 - (standerdRadius + (standerdCircleWidth / 2)),
                    paddingLeft + progressWidth + (standerdRadius + (standerdCircleWidth / 2)), height / 2 + (standerdRadius + (standerdCircleWidth / 2)));
//        RadialGradient radialGradient = new RadialGradient(paddingLeft + progressWidth, height / 2, 50, paint.getColor(), Color.GRAY, Shader.TileMode.CLAMP);
            drawCircleStroke(canvas, paint, progressBigCircleWidth, paddingLeft + progressWidth - progressRadius, height / 2 - progressRadius,
                    paddingLeft + progressWidth + progressRadius, height / 2 + progressRadius);
            if (fillColor != 0) {
                paint.setColor(fillColor);
                int width = progressRadius - standerdRadius - progressBigCircleWidth / 2 - (standerdCircleWidth / 2);
                int radius = ((progressRadius) + standerdRadius) / 2 + progressBigCircleWidth;
                drawCircleStroke(canvas, paint, width, paddingLeft + progressWidth - radius, height / 2 - radius,
                        paddingLeft + progressWidth + radius, height / 2 + radius);
            }
        } else {
            canvas.drawBitmap(cursor, null, new RectF(paddingLeft + progressWidth - progressRadius, height / 2 - progressRadius,
                    paddingLeft + progressWidth + progressRadius, height / 2 + progressRadius), paint);
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (0 == max) {
            return false;
        }
        if (state == STATE_UNUSDED) {
            return false;
        }
        splitMax = max / 300;
        float downX;
        int change = 0;
        downX = event.getX();

        if (downX <= paddingLeft) {
            while (this.progress > 0) {
                this.progress -= splitMax;
                invalidate();
            }
            this.progress = 0;
            change = 0;
            precent = 0;
        } else if (downX >= (width - paddingRight)) {
            while (this.progress < max) {
                this.progress += splitMax;
                invalidate();
            }
            change = 100;
            this.progress = max;
            precent = 100;
        } else {
            float v = (downX - paddingLeft) / contentWidth;
            change = (int) (v * 100);
            float newProgress = max * change / 100f;
            if (change > precent) {
                while (progress < newProgress) {
                    this.progress += splitMax;
                    invalidate();
                }
            } else if (change < precent) {
                while (progress > newProgress) {
                    this.progress -= splitMax;
                    invalidate();
                }
            }
//                progress = max * v;高精度计算
            this.progress = newProgress;//精确两位小数
        }

        if (null != listener && change != precent) {
            listener.change(max, this.progress, plice, change / 100f);
        }
        precent = change;
        invalidate();
        return true;


    }

    private ProgressChangeListener listener;

    public void addProgressChangeListenter(ProgressChangeListener listener) {
        this.listener = listener;
    }


    public interface ProgressChangeListener {
        void change(float max, float progress, float plice, float precent);
    }
}
