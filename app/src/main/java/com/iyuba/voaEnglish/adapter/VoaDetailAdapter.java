package com.iyuba.voaEnglish.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.EvaBean;
import com.iyuba.voaEnglish.model.bean.EvaHistoryBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoaDetailAdapter extends RecyclerView.Adapter<VoaDetailAdapter.CustomViewHolder> {

    private List<VoaDetailBean.VoatextDTO> datas;

    private int sentence = 1;

    private Context context;

    private CallBack callBack;

    private boolean isplay = false;

    private boolean isEva = false;

    private boolean isEvaPlay = false;

    private boolean isHide = true;

    private boolean isShow = false;

    private List<EvaBean.DataDTO.WordsDTO> evaList;

    private String evaData = null;


    private String audioUrl = null;

    private boolean init = false;

    /**
     * 操作的位置
     */
    private int choosePosition = 0;

    private int gColor = Color.parseColor("#48a360");


    private List<EvaHistoryBean.DataDTO> evaHistoryList = new ArrayList<>();


    public VoaDetailAdapter(Context context, List<VoaDetailBean.VoatextDTO> datas) {
        this.context = context;
        this.datas = datas;
    }

    public boolean isIsplay() {
        return isplay;
    }

    public void setIsplay(boolean isplay) {
        this.isplay = isplay;
    }

    public boolean isEva() {
        return isEva;
    }

    public void setEva(boolean eva) {
        this.isEva = eva;
    }

    public boolean isEvaPlay() {
        return isEvaPlay;
    }

    public void setEvaPlay(boolean evaPlay) {
        isEvaPlay = evaPlay;
    }

    public boolean isHide() {
        return isHide;
    }

    public void setHide(boolean hide) {
        isHide = hide;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public int getPosition() {
        return choosePosition;
    }

    public void setPosition(int choosePosition) {
        this.choosePosition = choosePosition;
    }

    public List<EvaBean.DataDTO.WordsDTO> getEvaList() {
        return evaList;
    }

    public void setEvaList(List<EvaBean.DataDTO.WordsDTO> evaList) {
        this.evaList = evaList;
    }

    public VoaDetailBean.VoatextDTO getItem(int position) {
        return datas.get(position);
    }

    public CallBack getCallBack() {
        return callBack;
    }


    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public List<EvaHistoryBean.DataDTO> getEvaHistoryList() {
        return evaHistoryList;
    }

    public void setEvaHistoryList(List<EvaHistoryBean.DataDTO> evaHistoryList) {
        this.evaHistoryList = evaHistoryList;
    }

    @NonNull
    @Override
    public VoaDetailAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_adapter_detail, parent, false);
        return new VoaDetailAdapter.CustomViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull VoaDetailAdapter.CustomViewHolder holder, int position) {
        VoaDetailBean.VoatextDTO voaDetailBean = datas.get(position);


        if (evaList != null) {

            for (int i = 0; i < evaList.size(); i++) {
                if (evaData != null) {
                    evaData = evaData + " " + evaList.get(i).getContent();
                } else {
                    evaData = evaList.get(i).getContent();
                }
            }

            List<String> evaSplit = Arrays.asList((evaData.split(" ")));
            ArrayList<Integer> wordNumber = new ArrayList<>();

            for (int i = 0; i < evaSplit.size(); i++) {
                wordNumber.add(evaSplit.get(i).length());
            }
            //获取到句子中每个单词的位置
            Constant.spannableString[choosePosition] = new SpannableString(evaData);
            int wordLength = 0;
            for (int i = 0; i < wordNumber.size(); i++) {
                if (Double.parseDouble(evaList.get(i).getScore()) < 2.0) {
                    Constant.spannableString[choosePosition].setSpan(new ForegroundColorSpan(Color.RED), wordLength, wordLength + wordNumber.get(i), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                } else if (Double.parseDouble(evaList.get(i).getScore()) > 3.0) {
                    Constant.spannableString[choosePosition].setSpan(new ForegroundColorSpan(gColor), wordLength, wordLength + wordNumber.get(i), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                wordLength = wordNumber.get(i) + wordLength + 1;
            }


            evaData = null;
            evaList = null;
        }


        if (Constant.spannableString[position] != null) {

            holder.nameTxt.setText(Constant.spannableString[position]);

        } else {
            holder.nameTxt.setText(voaDetailBean.getSentence());
        }


        holder.nameTxtTitle.setText(voaDetailBean.getSentenceCn());


        for (int i = 1; i <= datas.size(); i++) {
            holder.sentenceEva.setText((position + 1) + "");
        }


        //提取出一个数组来，为评测后的语句


        if (isEva) {

            if (choosePosition == position) {
                holder.imageEva.setImageResource(R.drawable.tingzhiluyin);

            } else {
                holder.imageEva.setImageResource(R.drawable.luyin);
            }
        } else {
            holder.imageEva.setImageResource(R.drawable.luyin);
        }


        if (isplay) {

            if (choosePosition == position) {
                holder.imageViewPlay.setImageResource(R.drawable.bofang);

            } else {
                holder.imageViewPlay.setImageResource(R.drawable.zanting);
            }
        } else {

            holder.imageViewPlay.setImageResource(R.drawable.zanting);
        }


        if (isEvaPlay) {

            if (choosePosition == position) {

                holder.imageListen.setImageResource(R.drawable.yuyinbofang);

            } else {
                holder.imageListen.setImageResource(R.drawable.erji);
            }
        } else {

            holder.imageListen.setImageResource(R.drawable.erji);
        }

        //图标隐藏
        if (isHide) {

            if (choosePosition == position) {
                holder.linearLayout.setVisibility(View.VISIBLE);
                init = false;

            } else {
                holder.linearLayout.setVisibility(View.GONE);
            }
        } else {
            holder.linearLayout.setVisibility(View.GONE);
        }

        //听力和上传隐藏
        if (isShow || Constant.spannableString[position] != null) {
            holder.scoreText.setText(Constant.evaScore[choosePosition] + "");

            if (choosePosition == position) {
                holder.imageListen.setVisibility(View.VISIBLE);
                holder.imageUpload.setVisibility(View.VISIBLE);
                holder.scoreText.setVisibility(View.VISIBLE);
            } else {
                holder.imageListen.setVisibility(View.INVISIBLE);
                holder.imageUpload.setVisibility(View.INVISIBLE);
                holder.scoreText.setVisibility(View.INVISIBLE);
                if (evaHistoryList != null) {
                    for (int i = 0; i < evaHistoryList.size(); i++) {
                        if (voaDetailBean.getParaId().equals(evaHistoryList.get(i).getParaid() + "") && voaDetailBean.getIdIndex().equals(evaHistoryList.get(i).getIdindex() + "")) {
                            holder.imageListen.setVisibility(View.VISIBLE);
                            holder.imageUpload.setVisibility(View.VISIBLE);
                            holder.scoreText.setText((Math.round(Double.parseDouble(evaHistoryList.get(i).getScore()) * 20)) + "");
                            if(Constant.evaScore[choosePosition]!=0){
                                holder.scoreText.setText(Constant.evaScore[choosePosition] + "");
                            }
                            holder.scoreText.setVisibility(View.VISIBLE);
                            if (Constant.audioURL[position] == null){
                                Constant.audioURL[position] = evaHistoryList.get(i).getUrl();
                            }
                            break;
                        }
                    }

                }
            }
        } else {
            holder.imageListen.setVisibility(View.INVISIBLE);
            holder.imageUpload.setVisibility(View.INVISIBLE);
            holder.scoreText.setVisibility(View.INVISIBLE);
            if (evaHistoryList != null) {
                for (int i = 0; i < evaHistoryList.size(); i++) {
                    //Log.d("chen", (voaDetailBean.getParaId().equals(evaHistoryList.get(i).getParaid() + "") && voaDetailBean.getIdIndex().equals(evaHistoryList.get(i).getIdindex() + "")) + "mmm");
                    if (voaDetailBean.getParaId().equals(evaHistoryList.get(i).getParaid() + "") && voaDetailBean.getIdIndex().equals(evaHistoryList.get(i).getIdindex() + "")) {
                        holder.imageListen.setVisibility(View.VISIBLE);
                        holder.imageUpload.setVisibility(View.VISIBLE);
                        holder.scoreText.setText((Math.round(Double.parseDouble(evaHistoryList.get(i).getScore()) * 20)) + "");
                        if (Constant.evaScore[choosePosition] != 0) {
                            holder.scoreText.setText(Constant.evaScore[choosePosition] + "");
                        }
                        holder.scoreText.setVisibility(View.VISIBLE);
                        if (Constant.audioURL[position] == null){
                            Constant.audioURL[position] = evaHistoryList.get(i).getUrl();
                        }

                        break;
                    }
                }

            }
        }

        if (!init) {
            if (evaHistoryList != null) {
                for (int i = 0; i < evaHistoryList.size(); i++) {
                    if (voaDetailBean.getParaId().equals(evaHistoryList.get(i).getParaid() + "") && voaDetailBean.getIdIndex().equals(evaHistoryList.get(i).getIdindex() + "")) {
                        holder.imageListen.setVisibility(View.VISIBLE);
                        holder.imageUpload.setVisibility(View.VISIBLE);
                        holder.scoreText.setVisibility(View.VISIBLE);
                        holder.scoreText.setText((Math.round(Double.parseDouble(evaHistoryList.get(i).getScore()) * 20)) + "");
                        if (Constant.evaScore[choosePosition] != 0) {
                            holder.scoreText.setText(Constant.evaScore[choosePosition] + "");
                        }
                        if (Constant.audioURL[position] == null){
                            Constant.audioURL[position] = evaHistoryList.get(i).getUrl();
                        }
                        break;
                    }
                }

            }
            init = true;

        }


    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView nameTxtTitle;

        ImageView imageViewPlay;

        ImageView imageEva;

        ImageView imageListen;

        ImageView imageUpload;

        LinearLayout linearLayout;

        TextView scoreText, sentenceEva;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);


            linearLayout = itemView.findViewById(R.id.isShow);
            nameTxt = itemView.findViewById(R.id.item_view);
            nameTxtTitle = itemView.findViewById(R.id.item_view_title);
            imageViewPlay = itemView.findViewById(R.id.play_stop);
            imageEva = itemView.findViewById(R.id.eva_play);
            imageListen = itemView.findViewById(R.id.listen);
            imageUpload = itemView.findViewById(R.id.upload);
            scoreText = itemView.findViewById(R.id.score_eva);
            sentenceEva = itemView.findViewById(R.id.sentence_eva);

            nameTxt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickText(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });
            nameTxtTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickText(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });


            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickPlay(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });

            imageEva.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickEva(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }
            });

            imageListen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        try {
                            callBack.clickListen(getAdapterPosition());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });

            imageUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (callBack != null) {
                        callBack.clickUpload(getAdapterPosition());
                    }
                }
            });
        }


    }

    public interface CallBack {
        void clickPlay(int position) throws IOException;

        void clickEva(int position) throws IOException;

        void clickListen(int position) throws IOException;

        void clickUpload(int position);

        void clickText(int posotion) throws IOException;
    }
}