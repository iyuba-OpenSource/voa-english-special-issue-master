package com.iyuba.voaEnglish.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.adapter.MyMoneyAdpter;
import com.iyuba.voaEnglish.databinding.ActivityMyMoneyDetailBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.bean.MyMoneyBean;
import com.iyuba.voaEnglish.presenter.home.MyMoneyPresenter;
import com.iyuba.voaEnglish.view.home.MyMoneyContract;

import java.text.SimpleDateFormat;
import java.util.List;

public class MyMoneyDetailActivity extends AppCompatActivity implements MyMoneyContract.MyMoneyView {

    private ActivityMyMoneyDetailBinding binding;
    private MyMoneyPresenter myMoneyPresenter;

    private MyMoneyAdpter adpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyMoneyDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        myMoneyPresenter = new MyMoneyPresenter();
        myMoneyPresenter.attchView(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //recyclerView.setLayoutManager(linearLayoutManager);
        binding.moneyRv.setLayoutManager(linearLayoutManager);

        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String sign = MD5.md5(Constant.useruid+"iyuba"+df.format(System.currentTimeMillis()));
        myMoneyPresenter.getMyMoney(String.valueOf(Constant.useruid),1,40,sign);

        binding.tb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void getMyMoney(MyMoneyBean myMoneyBean) {

        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");
        binding.money.setText( Constant.money/100+"元");  //格式化显示
        List<MyMoneyBean.DataDTO> list = myMoneyBean.getData();
        //控制定位光标  2行代码
        adpter = new MyMoneyAdpter(this, list);
        binding.moneyRv.setAdapter(adpter);
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