package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.Log;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityClockBinding;
import com.iyuba.voaEnglish.databinding.ActivityMyClockBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.ClockMoneyBean;
import com.iyuba.voaEnglish.presenter.home.ClockMoneyPresenter;
import com.iyuba.voaEnglish.view.home.ClockMoneyContract;
import com.iyuba.voaEnglish.work.EncodingUtils;
import com.iyuba.voaEnglish.wxapi.WXEntryActivity;
import com.othershe.calendarview.utils.CalendarUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class ClockActivity extends AppCompatActivity implements ClockMoneyContract.ClockMoneyView {

    ActivityClockBinding binding;


    private ClockMoneyPresenter clockMoneyPresenter;

    private int ranking, totalDays;

    private String share;


    private int[] cDate = CalendarUtil.getCurrentDate();

    private static final String APP_ID = "wxd9985efb50eca08c";

    private IWXAPI api;

    private Boolean isInit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClockBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Network.getInstance().init();

        clockMoneyPresenter = new ClockMoneyPresenter();
        clockMoneyPresenter.attchView(this);

        api = WXAPIFactory.createWXAPI(ClockActivity.this, APP_ID, true);
        api.registerApp(APP_ID);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = ClockActivity.this.getIntent().getExtras();

        ranking = bundle.getInt("ranking");


        totalDays = bundle.getInt("totalDays");


        share = bundle.getString("shareId");

        binding.totalDay.setText("学习天数:" + totalDays);

        binding.clockRanking.setText("用户排名:" + ranking);

        binding.clockName.setText(Constant.username);

        Glide.with(ClockActivity.this).load(Constant.user_img).into(binding.clockImg);

        String s = "http://app.iyuba.cn/share.jsp?uid=" + Constant.useruid + "&appId=201&shareId=" + share;

        Bitmap codeBitmap = EncodingUtils.createQRCode(s, 500, 500, null);
        binding.clockCode.setImageBitmap(codeBitmap);

        if (cDate[2] == 0) {
            binding.clockBack.setBackgroundResource(R.drawable.p0);
        } else if (cDate[2] == 1) {
            binding.clockBack.setBackgroundResource(R.drawable.p1);
        } else if (cDate[2] == 2) {
            binding.clockBack.setBackgroundResource(R.drawable.p2);
        } else if (cDate[2] == 3) {
            binding.clockBack.setBackgroundResource(R.drawable.p3);
        } else if (cDate[2] == 4) {
            binding.clockBack.setBackgroundResource(R.drawable.p4);
        } else if (cDate[2] == 5) {
            binding.clockBack.setBackgroundResource(R.drawable.p5);
        } else if (cDate[2] == 6) {
            binding.clockBack.setBackgroundResource(R.drawable.p6);
        } else if (cDate[2] == 7) {
            binding.clockBack.setBackgroundResource(R.drawable.p7);
        } else if (cDate[2] == 8) {
            binding.clockBack.setBackgroundResource(R.drawable.p8);
        } else if (cDate[2] == 9) {
            binding.clockBack.setBackgroundResource(R.drawable.p9);
        } else if (cDate[2] == 10) {
            binding.clockBack.setBackgroundResource(R.drawable.p10);
        } else if (cDate[2] == 11) {
            binding.clockBack.setBackgroundResource(R.drawable.p11);
        } else if (cDate[2] == 12) {
            binding.clockBack.setBackgroundResource(R.drawable.p12);
        } else if (cDate[2] == 13) {
            binding.clockBack.setBackgroundResource(R.drawable.p13);
        } else if (cDate[2] == 14) {
            binding.clockBack.setBackgroundResource(R.drawable.p14);
        } else if (cDate[2] == 15) {
            binding.clockBack.setBackgroundResource(R.drawable.p15);
        } else if (cDate[2] == 16) {
            binding.clockBack.setBackgroundResource(R.drawable.p16);
        } else if (cDate[2] == 17) {
            binding.clockBack.setBackgroundResource(R.drawable.p17);
        } else if (cDate[2] == 18) {
            binding.clockBack.setBackgroundResource(R.drawable.p18);
        } else if (cDate[2] == 19) {
            binding.clockBack.setBackgroundResource(R.drawable.p19);
        } else if (cDate[2] == 20) {
            binding.clockBack.setBackgroundResource(R.drawable.p20);
        } else if (cDate[2] == 21) {
            binding.clockBack.setBackgroundResource(R.drawable.p21);
        } else if (cDate[2] == 22) {
            binding.clockBack.setBackgroundResource(R.drawable.p22);
        } else if (cDate[2] == 23) {
            binding.clockBack.setBackgroundResource(R.drawable.p23);
        } else if (cDate[2] == 24) {
            binding.clockBack.setBackgroundResource(R.drawable.p24);
        } else if (cDate[2] == 25) {
            binding.clockBack.setBackgroundResource(R.drawable.p25);
        } else if (cDate[2] == 26) {
            binding.clockBack.setBackgroundResource(R.drawable.p26);
        } else if (cDate[2] == 27) {
            binding.clockBack.setBackgroundResource(R.drawable.p27);
        } else if (cDate[2] == 28) {
            binding.clockBack.setBackgroundResource(R.drawable.p28);
        } else if (cDate[2] == 29) {
            binding.clockBack.setBackgroundResource(R.drawable.p29);
        } else {
            binding.clockBack.setBackgroundResource(R.drawable.p30);
        }

        binding.clockCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                View dView = getWindow().getDecorView();//获取DecorView
                dView.setDrawingCacheEnabled(true);//生成View的副本，是一个Bitmap
                Bitmap bmp = dView.getDrawingCache();//获取View的副本，就是这个View上面所显示的任何内容

                if (!api.isWXAppInstalled()) {
                    Toast.makeText(ClockActivity.this, "您还没有安装微信", Toast.LENGTH_SHORT).show();
                } else {
                    //开始分享功能

                    if (isInit) {

                        WXImageObject imgObj = new WXImageObject(bmp);
                        WXMediaMessage msg = new WXMediaMessage();


                        msg.mediaObject = imgObj;


                        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 75, 122, true);
                        bmp.recycle();
                        msg.thumbData = bmpToByteArray(thumbBmp, true);

                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = String.valueOf(System.currentTimeMillis());  //transaction字段用与唯一标示一个请求
                        req.message = msg;
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;

                        api.sendReq(req);
                        isInit = false;
                    }


                    //调用api接口，发送数据到微信


                }

                return false;
            }
        });

        onResume();

        if (Constant.WX_SHARE) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            Date date = new Date(System.currentTimeMillis());
            String fl = formatter.format(date);
            String flg = Base64.encodeToString(fl.getBytes(), Base64.DEFAULT);
            clockMoneyPresenter.clockAddMoney(81, 1, flg, Constant.useruid, 201);
            Constant.WX_SHARE = false;
        }


    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
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
    public void clockAddMoney(ClockMoneyBean clockMoneyBean) {
        if (clockMoneyBean.getResult().equals("200")){
            Toast.makeText(this, "打卡成功，钱包增加了"+clockMoneyBean.getMoney()+"分,积分增加了"+clockMoneyBean.getAddcredit()+"点。", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "今日以打卡", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    public void onResume() {

        super.onResume();
        if (Constant.WX_SHARE) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT+08"));
            Date date = new Date(System.currentTimeMillis());
            String fl = formatter.format(date);
            String flg = Base64.encodeToString(fl.getBytes(), Base64.DEFAULT);
            clockMoneyPresenter.clockAddMoney(81, 1, flg, Constant.useruid, 201);
            Constant.WX_SHARE = false;
        }
    }


}