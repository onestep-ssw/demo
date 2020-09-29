package com.ssw.apptwo.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssw.apptwo.R;

import java.util.List;

public class RecyclerAdapterDemo extends RecyclerView.Adapter<RecyclerAdapterDemo.MyHolder> {

    private Context context;
    private List<DataUtil> list;
    private View inflater;

    public RecyclerAdapterDemo(Context context,List<DataUtil> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater=  LayoutInflater.from(context).inflate(R.layout.item_recycler,parent,false);
        adapt();
        MyHolder myHolder = new MyHolder(inflater);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DataUtil dataUtil = list.get(position);
        holder.myName.setText("姓名："+dataUtil.getName());
        holder.age.setText("年龄："+dataUtil.getAge());
        holder.score.setText("分数："+dataUtil.getScore());
        holder.clazz.setText("班级："+dataUtil.getClazz());
        if (dataUtil.getBitmap()!=null){
            holder.photo.setImageBitmap(dataUtil.getBitmap());
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public void adapt(){
        int width = UtilClass.getWidth(context, 3);

        LinearLayout itemOne=  inflater.findViewById(R.id.itemOne);
        ViewGroup.LayoutParams itemOneLa = itemOne.getLayoutParams();
        itemOneLa.width=width;
        itemOne.setLayoutParams(itemOneLa);


        LinearLayout itemTwo=  inflater.findViewById(R.id.itemTwo);
        ViewGroup.LayoutParams itemTwoLa= itemTwo.getLayoutParams();
        itemTwoLa.width=width;
        itemTwo.setLayoutParams(itemTwoLa);

        LinearLayout itemThree=  inflater.findViewById(R.id.itemThree);
        ViewGroup.LayoutParams itemThreeLa= itemTwo.getLayoutParams();
        itemThreeLa.width=width;
        itemTwo.setLayoutParams(itemThreeLa);

    }

    //内部类  绑定控件
    class  MyHolder extends RecyclerView.ViewHolder{
        TextView age;
        TextView myName;
        TextView score;
        TextView clazz;
        ImageView photo;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            age=itemView.findViewById(R.id.age);
            myName=itemView.findViewById(R.id.myName);
            score=itemView.findViewById(R.id.score);
            clazz=itemView.findViewById(R.id.clazz);
            photo=itemView.findViewById(R.id.photo);
        }
    }
}
