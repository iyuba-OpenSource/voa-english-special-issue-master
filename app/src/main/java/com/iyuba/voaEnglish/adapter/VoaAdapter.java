package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.util.BuddhistCalendar;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.activity.VoaDetailActivity;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.sqlitedb.OriginalTableDb;

import java.util.List;

public class VoaAdapter extends RecyclerView.Adapter<VoaAdapter.CustomViewHolder>{

    private List<VoaBean.DataDTO> datas;
    private Context context;



    public VoaAdapter(Context context, List<VoaBean.DataDTO> datas) {
        this.context = context;
        this.datas = datas;
    }



    @NonNull
    @Override
    public VoaAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_adapter, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VoaAdapter.CustomViewHolder holder, int position) {

        VoaBean.DataDTO voaBean = datas.get(position);
        holder.nameTxt.setText(voaBean.getTitleCn());
        holder.nameTxtTitle.setText(voaBean.getTitle());
        holder.readCount.setText(voaBean.getReadCount());

        //时间不对
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));

        int year = calendar.get(calendar.YEAR);

        int month = calendar.get(calendar.MONTH)+1;
        
        String month_copy = null;

        int day = calendar.get(calendar.DAY_OF_MONTH);

        String day_copy = null;
        
        if((month+"").length()==1){
            month_copy = "0"+ (month+"");
        }else{
            month_copy = (month+"");
        }

        if((day+"").length()==1){
            day_copy = "0"+ (day+"");
        }else{
            day_copy = (day+"");
        }




        if (!(year + "").equals(voaBean.getCreatTime().substring(0, 4))) {
            holder.creatTime.setText(voaBean.getCreatTime().substring(0, 10));
        }else if(!month_copy.equals(voaBean.getCreatTime().substring(5, 7))){
            holder.creatTime.setText(voaBean.getCreatTime().substring(0, 10));
        }else if(!(day_copy+"").equals(voaBean.getCreatTime().substring(8,10))){
            holder.creatTime.setText(voaBean.getCreatTime().substring(0, 10));
        }else{
            holder.creatTime.setText("今天"+voaBean.getCreatTime().substring(11, 16));
        }

        Glide.with(context).load(voaBean.getPic()).into(holder.nameImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //需要更改跳转界面
                Intent intent = new Intent(context, VoaDetailActivity.class);

                Bundle bundle = new Bundle();

                bundle.putInt("voaid", Integer.parseInt(voaBean.getVoaId()));

                Constant.voaid = Integer.parseInt(voaBean.getVoaId());

                Constant.audioSound = "http://staticvip.iyuba.cn/sounds/voa"+voaBean.getSound();



                bundle.putString("sound",voaBean.getSound());

                bundle.putInt("position",holder.getAdapterPosition());

                bundle.putBoolean("iscollect",false);

                Constant.title = voaBean.getTitle();

                intent.putExtras(bundle);

                context.startActivity(intent);


            }
        });

    }

    public List<VoaBean.DataDTO> getDatas() {
        return datas;
    }

    public void setDatas(List<VoaBean.DataDTO> datas) {
        this.datas = datas;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView nameTxtTitle;

        TextView creatTime,readCount;

        ImageView nameImage;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.item_view);
            nameTxtTitle = itemView.findViewById(R.id.item_view_title);
            nameImage = itemView.findViewById(R.id.image_view);
            creatTime = itemView.findViewById(R.id.item_creat_time);
            readCount = itemView.findViewById(R.id.item_read_count);
        }


    }



}
