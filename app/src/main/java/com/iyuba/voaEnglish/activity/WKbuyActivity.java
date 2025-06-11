package com.iyuba.voaEnglish.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alipay.sdk.app.PayTask;

import com.iyuba.imooclib.IMooc;
import com.iyuba.module.user.IyuUserManager;
import com.iyuba.module.user.User;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.databinding.ActivityWkbuyBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.model.bean.VipBean;
import com.iyuba.voaEnglish.model.bean.VipParseBean;
import com.iyuba.voaEnglish.presenter.home.UidLoginPresenter;
import com.iyuba.voaEnglish.presenter.home.VipBuyPresenter;
import com.iyuba.voaEnglish.view.home.UidLoginContract;
import com.iyuba.voaEnglish.view.home.VipBuyContract;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Map;

public class WKbuyActivity extends AppCompatActivity  implements VipBuyContract.VipBuyView, UidLoginContract.UidLoginView {


    private ActivityWkbuyBinding binding;

    private UidLoginPresenter uidLoginpresenter;

    private VipBuyPresenter vipBuypresenter;

    private String code = null;

    private String WIDtatal_fee = null;

    private int amount = 0;

    private int product_id = -1;

    private String WIDbody = null;

    private String WIDsubject = null;
    private int appid = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityWkbuyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        vipBuypresenter = new VipBuyPresenter();
        vipBuypresenter.attchView(this);
        uidLoginpresenter=new UidLoginPresenter();
        uidLoginpresenter.attchView(this);

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            @Override
            public void run() {

              binding.buyWK.callOnClick();

            }
        }); //延迟时间


        binding.buyWK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                simpleDateFormat.applyPattern("yyyy-MM-dd");
                code = MD5.md5(Constant.useruid + "iyuba" + simpleDateFormat.format(System.currentTimeMillis()));
                WIDtatal_fee = Constant.wkPrice;
                amount = Constant.wkId;
                product_id = 200;
                WIDsubject = "微课";
                try {
                    WIDbody =  URLEncoder.encode(Constant.wkBody,  "utf-8");
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                vipBuypresenter.getVip(appid, Integer.parseInt(String.valueOf(Constant.useruid)), code, WIDtatal_fee, amount, product_id, WIDbody, WIDsubject);

            }
        });
    }

    @Override
    public void uidLogin(UidBean uidBean) {


        Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

        SharedPreferences vipStatusInfo = getSharedPreferences("vipStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = vipStatusInfo.edit();

        Constant.money = uidBean.getMoney();//钱包

        Constant.amount = uidBean.getAmount();//爱语币

        //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
        //要转成后的时间的格式
        android.icu.text.SimpleDateFormat simpleDateFormat = null;
        simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
        // 时间戳转换成时间
        String vipDate = null;
        vipDate = simpleDateFormat.format(new Date(timeStamp));

        Constant.vipDate = vipDate;//vip时间


        User user = new User();
        user.vipStatus = String.valueOf(Constant.vipStatus);
        if (Constant.vipStatus != 0) {
            user.vipExpireTime = uidBean.getExpireTime();
        }
        user.uid = Integer.parseInt(String.valueOf(Constant.useruid));
        user.credit = Integer.parseInt(uidBean.getCredits());
        user.name = Constant.username;
        user.imgUrl = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big";
        user.email = Constant.email;
        user.mobile = Constant.mobile;
        user.iyubiAmount = (int) Constant.amount;
        IyuUserManager.getInstance().setCurrentUser(user);


        onResume();
        finish();
    }

    @Override
    public void onResume() {

        super.onResume();




    }

    @Override
    public void getVip(VipParseBean vipParseBean) {



        if(vipParseBean.getResult().equals("200")){


//            Handler mHandler = new Handler();

            Runnable payRunnable = () -> {
                PayTask alipay = new PayTask(WKbuyActivity.this);
                Map<String,String> result = alipay.payV2(vipParseBean.getAlipayTradeStr(),true);
                //Log.d("chen",result.toString());
                if(result.get("resultStatus").equals("9000")){
                    vipBuypresenter.callbackVip(result.toString());
                }else{

                    toast(result.get("memo"));
                    hideLoading();
                    finish();
                }
              /*  Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);*/
            };
            // 必须异步调用
            Thread payThread = new Thread(payRunnable);
            payThread.start();

        }
    }

    @Override
    public void callbackVip(VipBean vipBean) {

        IMooc.notifyCoursePurchased();
        String sign = MD5.md5("20001" + Constant.useruid + "iyubaV2");
        uidLoginpresenter.uidLogin("android", "json",20001, Constant.useruid, Constant.useruid, 201, sign);
        finish();
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