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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.activity.UserRankingListActivity;
import com.iyuba.voaEnglish.model.bean.RankingBean;

import java.util.List;

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.CustomViewHolder>{


    private List<RankingBean.DataDTO> datas;

    private Context context;

    public RankingAdapter(Context context, List<RankingBean.DataDTO> datas) {
        this.context = context;
        this.datas = datas;
    }
    @NonNull
    @Override
    public RankingAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_ranking_adapter, parent, false);
        return new RankingAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RankingAdapter.CustomViewHolder holder, int position) {

        RankingBean.DataDTO rankingBean = datas.get(position);
        holder.rankView.setText(rankingBean.getRanking()+"");
        holder.nameView.setText(rankingBean.getName());
        holder.sentenceView.setText("句子数："+rankingBean.getCount()+"");
        holder.scoreView.setText("总分："+rankingBean.getScores()+"");
        holder.avgView.setText("平均分："+(rankingBean.getScores()/rankingBean.getCount())+"");
        Glide.with(context).load(rankingBean.getImgSrc()).into(holder.imgView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //携带参数跳转界面
                Intent intent = new Intent(context, UserRankingListActivity.class);

                Bundle bundle = new Bundle();

                bundle.putInt("uid",rankingBean.getUid());

                bundle.putString("img",rankingBean.getImgSrc());

                bundle.putString("name", rankingBean.getName());

                bundle.putInt("score",rankingBean.getScores());

                bundle.putInt("sentence",rankingBean.getCount());

                intent.putExtras(bundle);

                //rankingBean.getUid();
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        TextView rankView, nameView, sentenceView ,scoreView, avgView;
        ImageView imgView;

        //CardView imgView;



        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            rankView = itemView.findViewById(R.id.ranking_rank);

            nameView = itemView.findViewById(R.id.name);

            sentenceView = itemView.findViewById(R.id.sentence);

            scoreView = itemView.findViewById(R.id.score);

            avgView = itemView.findViewById(R.id.avg);

            imgView = itemView.findViewById(R.id.ranking_img);



        }
    }
}
