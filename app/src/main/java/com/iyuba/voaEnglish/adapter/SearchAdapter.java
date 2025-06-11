package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.iyuba.voaEnglish.model.bean.HotWordSearchBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CustomViewHolder> {

    private Context context;

    private List<HotWordSearchBean.DataDTO> list;

    public SearchAdapter(Context context, List<HotWordSearchBean.DataDTO> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SearchAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_adapter, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.CustomViewHolder holder, int position) {

        HotWordSearchBean.DataDTO data = list.get(position);
        holder.nameTxt.setText(data.getTitle());
        holder.nameTxtTitle.setText(data.getTitleCn());
        holder.readCount.setText(data.getReadCount());
        holder.creatTime.setText(data.getCreatTime().substring(0, 10));
        Glide.with(context).load(data.getPic()).into(holder.nameImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, VoaDetailActivity.class);

                Bundle bundle = new Bundle();

                bundle.putInt("voaid", data.getVoaid());

                Constant.voaid = data.getVoaid();


                Constant.audioSound = data.getSound();

                Log.d("fang0147852", "onClick: "+data.getSound());

                bundle.putString("sound",data.getSound());

                bundle.putInt("position",holder.getAdapterPosition());

                Constant.title = data.getTitle();

                intent.putExtras(bundle);

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView nameTxtTitle;

        TextView creatTime, readCount;

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
