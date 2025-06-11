package com.iyuba.voaEnglish.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.adapter.ReadReporterAdpter;
import com.iyuba.voaEnglish.databinding.ActivityReadReporterDetailBinding;
import com.iyuba.voaEnglish.model.bean.HearingDetailBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterDetailBean;
import com.iyuba.voaEnglish.model.bean.SpokenDetailBean;
import com.iyuba.voaEnglish.presenter.home.LearningReportDetailPresenter;
import com.iyuba.voaEnglish.view.home.LearningReportDetailContract;

import java.util.List;

public class ReadReporterDetailActivity extends AppCompatActivity implements LearningReportDetailContract.LearningReportDetailView {

    private ActivityReadReporterDetailBinding binding;

    private LearningReportDetailPresenter learningReportDetailPresenter;


    private ReadReporterAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadReporterDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        learningReportDetailPresenter = new LearningReportDetailPresenter();
        learningReportDetailPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.hearingdetailRv.setLayoutManager(linearLayoutManager);


        learningReportDetailPresenter.getReadReporterDetail(String.valueOf(Constant.useruid),"json",20,0);

        binding.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }



    @Override
    public void getHearingDetail(HearingDetailBean hearingDetailBean) {

    }

    @Override
    public void getSpokenDetail(SpokenDetailBean spokenDetailBean) {

    }

    @Override
    public void getReadReporterDetail(ReadReporterDetailBean readReporterDetailBean) {
        List<ReadReporterDetailBean.DataDTO> list = readReporterDetailBean.getData();
        //控制定位光标  2行代码
        adpter = new ReadReporterAdpter( this,list);
        binding.hearingdetailRv.setAdapter(adpter);

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