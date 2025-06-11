package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.model.bean.UserRankingDetailBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;

import java.util.List;

public class UserRankingDetailAdapter extends RecyclerView.Adapter<UserRankingDetailAdapter.CustomViewHolder> {

    private List<UserRankingDetailBean.DataDTO> datas;
    private Context context;

    private int choosePosition = -1;

    private boolean isPlay = false;

    private CallBack callBack;

    public UserRankingDetailAdapter(Context context, List<UserRankingDetailBean.DataDTO> datas) {
        this.context = context;
        this.datas = datas;
    }

    public int getChoosePosition() {
        return choosePosition;
    }

    public void setChoosePosition(int choosePosition) {
        this.choosePosition = choosePosition;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }


    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public UserRankingDetailBean.DataDTO getItem(int position) {
        return datas.get(position);
    }

    @NonNull
    @Override
    public UserRankingDetailAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_ranking_detail_adapter, parent, false);
        return new UserRankingDetailAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRankingDetailAdapter.CustomViewHolder holder, int position) {

        UserRankingDetailBean.DataDTO daily = datas.get(position);

        holder.sentenceScore.setText(daily.getScore() + "");


        String time = daily.getCreateDate();
        String day = time.substring(0, 11);
        holder.createTime.setText(day);


        if ((daily.getParaid() == 0) && ((daily.getIdIndex() == 0))) {
//            Glide.with(context).load(R.drawable.jiaobiao).into(holder.Sentence_rankingdetails);
            holder.sentenceScore.setText( daily.getScore() + "");
            holder.createTime.setText(day);
            holder.sentenceEn.setText("                 文章合成音频");
        }


        Log.d("fang111",Constant.voatextDTOList.get(0).getSentence()+"");
        //原文句子
        for (int i = 0; i < Constant.voatextDTOList.size(); i++) {

            if (((daily.getParaid() + "").equals(Constant.voatextDTOList.get(i).getParaId())) && ((daily.getIdIndex() + "").equals(Constant.voatextDTOList.get(i).getIdIndex()))) {
                holder.sentenceEn.setText(Constant.voatextDTOList.get(i).getSentence());

//                Log.d("fang111",Constant.voatextDTOList.get(i).getSentence()+"");
            }
        }


        if (isPlay) {

            if (choosePosition == position) {
                holder.sentencePlay.setImageResource(R.drawable.bofang);
            } else {
                holder.sentencePlay.setImageResource(R.drawable.zanting);
            }
        } else {

            holder.sentencePlay.setImageResource(R.drawable.zanting);
        }

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView sentenceEn, sentenceCn, sentenceScore,createTime,xiebiao,playTime;

        ImageView sentencePlay;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);


            //sentenceCn = itemView.findViewById(R.id.sentence_cn);

            sentenceEn = itemView.findViewById(R.id.sentence_en);

            sentenceScore = itemView.findViewById(R.id.sentence_score);

            sentencePlay = itemView.findViewById(R.id.sentence_play);

            createTime = itemView.findViewById(R.id.create_time);

            xiebiao = itemView.findViewById(R.id.xiebiao);

            playTime = itemView.findViewById(R.id.play_time);

            sentencePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.clickPlay(getAdapterPosition());
                    }
                }
            });


        }

    }

    public interface CallBack{
        void clickPlay(int position);
    }
}
