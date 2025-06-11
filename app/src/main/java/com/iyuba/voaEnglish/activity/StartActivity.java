package com.iyuba.voaEnglish.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.common.base.VerifyException;
import com.iyuba.dlex.bizs.DLManager;
import com.iyuba.headlinelibrary.HeadlineType;
import com.iyuba.headlinelibrary.IHeadline;
import com.iyuba.headlinelibrary.data.local.db.HLDBManager;
import com.iyuba.imooclib.IMooc;
import com.iyuba.imooclib.data.local.IMoocDBManager;
import com.iyuba.imooclib.ui.web.Web;
import com.iyuba.module.dl.BasicDLDBManager;
import com.iyuba.module.favor.BasicFavor;
import com.iyuba.module.favor.data.local.BasicFavorDBManager;
import com.iyuba.module.favor.data.local.BasicFavorInfoHelper;
import com.iyuba.module.movies.data.local.db.DBManager;
import com.iyuba.module.privacy.PrivacyInfoHelper;
import com.iyuba.share.ShareExecutor;
import com.iyuba.share.mob.MobShareExecutor;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.MainActivity;
import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityStartBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.AdEntryBean;
import com.iyuba.voaEnglish.presenter.home.AdEntryPresenter;
import com.iyuba.voaEnglish.util.MyOaidHelper;
import com.iyuba.voaEnglish.view.home.AdEntryContract;

import com.mob.MobSDK;
import com.sdiyuba.rewardgold.model.NetWorkManager;
import com.umeng.commonsdk.UMConfigure;
import com.yd.saas.base.interfaces.AdViewSpreadListener;
import com.yd.saas.config.exception.YdError;
import com.yd.saas.ydsdk.YdSpread;
import com.yd.saas.ydsdk.manager.YdConfig;
import com.youdao.sdk.common.OAIDHelper;
import com.youdao.sdk.common.YouDaoAd;
import com.youdao.sdk.common.YoudaoSDK;
import com.youdao.sdk.nativeads.NativeErrorCode;
import com.youdao.sdk.nativeads.NativeResponse;
import com.youdao.sdk.nativeads.YouDaoNative;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class StartActivity extends AppCompatActivity implements AdEntryContract.AdEntryView, AdViewSpreadListener, YouDaoNative.YouDaoNativeNetworkListener {


    private ActivityStartBinding binding;

    private boolean isclick = false;

    private AdEntryPresenter adEntryPresenter;

    private int timeJump = 2;

    private YdSpread mSplashAd;

    private SharedPreferences.Editor appEditor;


    LinearLayout ad_view;

    private String adkey = "0135";
    private AdEntryBean.DataDTO dataDTO;

    private String ad_title = "ads4";

    private boolean isAdFirst = true;

    private boolean isAdCLick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //广告
        ad_view = binding.initLl.findViewById(R.id.csj_initad);
//        cover_line = binding.initLl.findViewById(R.id.init_cover);



        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);

        if (preferences.getString("name", "defaultname") != null) {

            String name = preferences.getString("name", "未登录");

            String useruid = preferences.getString("useruid", "0");

            String img = preferences.getString("img", Constant.user_img);

            String amount = preferences.getString("amount", "0");

            String money = preferences.getString("money", "0");


            Constant.useruid = Integer.parseInt(useruid);

            Constant.username = name;

            Constant.user_img = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big";

            Constant.amount = Double.parseDouble(amount);

            Constant.money = Double.parseDouble(money);


        }



        SharedPreferences firstpreferences = getSharedPreferences("isFirst", Context.MODE_PRIVATE);

        appEditor = firstpreferences.edit();

        Boolean isFirstIn = firstpreferences.getBoolean("first", true);


        //判断是否为第一次启动
        if (isFirstIn) {


            startDialog();
        } else {
            //不是第一次启动

            //友盟
            UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, MyApplication.getChannel());


            adEntryPresenter = new AdEntryPresenter();
            adEntryPresenter.attchView(this);

            if (Constant.useruid!=0){

                adEntryPresenter.getAdEntryAll(Constant.AdAppId,1,Constant.useruid);
            }else {

                adEntryPresenter.getAdEntryAll(Constant.AdAppId,1,0);
            }

//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent intent = new Intent(StartActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
//            },3000);


//            binding.jump.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(StartActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                    isclick = true;
//
//                }
//            });
//            //if(!isclick){
//            Timer mTimer = new Timer();
//
//            TimerTask timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    //延时执行内容
//
//                    if (!isclick) {
//                        Intent intent = new Intent(StartActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
//                        startActivity(intent);
//
//                    }
//
//                }
//            };
//            mTimer.schedule(timerTask, 3000);
//
//            Handler handler = new Handler();
//
//            Timer timer = new Timer();
//
//            TimerTask timerTask1 = new TimerTask() {
//                @Override
//                public void run() {
//                    handler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (!isclick) {
//                                binding.jump.setText(timeJump + "s 跳过");
//                                timeJump--;
//                            }
//                        }
//                    });
//                    //延时执行内容
//
//
//                }
//            };
//            timer.schedule(timerTask1, 1000, 1000);
//

        }


    }

    public static void submitPolicyGrantResult(boolean isGranted) {
        MobSDK.submitPolicyGrantResult(isGranted);
    }

    private void startDialog() {
        final android.app.AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_initmate);
            window.setGravity(Gravity.CENTER);

            TextView tvContent = window.findViewById(R.id.tv_content);
            TextView tvCancel = window.findViewById(R.id.tv_cancel);
            TextView tvAgree = window.findViewById(R.id.tv_agree);
            String str = "    感谢您对本公司的支持!本公司非常重视您的个人信息和隐私保护。" +
                    "为了更好地保障您的个人权益，在您使用我们的产品前，" +
                    "请务必审慎阅读《隐私政策》和《用户协议》内的所有条款，" +
                    "尤其是:\n" +
                    " 1.我们对您的个人信息的收集/保存/使用/对外提供/保护等规则条款，以及您的用户权利等条款;\n" +
                    " 2.约定我们的限制责任、免责条款;\n" +
                    " 3.其他以颜色或加粗进行标识的重要条款。\n" +
                    "您点击“同意并继续”的行为即表示您已阅读完毕并同意以上协议的全部内容。" +
                    "如您同意以上协议内容，请点击“同意”，开始使用我们的产品和服务!";

            SpannableStringBuilder ssb = new SpannableStringBuilder();
            ssb.append(str);
            final int start = str.indexOf("《");//第一个出现的位置
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    Intent intent = new Intent(StartActivity.this, PrivacyActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.main));
                    ds.setUnderlineText(false);
                }
            }, start, start + 6, 0);

            int end = str.lastIndexOf("《");
            ssb.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
//
                    Intent intent = new Intent(StartActivity.this, UseActivity.class);
                    startActivity(intent);
                }

                @Override
                public void updateDrawState(@NonNull TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(getResources().getColor(R.color.main));
                    ds.setUnderlineText(false);
                }
            }, end, end + 6, 0);

            tvContent.setMovementMethod(LinkMovementMethod.getInstance());
            tvContent.setText(ssb, TextView.BufferType.SPANNABLE);

            // 不同意用户协议和隐私政策
            tvCancel.setOnClickListener(v -> {

                submitPolicyGrantResult(false);
                finish();
            });

            // 同意用户协议和隐私政策
            tvAgree.setOnClickListener(v -> {


                initplay();



            });
        }
    }
    private void initplay() {
        //初始化集成

        Network.getInstance().init();
        Network.getInstance().initDev();

        SharedPreferences sharedPreferences = getSharedPreferences("isFirst", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("first", false).commit();

        SharedPreferences sharedPreferences1 = getSharedPreferences("isInit", Context.MODE_PRIVATE);
        sharedPreferences1.edit().putBoolean("init", true).commit();

        //获取oaid 的初始化    防止某些手机使用oaid会崩溃
        System.loadLibrary("msaoaidsec");
        OAIDHelper.getInstance().init(this);


        MobSDK.submitPolicyGrantResult(true); //验证码
        //获取oaid
        MyOaidHelper myOaidHelper = new MyOaidHelper(new MyOaidHelper.AppIdsUpdater() {
            @Override
            public void onIdsValid(String ids) {

                Constant.oaid = ids;
            }
        });
        myOaidHelper.getDeviceIds(this);

        YouDaoAd.getYouDaoOptions().setCanObtainAndroidId(false);

        YouDaoAd.getNativeDownloadOptions().setConfirmDialogEnabled(true);
        YouDaoAd.getYouDaoOptions().setAppListEnabled(false);
        YouDaoAd.getYouDaoOptions().setPositionEnabled(false);
        YouDaoAd.getYouDaoOptions().setSdkDownloadApkEnabled(true);
        YouDaoAd.getYouDaoOptions().setDeviceParamsEnabled(false);
        YouDaoAd.getYouDaoOptions().setWifiEnabled(false);

        YdConfig.getInstance().init(this, "201"); // 公司的所有广告
        //有道
        YoudaoSDK.init(this);

//        //瑞狮
//        VlionSdkConfig config = new VlionSdkConfig
//                .Builder()
//                .setAppId("A0082")//媒体在平台申请的 APP_ID
//                .setAppKey("e198402d8f1d63c27c1c17f949f9f190")//媒体在平台申请的 APP_KEY
//                .setEnableLog(false)//测试阶段打开，可以通过日志排查问题，上线时去除该调用
//                .setPrivateController(createPrivateController)//隐私信息控制设置，此项必须设置！！
//                .build();
//        //个性化推荐广告
//        VlionSDk.setPersonalizedAdState(true);
//        //初始化广告SDK
//        VlionSDk.init(getApplication(), config);

        ShareExecutor.getInstance().setRealExecutor(new MobShareExecutor());

//        //集成的广告流
        PrivacyInfoHelper.init(this);
        PrivacyInfoHelper.getInstance().putApproved(true);

//        IHeadline.setYoudaoStreamId(""); //隐藏集成的信息流
//        IMooc.setYoudaoId("229");
//        EventBus.getDefault().register(this);
//        //有道广告
//        //有道处理下载类广告问题    信息流




        IMoocDBManager.init(this);

        IHeadline.setDebug(false);
        IHeadline.init(getApplicationContext(), 201 + "", getString(R.string.app_name));
        IHeadline.setEnableShare(true);
        IHeadline.setEnableGoStore(false);
        IHeadline.setEnableSmallVideoTalk(true);//小视频配音
        IHeadline.setEnableIyuCircle(false);//禁用爱与圈

        IHeadline.setAdAppId("2011");
        //设置信息流广告   0224  0229 0236 0233
        IHeadline.setYdsdkTemplateKey("0241","0246","0253","0250", "");

        //db
        BasicDLDBManager.init(this);
        BasicFavor.init(getApplicationContext(), 201 + "");
        BasicFavorDBManager.init(this);
        DLManager.init(this, 5);
        DBManager.init(this);
        HLDBManager.init(this);

        //微课初始化
        IMooc.init(getApplicationContext(), String.valueOf(201), "voa");
        IMooc.setYoudaoId("edbd2c39ce470cd72472c402cccfb586");
        IMooc.setAdAppId(String.valueOf(Constant.AdAppId));

        IMooc.setYdsdkTemplateKey("0241","0246","0253","0250","");


        Intent intent = new Intent(StartActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);

        startActivity(intent);
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

    @Override
    public void getAdEntryAll(List<AdEntryBean> adEntryBean) {


        dataDTO = adEntryBean.get(0).getData();
        String type = dataDTO.getType();

        if (!dataDTO.getTitle().equals("")){

            ad_title = dataDTO.getTitle();
        }

        Log.d("fang888", "getAdEntryAllComplete: " + type);
        if (type.equals("web")) {


//            Glide.with(StartActivity.this).load("http://static3.iyuba.cn/dev" + dataDTO.getStartuppic()).into(binding.initView);
        } else if (type.equals(Constant.AD_ADS1) || type.equals(Constant.AD_ADS2) || type.equals(Constant.AD_ADS3)
                || type.equals(Constant.AD_ADS4) || type.equals(Constant.AD_ADS5) || type.equals(Constant.AD_ADS6)) {

            if (type.equals(Constant.AD_ADS1)) {
                adkey = "0255";
            } else if (type.equals(Constant.AD_ADS2)) {
                adkey = "0135";
            } else if (type.equals(Constant.AD_ADS3)) {
                adkey = "0248";
            } else if (type.equals(Constant.AD_ADS4)) {
                adkey = "0243";
            } else if (type.equals(Constant.AD_ADS5)) {
                adkey = "0251";
            } else if (type.equals(Constant.AD_ADS6)) {
                adkey = "0135";
            }


            YdSpread mSplashAd = new YdSpread.Builder(StartActivity.this)
                    .setKey(adkey)
                    .setContainer(ad_view)
                    .setSpreadListener(this)
                    .setCountdownSeconds(4)
                    .setSkipViewVisibility(true)
                    .build();
            mSplashAd.requestSpread();
        } else {

            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }

    }

    @Override
    public void onAdDisplay() {

        //曝光的回调  成功展示

    }

    @Override
    public void onAdClose() {
        if (!isAdCLick) {

            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
            isAdCLick = true;
        }

    }

    @Override
    public void onAdClick(String s) {

        isAdCLick = true;
        com.google.android.exoplayer2.util.Log.d("adadad", "onAdClick");
    }

    @Override
    public void onAdFailed(YdError ydError) {

        Log.d("fang888",ad_title+"getAdEntryAllComplete: " + ydError.getMsg());

        if (isAdFirst){

            isAdFirst = false;


            if (ad_title.equals(Constant.AD_ADS1)) {
                adkey = "0255";
            } else if (ad_title.equals(Constant.AD_ADS2)) {
                adkey = "0135";
            } else if (ad_title.equals(Constant.AD_ADS3)) {
                adkey = "0248";
            } else if (ad_title.equals(Constant.AD_ADS4)) {
                adkey = "0243";
            } else if (ad_title.equals(Constant.AD_ADS5)) {
                adkey = "0251";
            } else if (ad_title.equals(Constant.AD_ADS6)) {
                adkey = "0135";
            }

            YdSpread mSplashAd = new YdSpread.Builder(StartActivity.this)
                    .setKey(adkey)
                    .setContainer(ad_view)
                    .setSpreadListener(this)
                    .setCountdownSeconds(4)
                    .setSkipViewVisibility(true)
                    .build();
            mSplashAd.requestSpread();


        }else {
            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onNativeLoad(NativeResponse nativeResponse) {

    }

    @Override
    public void onNativeFail(NativeErrorCode nativeErrorCode) {

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isAdCLick) {//点击了就直接跳转mainactivity

            startActivity(new Intent(StartActivity.this, MainActivity.class));
            finish();
        }


    }
}