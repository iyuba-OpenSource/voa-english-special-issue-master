package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityIyubiBinding;
import com.iyuba.voaEnglish.databinding.ActivityVipactivityBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.model.bean.VipBean;
import com.iyuba.voaEnglish.model.bean.VipParseBean;
import com.iyuba.voaEnglish.presenter.home.UidLoginPresenter;
import com.iyuba.voaEnglish.presenter.home.VipBuyPresenter;
import com.iyuba.voaEnglish.view.home.UidLoginContract;
import com.iyuba.voaEnglish.view.home.VipBuyContract;

import java.text.SimpleDateFormat;
import java.util.Map;

public class IyubiActivity extends AppCompatActivity implements VipBuyContract.VipBuyView, UidLoginContract.UidLoginView {

    private ActivityIyubiBinding binding;

    private VipBuyPresenter vipBuyPresenter;

    private UidLoginContract.UidLoginPresenter uidLoginPresenter;

    private int appid = 201;

    private String code = null;

    private String WIDtatal_fee = null;

    private int amount = 0;

    private int product_id = -1;

    private String WIDbody = null;

    private String WIDsubject = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIyubiBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Network.getInstance().init();
        vipBuyPresenter = new VipBuyPresenter();
        vipBuyPresenter.attchView(this);

        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.buySmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //购买19.9元210爱语币
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.useruid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 19.9 + "";
                amount = 210;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                vipBuyPresenter.getVip(appid, Constant.useruid, code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject);
            }
        });
        binding.buyMoreSmall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.useruid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 59.9 + "";
                amount = 650;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                vipBuyPresenter.getVip(appid, Constant.useruid, code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject);
            }
        });
        binding.buyNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.useruid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 99.9 + "";
                amount = 1100;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                vipBuyPresenter.getVip(appid, Constant.useruid, code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject);
            }
        });
        binding.buyMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.useruid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 599 + "";
                amount = 6600;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                vipBuyPresenter.getVip(appid, Constant.useruid, code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject);
            }
        });
        binding.buyMost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.useruid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = 999 + "";
                amount = 12000;
                product_id = 1;
                WIDsubject = "爱语币";
                WIDbody = "花费" + WIDtatal_fee + "元购买" + WIDsubject;
                vipBuyPresenter.getVip(appid, Constant.useruid, code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject);
            }
        });


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
    public void uidLogin(UidBean uidBean) {
        Constant.amount = uidBean.getAmount();//爱语币
    }

    @Override
    public void getVip(VipParseBean vipParseBean) {
        if (vipParseBean.getResult().equals("200")) {


            Handler mHandler = new Handler();

            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    PayTask alipay = new PayTask(IyubiActivity.this);
                    Map<String, String> result = alipay.payV2(vipParseBean.getAlipayTradeStr(), true);
                    if (result.get("resultStatus").equals("9000")) {
                        vipBuyPresenter.callbackVip(result.toString());
                    } else {
                        toast(result.get("memo"));
                        hideLoading();
                    }
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        }
    }

    @Override
    public void callbackVip(VipBean vipBean) {
        //实现用户uid登录
        if (vipBean.getMsg().equals("Success")) {

            String sign = MD5.md5("20001" + Constant.useruid + "iyubaV2");
            uidLoginPresenter.uidLogin("android", "json", 20001, Constant.useruid, Constant.useruid, 201, sign);


        }
    }
}