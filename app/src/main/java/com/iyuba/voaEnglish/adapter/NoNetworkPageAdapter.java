package com.iyuba.voaEnglish.adapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iyuba.voaEnglish.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NoNetworkPageAdapter extends RecyclerView.Adapter<NoNetworkPageAdapter.CustomViewHolder> {

    //传递过来的参数
    private Context context;
    List<Map<String, String>> list = new ArrayList<>();

    public NoNetworkPageAdapter(Context context, List<Map<String, String>> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public NoNetworkPageAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_voa_page, parent, false);

        return new NoNetworkPageAdapter.CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoNetworkPageAdapter.CustomViewHolder holder, int position) {

        Map<String, String> data = list.get(position);

        holder.nameTxt.setText(data.get("pageEn"));
        holder.nameTxtTitle.setText(data.get("pageCn"));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView nameTxt;
        TextView nameTxtTitle;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTxt = itemView.findViewById(R.id.voa_title);
            nameTxtTitle = itemView.findViewById(R.id.voa_title_cn);

        }


    }
}
