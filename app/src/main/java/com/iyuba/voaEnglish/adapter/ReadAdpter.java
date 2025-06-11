package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.iyuba.voaEnglish.R;

import java.util.List;

public class ReadAdpter extends RecyclerView.Adapter<ReadAdpter.ReadViewHoler>{


    private List<String> listEn;
    List<String> listCn;

    private Context context;

    private boolean changeEn = true;

    public ReadAdpter(List<String> listEn, List<String> listCn, Context context) {
        this.listEn = listEn;
        this.listCn = listCn;
        this.context = context;
    }

    public boolean isChangeEn() {
        return changeEn;
    }

    public void setChangeEn(boolean changeEn) {
        this.changeEn = changeEn;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReadViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_read, parent, false);
        return new ReadViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReadViewHoler holder, int position) {

        String sentenceEn = listEn.get(position);

        String sentenceCn = listCn.get(position);

//        holder.wenben.setText("\u3000\u3000"+sentenceEn);
//        holder.wenben2.setText("\u3000\u3000"+sentenceCn);


        holder.wenben.setText(sentenceEn);
        holder.wenben2.setText(sentenceCn);
        //显示隐藏 中英文语句
        if (!changeEn) {
            holder.wenben2.setVisibility(View.VISIBLE);
        } else {
            holder.wenben2.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return  listEn == null ? 0 : listEn.size();
    }

    class ReadViewHoler extends RecyclerView.ViewHolder {
        //第二步   用于解析item里面得东西
        TextView wenben;
        TextView wenben2;

        TextView startTime;
        TextView endTime;


        public ReadViewHoler(@NonNull View itemView) {
            super(itemView);

            wenben = itemView.findViewById(R.id.original_tv_en);
            wenben2 = itemView.findViewById(R.id.original_tv_ch);
//            startTime =itemView.findViewById(R.id.original_tv_play_time);
//            endTime =itemView.findViewById(R.id.original_tv_duration);

        }

    }
}
