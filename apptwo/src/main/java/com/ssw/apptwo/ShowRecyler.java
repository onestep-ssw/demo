package com.ssw.apptwo;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.ssw.apptwo.util.DataUtil;
import com.ssw.apptwo.util.RecyclerAdapterDemo;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ShowRecyler extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.loadIcon)
    TextView loadIcon;
    RecyclerAdapterDemo adapterDemo;
    private Context context;
    private List<DataUtil> list=new ArrayList();
    private OkHttpClient client=new OkHttpClient();

    //当前页
    int pageSize=1;

    int count=0;
    //是否正在刷新
    boolean isFlush=false;

    //是不是第一次加载
    public boolean isFirstLoad=true;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_recyler);
        ButterKnife.bind(this);
        context=this;
        //初始化的值
        getData(pageSize);
        //设置移除动画效果
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener(){

            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //由于在改变的时候会执行这个回调所以要夹层坐标的判断

                if(oldScrollY!=0){
                    //最后一个执行刷新
                    boolean b = recyclerView.canScrollVertically(count);
                    if(!b){

                        loadIcon.setText("上拉加载");
                        loadIcon.setVisibility(View.VISIBLE);
                        /*if(list.size()>=count){
                            Toast.makeText(ShowRecyler.this,"人家时又底线的额",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!isFlush){
                            getData(pageSize);
                            isFlush=true;
                        }*/

                    }
                }

            }
        });
    }


    Handler handler=  new  Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what==666){
                if (isFirstLoad){
                    adapterDemo=new RecyclerAdapterDemo(context,list);
                    LinearLayoutManager manager = new LinearLayoutManager(ShowRecyler.this);
                    manager.setOrientation(LinearLayoutManager.VERTICAL);
                    //设置布局管理器
                    recyclerView.setLayoutManager(manager);
                    //设置adapter
                    recyclerView.setAdapter(adapterDemo);

                    isFirstLoad=false;
                }
                adapterDemo.notifyDataSetChanged();
                System.out.println("执行完毕");
                isFlush=false;
            }

        }
    };


    public void getData(int size){
        Runnable r=  new Runnable(){
            @Override
            public void run() {
                String url="http://30.40.27.246:8080/demo/jsonS";
                FormBody body = new FormBody.Builder().add("size", String.valueOf(size)).build();
                Request build = new Request.Builder().post(body).url(url).build();
                Call call = client.newCall(build);
                try {
                    Response response = call.execute();
                    String string = response.body().string();
                    JSONObject jsonObject = JSONObject.parseObject(string);
                    //最大行数
                    count=jsonObject.getInteger("count");
                    String data = jsonObject.getString("data");
                    List<DataUtil> dataUtils = JSONObject.parseArray(data, DataUtil.class);
                    Convert(dataUtils);
                    handler.sendEmptyMessage(666);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
    }


    public void Convert(List<DataUtil> dl){
        for (DataUtil d:dl) {
            String photo = d.getPhoto();
            try {
                Bitmap bitmap = Glide.with(context).asBitmap().load(photo).centerCrop().into(300, 300).get();
                d.setBitmap(bitmap);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        list.addAll(dl);
        pageSize++;
    }
}