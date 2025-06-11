package com.iyuba.voaEnglish.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.adapter.HearingDetailAdpter;
import com.iyuba.voaEnglish.databinding.ActivityHearingDetailBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.bean.HearingDetailBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterDetailBean;
import com.iyuba.voaEnglish.model.bean.SpokenDetailBean;
import com.iyuba.voaEnglish.presenter.home.LearningReportDetailPresenter;
import com.iyuba.voaEnglish.view.home.LearningReportDetailContract;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class HearingDetailActivity extends AppCompatActivity implements LearningReportDetailContract.LearningReportDetailView {

    private ActivityHearingDetailBinding binding;

    private LearningReportDetailPresenter learningReportDetailPresenter;

    private HearingDetailAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHearingDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        learningReportDetailPresenter = new LearningReportDetailPresenter();
        learningReportDetailPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.hearingdetailRv.setLayoutManager(linearLayoutManager);

        //获取当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String sign= MD5.md5(Constant.useruid+simpleDateFormat.format(date));
        learningReportDetailPresenter.getHearingDetail(50,1,1, String.valueOf(Constant.useruid),sign);


        binding.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getHearingDetail(HearingDetailBean hearingDetailBean) {


        List<HearingDetailBean.DataDTO> list = hearingDetailBean.getData();
        //控制定位光标  2行代码
            adpter = new HearingDetailAdpter(list, this);
            binding.hearingdetailRv.setAdapter(adpter);
    }

    @Override
    public void getSpokenDetail(SpokenDetailBean spokenDetailBean) {

    }

    @Override
    public void getReadReporterDetail(ReadReporterDetailBean readReporterDetailBean) {

    }

    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void toast(String msg) {

    }
}