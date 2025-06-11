package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.MyApplication;
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
import com.alipay.sdk.app.PayTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class VIPActivity extends AppCompatActivity implements VipBuyContract.VipBuyView, UidLoginContract.UidLoginView {

    private ActivityVipactivityBinding binding;

    private int gColor = Color.parseColor("#1296db");

    private int backColor = Color.parseColor("#C7EBFF");

    private int whiteColor = Color.parseColor("#ffffff");

    private int blackColor = Color.BLACK;

    private int isClick = 0;

    private VipBuyPresenter vipBuyPresenter;


    private UidLoginContract.UidLoginPresenter uidLoginPresenter;
    private int appid = 201;

    private String code = null;

    private String WIDtatal_fee = null;

    private int amount = 0;

    private int product_id = -1;

    private String WIDbody = null;

    private String WIDsubject = null;

    private int vipType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVipactivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Network.getInstance().init();
        vipBuyPresenter = new VipBuyPresenter();
        vipBuyPresenter.attchView(this);

        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);

        Glide.with(this).load(Constant.user_img).into(binding.userImg);

        binding.userNameMine.setText(Constant.username);

        if(Constant.vipStatus==0){
            binding.userVip.setText("普通用户");
        }else{
            binding.userVip.setText("会员有效期:"+Constant.vipDate);
        }





        binding.buyIyubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转到购买iyubi界面
                Intent intent = new Intent(VIPActivity.this, IyubiActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = VIPActivity.this.getIntent().getExtras();

        if(bundle!=null){
            vipType = bundle.getInt("vipType");
        }


        //会员协议
        binding.vipRuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://iuserspeech.iyuba.cn:9001/api/vipServiceProtocol.jsp?company=1&type=app");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
            }
        });

        binding.appVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick = 0;
                binding.appVip.setBackgroundColor(backColor);
                binding.allVip.setBackgroundColor(whiteColor);
                binding.goldVip.setBackgroundColor(whiteColor);
                binding.appVipLin.setVisibility(View.VISIBLE);
                binding.allVipLin.setVisibility(View.GONE);
                binding.goldVipLin.setVisibility(View.GONE);


            }
        });
        binding.allVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick = 1;
                binding.appVip.setBackgroundColor(whiteColor);
                binding.allVip.setBackgroundColor(backColor);
                binding.goldVip.setBackgroundColor(whiteColor);
                binding.appVipLin.setVisibility(View.GONE);
                binding.allVipLin.setVisibility(View.VISIBLE);
                binding.goldVipLin.setVisibility(View.GONE);

                binding.allApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < binding.allVipSelect.getChildCount(); i++) {
                            RadioButton radioButton = (RadioButton) binding.allVipSelect.getChildAt(i);
                            if (radioButton.isChecked()) {
                                if (radioButton.getText().equals("全站会员1个月                                  ￥50")) {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 50+"" ;
                                    amount = 1;
                                    product_id = 0;
                                    WIDsubject = "全站会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                } else if (radioButton.getText().equals("全站会员6个月                                  ￥198")) {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 198+"" ;
                                    amount = 6;
                                    product_id = 0;
                                    WIDsubject = "全站会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                } else if (radioButton.getText().equals("全站会员12个月                                ￥298")) {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 298+"" ;
                                    amount = 12;
                                    product_id = 0;
                                    WIDsubject = "全站会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                } else {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 588+"" ;
                                    amount = 36;
                                    product_id = 0;
                                    WIDsubject = "全站会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                }
                            }
                        }
                    }
                });

            }
        });


        //判断vipType
        if(vipType == 2){
            isClick = 2;
            binding.appVip.setBackgroundColor(whiteColor);
            binding.allVip.setBackgroundColor(whiteColor);
            binding.goldVip.setBackgroundColor(backColor);
            binding.appVipLin.setVisibility(View.GONE);
            binding.allVipLin.setVisibility(View.GONE);
            binding.goldVipLin.setVisibility(View.VISIBLE);
            binding.goldApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < binding.goldVipSelect.getChildCount(); i++) {
                        RadioButton radioButton = (RadioButton) binding.goldVipSelect.getChildAt(i);
                        if (radioButton.isChecked()) {
                            //Log.d("chen", radioButton.getText() + " " + isClick + "");
                            if (radioButton.getText().equals("黄金会员1个月                                  ￥98")) {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 98+"" ;
                                amount = 1;
                                product_id = 2;
                                WIDsubject = "黄金会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            } else if (radioButton.getText().equals("黄金会员3个月                                  ￥288")) {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 288+"" ;
                                amount = 3;
                                product_id = 2;
                                WIDsubject = "黄金会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            } else if (radioButton.getText().equals("黄金会员6个月                                  ￥518")) {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 518+"" ;
                                amount = 6;
                                product_id = 2;
                                WIDsubject = "黄金会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            } else {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 998+"" ;
                                amount = 12;
                                product_id = 2;
                                WIDsubject = "黄金会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            }
                        }
                    }
                }
            });
        }

        binding.goldVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isClick = 2;
                binding.appVip.setBackgroundColor(whiteColor);
                binding.allVip.setBackgroundColor(whiteColor);
                binding.goldVip.setBackgroundColor(backColor);
                binding.appVipLin.setVisibility(View.GONE);
                binding.allVipLin.setVisibility(View.GONE);
                binding.goldVipLin.setVisibility(View.VISIBLE);
                binding.goldApply.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i < binding.goldVipSelect.getChildCount(); i++) {
                            RadioButton radioButton = (RadioButton) binding.goldVipSelect.getChildAt(i);
                            if (radioButton.isChecked()) {
                                //Log.d("chen", radioButton.getText() + " " + isClick + "");
                                if (radioButton.getText().equals("黄金会员1个月                                  ￥98")) {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 98+"" ;
                                    amount = 1;
                                    product_id = 2;
                                    WIDsubject = "黄金会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                } else if (radioButton.getText().equals("黄金会员3个月                                  ￥288")) {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 288+"" ;
                                    amount = 3;
                                    product_id = 2;
                                    WIDsubject = "黄金会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                } else if (radioButton.getText().equals("黄金会员6个月                                  ￥518")) {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 518+"" ;
                                    amount = 6;
                                    product_id = 2;
                                    WIDsubject = "黄金会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                } else {
                                    SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                    simpleDateFormat.applyPattern("yyyy-MM-dd");
                                    code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                    WIDtatal_fee = 998+"" ;
                                    amount = 12;
                                    product_id = 2;
                                    WIDsubject = "黄金会员";
                                    WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                    vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                                }
                            }
                        }
                    }
                });
            }
        });


        if (isClick == 0) {

            binding.appApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < binding.appVipSelect.getChildCount(); i++) {
                        RadioButton radioButton = (RadioButton) binding.appVipSelect.getChildAt(i);
                        if (radioButton.isChecked()) {
                            if (radioButton.getText().equals("本应用一月会员                                  ￥25")) {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 25+"" ;
                                amount = 1;
                                product_id = 10;
                                WIDsubject = "本应用会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            } else if (radioButton.getText().equals("本应用半年会员                                  ￥69")) {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 69+"" ;
                                amount = 6;
                                product_id = 10;
                                WIDsubject = "本应用会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            } else if (radioButton.getText().equals("本应用一年会员                                  ￥99")) {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 99+"" ;
                                amount = 12;
                                product_id = 10;
                                WIDsubject = "本应用会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            } else {
                                SimpleDateFormat simpleDateFormat = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
                                simpleDateFormat.applyPattern("yyyy-MM-dd");
                                code = MD5.md5(Constant.useruid+"iyuba"+simpleDateFormat.format(System.currentTimeMillis()));
                                WIDtatal_fee = 199+"" ;
                                amount = 36;
                                product_id = 10;
                                WIDsubject = "本应用会员";
                                WIDbody = "花费"+WIDtatal_fee+"元购买"+WIDsubject;
                                vipBuyPresenter.getVip(appid,Constant.useruid,code,WIDtatal_fee,amount,product_id,WIDbody,WIDsubject);
                            }
                        }
                    }
                }
            });


        }


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
    public void getVip(VipParseBean vipParseBean) {

        if (binding.vipCbObey.isChecked()) {
            if(vipParseBean.getResult().equals("200")){



                Handler mHandler = new Handler();

                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(VIPActivity.this);
                        Map <String,String> result = alipay.payV2(vipParseBean.getAlipayTradeStr(),true);
                        if(result.get("resultStatus").equals("9000")){
                            vipBuyPresenter.callbackVip(result.toString());
                        }else{
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

        }else {

            Toast.makeText(MyApplication.getContext(), "请先阅读并同意会员服务协议", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void callbackVip(VipBean vipBean) {

        //实现用户uid登录


            String sign = MD5.md5("20001"+Constant.useruid+"iyubaV2");
            uidLoginPresenter.uidLogin("android","json",20001,Constant.useruid,Constant.useruid,201,sign);




    }

    @Override
    public void uidLogin(UidBean uidBean) {

        Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

        Constant.money = uidBean.getMoney();//钱包

        Constant.amount = uidBean.getAmount();//爱语币

        //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long timeStamp = uidBean.getExpireTime()*1000L;//转化成长整型
        //要转成后的时间的格式
        android.icu.text.SimpleDateFormat simpleDateFormat= null;
        simpleDateFormat = new android.icu.text.SimpleDateFormat("yyyy-MM-dd");
        // 时间戳转换成时间
        String vipDate = null;
        vipDate = simpleDateFormat.format(new Date(timeStamp));

        Constant.vipDate = vipDate;//vip时间

        Resfresh();


    }
    public void Resfresh(){
        if(Constant.vipStatus==0){
            binding.userVip.setText("普通用户");
        }else{
            binding.userVip.setText(Constant.vipDate);
        }
    }
}