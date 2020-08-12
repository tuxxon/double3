package com.touchizen.double3;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Utils {
    public static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                view.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
}
