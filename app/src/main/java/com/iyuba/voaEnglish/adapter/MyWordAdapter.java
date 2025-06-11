package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.model.bean.MyCollectBean;
import com.iyuba.voaEnglish.model.bean.MyWordBean;

import java.util.List;

public class MyWordAdapter extends RecyclerView.Adapter<MyWordAdapter.CustomViewHolder> {
    private Context context;

    private List<MyWordBean.DataDTO> list;

    private CallBack callBack;

    private boolean delWord = false;

    private String wordKey  = null;

    private String wordKeyAudio = null;


    public MyWordAdapter(Context context, List<MyWordBean.DataDTO> list) {
        this.context = context;
        this.list = list;
    }

    public List<MyWordBean.DataDTO> getList() {
        return list;
    }

    public void setList(List<MyWordBean.DataDTO> list) {
        this.list = list;
    }

    public boolean isDelWord() {
        return delWord;
    }

    public void setDelWord(boolean delWord) {
        this.delWord = delWord;
    }

    public String getWordKey() {
        return wordKey;
    }

    public void setWordKey(String wordKey) {
        this.wordKey = wordKey;
    }

    public String getWordKeyAudio() {
        return wordKeyAudio;
    }

    public void setWordKeyAudio(String wordKeyAudio) {
        this.wordKeyAudio = wordKeyAudio;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MyWordAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_word_adapter, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyWordAdapter.CustomViewHolder holder, int position) {

        MyWordBean.DataDTO word = list.get(position);

        holder.myWordDetail.setText(word.getWord());
        holder.myWordPron.setText("["+word.getPron()+"]");
        holder.myWordMeaning.setText(word.getDef());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                        .setTitle("移除生词本")
                        .setPositiveButton("移除", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                                delWord = true;
                                wordKey = word.getWord();

                                if (callBack != null) {
                                    callBack.clickDelWord();
                                }
                            }
                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        })
                        .create();
                alertDialog2.show();
                return true;
            }
        });


        holder.myWordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wordKeyAudio = word.getAudio();
                if (callBack!=null){
                    callBack.clickListenWord();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        ImageView myWordAudio;

        TextView myWordDetail, myWordPron, myWordMeaning;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);


            myWordAudio = itemView.findViewById(R.id.my_word_audio);

            myWordDetail = itemView.findViewById(R.id.my_word_detail);

            myWordPron = itemView.findViewById(R.id.my_word_pron);

            myWordMeaning = itemView.findViewById(R.id.my_word_meaning);
        }
    }

    public interface CallBack {
        void clickDelWord();

        void clickListenWord();
    }
}
