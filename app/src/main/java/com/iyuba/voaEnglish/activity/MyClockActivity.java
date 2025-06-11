package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.databinding.ActivityMyClockBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.AppClockBean;
import com.iyuba.voaEnglish.model.bean.ClockRecordBean;
import com.iyuba.voaEnglish.presenter.home.AppClockPresenter;
import com.iyuba.voaEnglish.presenter.home.ClockRecordPresenter;
import com.iyuba.voaEnglish.presenter.home.UserPresenter;
import com.iyuba.voaEnglish.view.home.AppClockContract;
import com.iyuba.voaEnglish.view.home.ClockRecordContract;
import com.othershe.calendarview.bean.DateBean;
import com.othershe.calendarview.listener.OnPagerChangeListener;
import com.othershe.calendarview.listener.OnSingleChooseListener;
import com.othershe.calendarview.utils.CalendarUtil;
import com.othershe.calendarview.weiget.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MyClockActivity extends AppCompatActivity implements  AppClockContract.AppClockView {

    private ActivityMyClockBinding binding;

   // private ClockRecordPresenter clockRecordPresenter;

    private AppClockPresenter appClockPresenter;

    CalendarView calendarView;


    private int[] cDate = CalendarUtil.getCurrentDate();

    private String curTime = "";


    private List<String> list = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyClockBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Network.getInstance().init();

        appClockPresenter = new AppClockPresenter();
        appClockPresenter.attchView(this);


        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        calendarView = binding.calendar;
        calendarView
                .setStartEndDate("2016.1", "2028.12")
                .setDisableStartEndDate("2016.10.10", "2028.10.10")
                .setInitDate(cDate[0] + "." + cDate[1])
                .init();
        binding.timeTitle.setText(cDate[0] + "年" + cDate[1] + "月" );


        getCalendar(cDate);

        binding.lastMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cDate[1] > 0) {
                    cDate[1]--;
                } else {
                    cDate[0]--;
                    cDate[1] = 12;
                }
                getCalendar(cDate);
            }
        });
        binding.nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cDate[1] < 12) {
                    cDate[1]++;
                } else {
                    cDate[0]++;
                    cDate[1] = 1;
                }
                getCalendar(cDate);
            }
        });

        //执行网络请求

    }

    private void getCalendar(int[] curDate) {

        if (curDate[1] < 10) {
            curTime = curDate[0] + "0" + curDate[1];
        } else {
            curTime = curDate[0] + "" + curDate[1];
        }
        appClockPresenter.getAppClock(Constant.useruid,201,curTime);

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



    @Override
    public void getAppClock(AppClockBean appClockBean) {

        List<AppClockBean.RecordDTO> list1 = appClockBean.getRecord();



        for(int i = 0 ;i<list1.size();i++){
            String s;
            s=list1.get(i).getCreatetime().substring(0,4)+"."+list1.get(i).getCreatetime().substring(5,7)+"."+list1.get(i).getCreatetime().substring(8,10);
            list.add(s);
        }

        calendarView
                .setStartEndDate("2016.1", "2028.12")
                .setDisableStartEndDate("2016.10.10", "2028.10.10")
                .setInitDate(cDate[0] + "." + cDate[1])
                .init();
        binding.timeTitle.setText(cDate[0] + "年" + cDate[1] + "月" );

        //calendarView.

        calendarView.setMultiDate(list);
    }
}