package com.gmail.task04_database;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrawCustomView extends View {

    private final Paint paint = new Paint();
    private List<Integer> colors = new ArrayList<>();
    private int width, height;
    private float centerX, centerY;
    private int minSide;
    private float radius;
    private float radiusMin;
    private float leftPointX, rightPointX, topPointY, bottomPointY;
    private onCustomViewTouchListener onCustomViewTouchListener;
    private RectF sector1;
    private RectF sector2;
    private RectF sector3;
    private RectF sector4;


    public DrawCustomView(Context context) {
        super(context);
        this.onCustomViewTouchListener = null;
    }

    public DrawCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.onCustomViewTouchListener = null;
    }

    public void setOnCustomViewTouchListener(onCustomViewTouchListener onCustomViewTouchListener) {
        this.onCustomViewTouchListener = onCustomViewTouchListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.GREEN);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        width = getMeasuredWidth();
        height = getMeasuredHeight();

        centerX = width / 2;
        centerY = height / 2;

        minSide = Math.min(width, height);
        radius = minSide / 3;
        radiusMin = radius / 3;

        leftPointX = centerX - radius;
        rightPointX = centerX + radius;
        topPointY = centerY - radius;
        bottomPointY = centerY + radius;

        paint.setColor(colors.get(0));  //YELLOW
        sector1 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector1, 0F, 90F, true, paint);

        paint.setColor(colors.get(1));  //BLUE
        sector2 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector2, 90F, 90F, true, paint);

        paint.setColor(colors.get(2));  //RED
        sector3 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector3, 180F, 90F, true, paint);

        paint.setColor(colors.get(3));  //GREEN
        sector4 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector4, 270F, 90F, true, paint);


        paint.setColor(Color.MAGENTA);
        canvas.drawCircle(centerX, centerY, radiusMin, paint);
    }

    public void shuffleColors() {
        Collections.shuffle(colors);
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = widthMeasureSpec;
        height = heightMeasureSpec;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        String coordinates = "Coordinates [x: " + x + "; y: " + y + "]";
        String colorYellow = "YELLOW";
        String colorBlue = "BLUE";
        String colorRed = "RED";
        String colorGreen = "GREEN";

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            if (radius < (float) (Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)))) {
                if (onCustomViewTouchListener != null) {
                    onCustomViewTouchListener.getCoordinates(coordinates);
                }

            } else if (radiusMin > (float) (Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)))) {
                shuffleColors();

            } else if (radiusMin < (float) (Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)))
                    && radius > (float) (Math.sqrt((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)))) {

                if (onCustomViewTouchListener != null) {
                    if (colors.get(0) == Color.YELLOW && x > centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorYellow);
                    } else if (colors.get(0) == Color.BLUE && x > centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorBlue);
                    }  else if (colors.get(0) == Color.RED && x > centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorRed);
                    } else if (colors.get(0) == Color.GREEN && x > centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorGreen);
                    }
                    if (colors.get(1) == Color.YELLOW && x < centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorYellow);
                    } else if (colors.get(1) == Color.BLUE && x < centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorBlue);
                    }  else if (colors.get(1) == Color.RED && x < centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorRed);
                    } else if (colors.get(1) == Color.GREEN && x < centerX && y > centerY) {
                        onCustomViewTouchListener.getColors(colorGreen);
                    }
                    if (colors.get(2) == Color.YELLOW && x < centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorYellow);
                    } else if (colors.get(2) == Color.BLUE && x < centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorBlue);
                    }  else if (colors.get(2) == Color.RED && x < centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorRed);
                    } else if (colors.get(2) == Color.GREEN && x < centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorGreen);
                    }
                    if (colors.get(3) == Color.YELLOW && x > centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorYellow);
                    } else if (colors.get(3) == Color.BLUE && x > centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorBlue);
                    }  else if (colors.get(3) == Color.RED && x > centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorRed);
                    } else if (colors.get(3) == Color.GREEN && x > centerX && y < centerY) {
                        onCustomViewTouchListener.getColors(colorGreen);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
