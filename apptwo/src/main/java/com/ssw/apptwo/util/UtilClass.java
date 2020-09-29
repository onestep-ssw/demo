package com.ssw.apptwo.util;

import android.content.Context;
import android.graphics.Point;
import android.view.WindowManager;

public class UtilClass {


    public static int getWidth(Context context,float sum){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point); //这里是将获取到的屏幕的宽度，放到point中，然后就可以用了
        int itemWidth = (int) (point.x / sum); //将屏幕均分为4.5份，即是可以显示4.5个unit的东西
        return itemWidth;
    }
}
