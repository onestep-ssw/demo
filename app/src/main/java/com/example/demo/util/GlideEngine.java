package com.example.demo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.huantansheng.easyphotos.engine.ImageEngine;

public class GlideEngine implements ImageEngine {

    private static GlideEngine instance = null;
    //单例模式，私有构造方法
    private GlideEngine() {
    }//获取单例
    public static GlideEngine getInstance() {
        if (null == instance) {
            synchronized (GlideEngine.class) {
                if (null == instance) {
                    instance = new GlideEngine();
                }
            }
        }
        return instance;
    }
    @Override
    public void loadPhoto(@NonNull Context context, @NonNull Uri uri, @NonNull ImageView imageView) {
       // Glide.with(context).load(uri).transition(withCrossFade()).into(imageView);

    }

    @Override
    public void loadGifAsBitmap(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {

    }

    @Override
    public void loadGif(@NonNull Context context, @NonNull Uri gifUri, @NonNull ImageView imageView) {

    }

    @Override
    public Bitmap getCacheBitmap(@NonNull Context context, @NonNull Uri uri, int width, int height) throws Exception {
        return null;
    }
}
