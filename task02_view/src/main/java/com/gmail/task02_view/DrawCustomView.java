package com.gmail.task02_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
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
    private float x, y;
    private MyListener myListener;
    private RectF sector1;
    private RectF sector2;
    private RectF sector3;
    private RectF sector4;


    public DrawCustomView(Context context) {
        super(context);
        this.myListener = null;
//        getWidthAndHeight();
    }

    public DrawCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.myListener = null;
//        getWidthAndHeight();
    }

    public void setMyListener(MyListener myListener) {
        this.myListener = myListener;
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

        leftPointX = centerX - radius;
        rightPointX = centerX + radius;
        topPointY = centerY - radius;
        bottomPointY = centerY + radius;

        int yellow = Color.YELLOW;
        colors.add(yellow);
        paint.setColor(colors.get(0));
        sector1 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector1, 0F, 90F, true, paint);

        int blue = Color.BLUE;
        colors.add(blue);
        paint.setColor(colors.get(1));
        sector2 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector2, 90F, 90F, true, paint);

        int red = Color.RED;
        colors.add(red);
        paint.setColor(colors.get(2));
        sector3 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector3, 180F, 90F, true, paint);

        int green = Color.GREEN;
        colors.add(green);
        paint.setColor(colors.get(3));
        sector4 = new RectF(leftPointX, topPointY, rightPointX, bottomPointY);
        canvas.drawArc(sector4, 270F, 90F, true, paint);

        radiusMin = radius / 3;
        paint.setColor(Color.MAGENTA);
        canvas.drawCircle(centerX, centerY, radiusMin, paint);
    }

    public void shuffleColors() {
        Collections.shuffle(colors);
        invalidate();
    }

//    public void getWidthAndHeight() {
//        if (myListener != null) {
//            myListener.myTouchListener(width, height);
//        }
//    }

}
