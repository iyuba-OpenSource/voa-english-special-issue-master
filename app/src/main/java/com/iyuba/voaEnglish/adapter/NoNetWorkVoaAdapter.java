package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.activity.VoaDetailActivity;
import com.iyuba.voaEnglish.sqlitedb.OriginalTableDb;
import com.iyuba.voaEnglish.view.home.PageContract;

import java.util.List;
import java.util.Map;

public class NoNetWorkVoaAdapter  extends RecyclerView.Adapter<NoNetWorkVoaAdapter.CustomViewHolder>{

    private Context context;

    private List<Map<String, String>> list;

    private OriginalTableDb originalTableDb;

    private SQLiteDatabase sqLiteDatabase;



    public NoNetWorkVoaAdapter(Context context,List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public NoNetWorkVoaAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_adapter, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoNetWorkVoaAdapter.CustomViewHolder holder, int position) {

        Map<String,String> data = list.get(position);

        String s = data.get("voaid");
        holder.nameTxt.setText(data.get("title"));
        holder.nameTxtTitle.setText(data.get("titleCn"));
        holder.readCount.setText(data.get("readNum"));
        holder.creatTime.setText(data.get("createTime").substring(0, 10));

        originalTableDb = new OriginalTableDb(context, "originalTable.db", null, 1);


        sqLiteDatabase = originalTableDb.getReadableDatabase();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击跳入下一个界面，判断voaid在下一个数据中是否存在，若存在，则跳转到下一个界面，否则弹出请检查网络
                Cursor cursor = sqLiteDatabase.query("originalTable",null,"voaid="+s,null,null,null,null);
                //查询成功
                if(cursor.getCount()==0){
                    //弹出此偏文章未缓存
                    Toast.makeText(context, "此文章未加载", Toast.LENGTH_SHORT).show();
                }else{

                    //若存在，则执行跳转，进入后续界面进行输出
                    Intent intent = new Intent(context, VoaDetailActivity.class);

                    Bundle bundle = new Bundle();

                    bundle.putInt("voaid", Integer.parseInt(s));

                    Constant.voaid = Integer.parseInt(s);

                    Constant.audioSound = null;

                    bundle.putString("sound",null);

                    Constant.title = data.get("title");

                    intent.putExtras(bundle);

                    context.startActivity(intent);
                }

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

        TextView creatTime,readCount;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.item_view);
            nameTxtTitle = itemView.findViewById(R.id.item_view_title);
            creatTime = itemView.findViewById(R.id.item_creat_time);
            readCount = itemView.findViewById(R.id.item_read_count);
        }
    }

}
