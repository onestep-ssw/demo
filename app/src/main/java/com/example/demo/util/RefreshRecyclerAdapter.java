package com.example.demo.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo.R;

import java.util.ArrayList;
import java.util.List;

public class RefreshRecyclerAdapter  extends RecyclerView.Adapter<RefreshRecyclerAdapter.ViewHolder>{

    private LayoutInflater mInflater;
    private List<String> mTitles=null;

    public RefreshRecyclerAdapter(Context context){
        mInflater= LayoutInflater.from(context);
        this.mTitles=new ArrayList<String>();
        for (int i=0;i<20;i++){
            int index=i+1;
            mTitles.add("item"+index);
        }

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view=mInflater.inflate(R.layout.itemshow,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item_tv.setText(mTitles.get(position));
        holder.itemView.setTag(position);
    }



    @Override
    public int getItemCount() {
        return mTitles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_tv;
        public ViewHolder(View view){
            super(view);
            item_tv = (TextView)view.findViewById(R.id.tv_item);
        }
    }

    //添加数据
    public void addItem(List<String> newDatas) {
        //mTitles.add(position, data);
        //notifyItemInserted(position);
        newDatas.addAll(mTitles);
        mTitles.removeAll(mTitles);
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void addMoreItem(List<String> newDatas) {
        mTitles.addAll(newDatas);
        notifyDataSetChanged();
    }
}
