package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.activity.LoginActivity;
import com.iyuba.voaEnglish.activity.VoaDetailActivity;
import com.iyuba.voaEnglish.model.bean.MyCollectBean;

import java.io.IOException;
import java.util.List;

public class PageCollectAdapter extends RecyclerView.Adapter<PageCollectAdapter.CustomViewHolder>{

    private List<MyCollectBean.DataDTO> list ;

    private Context context;

    private CallBack callBack;

    private boolean del = false;

    private int pageVoaid = 0;

    public PageCollectAdapter(Context context,List<MyCollectBean.DataDTO> list){
        this.context = context;
        this.list = list;
    }

    public boolean isDel() {
        return del;
    }

    public void setDel(boolean del) {
        this.del = del;
    }

    public int getPageVoaid() {
        return pageVoaid;
    }

    public void setPageVoaid(int pageVoaid) {
        this.pageVoaid = pageVoaid;
    }

    public CallBack getCallBack() {
        return callBack;
    }

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public PageCollectAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_page_collect,parent,false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PageCollectAdapter.CustomViewHolder holder, int position) {

        MyCollectBean.DataDTO collect = list.get(position);
        Glide.with(context).load(collect.getPic()).into(holder.imageViewz);
        holder.collectView.setText(collect.getTitleCn());
        holder.collectCreateTime.setText(collect.getCreateTime().substring(0, 10));
        holder.collectClass.setText(collect.getCategoryName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //需要更改跳转界面
//                Intent intent = new Intent(context, VoaDetailActivity.class);
//
//                Bundle bundle = new Bundle();
//
//                bundle.putInt("voaid", Integer.parseInt(collect.getVoaid()));
//
//                bundle.putString("sound", collect.getSound());
//
//                bundle.putBoolean("iscollect",true);
//
//                Constant.title = collect.getTitle();
//
//                intent.putExtras(bundle);

                //context.startActivity(intent);
                Toast.makeText(context, "此处不可跳转", Toast.LENGTH_SHORT).show();


            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                //弹出对话框
                AlertDialog alertDialog2 = new AlertDialog.Builder(context)
                        .setTitle("确认删除收藏文章")
                        .setPositiveButton("删除", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                                del = true;
                                pageVoaid = Integer.parseInt(collect.getVoaid());

                                if (callBack != null) {
                                    callBack.clickDel();
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
                //长按删除事件

                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewz;

        TextView collectView, collectCreateTime, collectClass;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewz = itemView.findViewById(R.id.image_collect);

            collectView = itemView.findViewById(R.id.collect_view);

            collectClass = itemView.findViewById(R.id.collect_class);

            collectCreateTime = itemView.findViewById(R.id.collect_creat_time);
        }
    }

    public interface CallBack{
        void clickDel() ;
    }

}
