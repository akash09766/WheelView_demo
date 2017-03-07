package com.lukedeighton.wheelsample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

public class TextDrawable extends Drawable {

    private final String text;
    private final Paint paint;
    private final Paint paint1;
    private String value;
    private Context context;

    public TextDrawable(Context context, String text, String value) {

        this.text = text;
        this.value = value;
        this.context = context;

        this.paint = new Paint();
        paint.setColor(Color.BLACK);
//        paint.setTextSize(52f);
        paint.setAntiAlias(true);
        paint.setFakeBoldText(true);
//        paint.setShadowLayer(12f, 0, 0, Color.TRANSPARENT);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);


        this.paint1 = new Paint();
        paint1.setColor(Color.RED);
//        paint1.setTextSize(52f);
        paint1.setAntiAlias(true);
        paint1.setFakeBoldText(false);
//        paint1.setShadowLayer(12f, 0, 0, Color.TRANSPARENT);
        paint1.setStyle(Paint.Style.FILL);
        paint1.setTextAlign(Paint.Align.LEFT);

        TypedValue outValue1 = new TypedValue();
        context.getResources().getValue(R.dimen.text_size, outValue1, true);
        float val1 = outValue1.getFloat();

        TypedValue outValue2 = new TypedValue();
        context.getResources().getValue(R.dimen.text_size2, outValue2, true);
        float val2 = outValue2.getFloat();

        paint.setTextSize(val1);
        paint1.setTextSize(val2);
    }

    @Override
    public void draw(Canvas canvas) {


        TypedValue outValue1 = new TypedValue();
        context.getResources().getValue(R.dimen.bound_x_val1, outValue1, true);
        float boundx1 = outValue1.getFloat();


        TypedValue outValue2 = new TypedValue();
        context.getResources().getValue(R.dimen.bound_x_val2, outValue2, true);
        float boundx2 = outValue2.getFloat();


        TypedValue outValue3 = new TypedValue();
        context.getResources().getValue(R.dimen.bound_y_val, outValue3, true);
        float boundy = outValue3.getFloat();


        Rect bounds = getBounds();
        canvas.drawText(text, bounds.centerX() - boundx1 * text.length(), bounds.centerY()
               /* + 15f*/, paint);
        if (value.length() > 4) {

            canvas.drawText(value, bounds.centerX() - boundx2 * text.length(), bounds.centerY()
                    + boundy, paint1);
        } else {

            canvas.drawText(value, bounds.centerX() - boundx1 * text.length(), bounds.centerY()
                    + boundy, paint1);
        }
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        paint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public String getText() {
        return text;
    }
}