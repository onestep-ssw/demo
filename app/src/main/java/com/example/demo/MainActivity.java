package com.example.demo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5Page;
import com.example.demo.util.GlideEngine;
import com.example.demo.util.OneAc;
import com.example.demo.util.TwoAc;
import com.huantansheng.easyphotos.EasyPhotos;
import com.mpaas.nebula.adapter.api.MPNebula;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private  String filePath;
    public static Uri uri;
    public static Intent intent;
    //摄像头事件code
    public static final int REQ_CODE=1;
    //摄像头权限code
    public static final int CAMERA_CODE=33;
    public static MainActivity mainActivity;

    @BindView(R.id.ttt)
    Button ttt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //需要引入这句话注解才能先加载
        ButterKnife.bind(this);
        mainActivity=this;
        findViewById(R.id.demo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OneAc.class);
                startActivityForResult(intent,101);
                /*registermPaas();
                MPNebula.startUrl("file:///android_asset/h5.html");*/
            }
        });
        Button bt = findViewById(R.id.cameraAA);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPhotos.createCamera(MainActivity.this)//参数说明：上下文
                        .setFileProviderAuthority("com.ssw.www.file")//参数说明：见下方`FileProvider的配置`
                        .start(1);
            }
        });
        findViewById(R.id.photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyPhotos.createAlbum(MainActivity.this, false, GlideEngine.getInstance())
                        .start(2);
            }
        });
        ttt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TActivity.class);
                startActivity(intent);
            }
        });
    }

    //注册jsapi mpaas
    public void registermPaas(){
        String[] str=  new String[]{"demo"};
        MPNebula.registerH5Plugin(JsAPI.class.getName(),"","page",str);
    }

    public Uri setUri() throws IOException {
        String path=getExternalFilesDir("")+ File.separator+"exFileImage";
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        File tempFile = File.createTempFile("demo", ".jpg", file);
        filePath = tempFile.getPath();
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            uri= FileProvider.getUriForFile(this, "com.xxx.file", tempFile);
        }else{
            //7.0之前可以
            uri = Uri.fromFile(tempFile);
        }
        return uri;
    }

    public void openCamera(Uri uri){

        intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = checkSelfPermission(Manifest.permission.CAMERA);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_CODE);
                return;
            }else{
                startActivityForResult(intent,REQ_CODE);
            }
        } else {
            startActivityForResult(intent,REQ_CODE);
        }
    }


    /**
     * 权限回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQ_CODE){

        }
    }

    /**
     * 事件回调
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (REQ_CODE==requestCode){
            Log.i("走没走","走没走");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}