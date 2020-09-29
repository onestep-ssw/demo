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

import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.activity.OnH5ActivityResult;

import java.io.File;
import java.io.IOException;

public class DemoActivity extends AppCompatActivity  implements OnH5ActivityResult {

    private  String filePath;
    public static Uri uri;
    public static Intent intent;
    //摄像头事件code
    public static final int REQ_CODE=1;
    //摄像头权限code
    public static final int CAMERA_CODE=33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        Uri uri = setUri();
        openCamera(uri);

    }

    public Uri setUri(){
        String path=getExternalFilesDir("")+ File.separator+"exFileImage";
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }
        File tempFile = null;
        try {
            tempFile = File.createTempFile("demo", ".jpg", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        onDestroy();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onGetResult(int i, int i1, Intent intent) {
        Log.i("这是什么","");
    }
}