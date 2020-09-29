package com.example.demo;

import android.content.Intent;
import android.net.Uri;

import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.activity.H5ActivityResultManager;

import java.io.IOException;

public class JsAPI extends H5SimplePlugin {


    private String API_1="demo";
    private  MainActivity mainActivity=MainActivity.mainActivity;
    @Override
    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(API_1);
        H5ActivityResultManager.getInstance().put(new DemoActivity());
    }

    @Override
    public boolean handleEvent(H5Event event, H5BridgeContext context) {

        if(API_1.equalsIgnoreCase(event.getAction())){
          /*  try {
                Uri uri = mainActivity.setUri();
                mainActivity.openCamera(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            Intent intent = new Intent(event.getActivity(), DemoActivity.class);
            event.getActivity().startActivityForResult(intent,30,null);
            return true;
        }
        return false;
    }
}
