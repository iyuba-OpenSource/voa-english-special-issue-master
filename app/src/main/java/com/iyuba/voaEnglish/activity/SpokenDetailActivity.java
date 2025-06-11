package com.iyuba.voaEnglish.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.adapter.SpokenDetailAdpter;
import com.iyuba.voaEnglish.databinding.ActivitySpokenDetailBinding;
import com.iyuba.voaEnglish.model.bean.HearingDetailBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterDetailBean;
import com.iyuba.voaEnglish.model.bean.SpokenDetailBean;
import com.iyuba.voaEnglish.presenter.home.LearningReportDetailPresenter;
import com.iyuba.voaEnglish.view.home.LearningReportDetailContract;

import java.util.List;

public class SpokenDetailActivity extends AppCompatActivity implements LearningReportDetailContract.LearningReportDetailView {

    private ActivitySpokenDetailBinding binding;

    private LearningReportDetailPresenter learningReportDetailPresenter;

    private SpokenDetailAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySpokenDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        learningReportDetailPresenter = new LearningReportDetailPresenter();
        learningReportDetailPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.spokendetailRv.setLayoutManager(linearLayoutManager);

        learningReportDetailPresenter.getSpokenDetail(String.valueOf(Constant.useruid),"voa","English",0,50);

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

        List<SpokenDetailBean.DataDTO> list = spokenDetailBean.getData();
        //控制定位光标  2行代码
        adpter = new SpokenDetailAdpter(list, this);
        binding.spokendetailRv.setAdapter(adpter);
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