package com.iyuba.voaEnglish.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.iyuba.voaEnglish.BuildConfig;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.databinding.ActivityInfoBinding;
import com.iyuba.voaEnglish.util.APKVersionCodeUtils;


public class InfoActivity extends AppCompatActivity {

    private ActivityInfoBinding binding;

    public static final String VERSION_CODE = APKVersionCodeUtils.getVerName(MyApplication.getContext())+ Constant.AdDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //activity中获取页面
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        binding.tb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.version.setText("version : " + VERSION_CODE);

        binding.beian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实现跳转网页的主要代码
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("https://beian.miit.gov.cn/#/Integrated/index");
                intent.setData(content_url);
                startActivity(intent);
            }
        });


    }
}