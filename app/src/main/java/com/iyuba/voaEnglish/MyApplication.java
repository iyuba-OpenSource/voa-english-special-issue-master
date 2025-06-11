package com.iyuba.voaEnglish;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;

import com.iyuba.dlex.bizs.DLManager;
import com.iyuba.headlinelibrary.HeadlineType;
import com.iyuba.headlinelibrary.IHeadline;
import com.iyuba.headlinelibrary.data.local.db.HLDBManager;
import com.iyuba.imooclib.IMooc;
import com.iyuba.module.dl.BasicDLDBManager;
import com.iyuba.module.favor.BasicFavor;
import com.iyuba.module.favor.data.local.BasicFavorDBManager;
import com.iyuba.module.favor.data.local.BasicFavorInfoHelper;
import com.iyuba.module.favor.ui.BasicFavorActivity;
import com.iyuba.module.movies.data.local.db.DBManager;
import com.iyuba.module.privacy.PrivacyInfoHelper;
import com.iyuba.share.ShareExecutor;
import com.iyuba.share.mob.MobShareExecutor;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.util.MyOaidHelper;

import com.mob.MobSDK;
import com.tencent.vasdolly.helper.ChannelReaderUtil;
import com.umeng.commonsdk.UMConfigure;
import com.yd.saas.ydsdk.manager.YdConfig;
import com.youdao.sdk.common.OAIDHelper;
import com.youdao.sdk.common.YouDaoAd;
import com.youdao.sdk.common.YoudaoSDK;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MyApplication extends Application {



    @SuppressLint("StaticFieldLeak")
    private static Context context;


    private static String channel;

    public static String getChannel() {
        return channel;
    }

    public static void setChannel(String channel) {
        MyApplication.channel = channel;
    }
    @Override
    public void onCreate() {
        super.onCreate();

        context = this;



        channel =   ChannelReaderUtil.getChannel(getApplicationContext());

//        channel =   Constant.channel;


        UMConfigure.preInit(this, "646f415ea1a164591b238b4f", channel);
        UMConfigure.setLogEnabled(false);

        SharedPreferences sharedPreferences1 = getSharedPreferences("isInit", Context.MODE_PRIVATE);
        boolean isInit = sharedPreferences1.getBoolean("init", false);


        if (isInit) {

            Network.getInstance().init();
            Network.getInstance().initDev();

            System.loadLibrary("msaoaidsec");
            OAIDHelper.getInstance().init(this);

            //获取oaid
            MyOaidHelper myOaidHelper = new MyOaidHelper(new MyOaidHelper.AppIdsUpdater() {
                @Override
                public void onIdsValid(String ids) {

                    Constant.oaid = ids;



                }
            });
            myOaidHelper.getDeviceIds(this);



            //有道处理下载类广告问题    信息流
            YouDaoAd.getYouDaoOptions().setCanObtainAndroidId(false);
            YouDaoAd.getNativeDownloadOptions().setConfirmDialogEnabled(true);
            YouDaoAd.getYouDaoOptions().setAppListEnabled(false);
            YouDaoAd.getYouDaoOptions().setPositionEnabled(false);
            YouDaoAd.getYouDaoOptions().setSdkDownloadApkEnabled(true);
            YouDaoAd.getYouDaoOptions().setDeviceParamsEnabled(false);
            YouDaoAd.getYouDaoOptions().setWifiEnabled(false);
            //有道
            YoudaoSDK.init(this);
            YdConfig.getInstance().init(this, "201"); // 公司的所有广告


            MobSDK.submitPolicyGrantResult(true); //一键登录 验证码


            PrivacyInfoHelper.init(this);
            PrivacyInfoHelper.getInstance().putApproved(true);
            IHeadline.setDebug(false);
            IHeadline.init(getApplicationContext(), 201 + "", getString(R.string.app_name));
            IHeadline.setEnableShare(false);
            IHeadline.setEnableGoStore(false);

            IHeadline.setAdAppId("2011");
            //设置信息流广告   0224  0229 0236 0233
            IHeadline.setYdsdkTemplateKey("0241","0246","0253","0250", "");



            //BasicFavor.init(getApplicationContext(), getString(Integer.parseInt("201")));
            BasicDLDBManager.init(this);
            BasicFavorDBManager.init(this);
            DLManager.init(this, 5);
            DBManager.init(this);
            HLDBManager.init(this);
            BasicFavorInfoHelper.init(this);


            List<String> typeFilter = new ArrayList<>();
            typeFilter.add(HeadlineType.MEIYU);
            typeFilter.add(HeadlineType.TED);
            typeFilter.add(HeadlineType.TOPVIDEOS);
            typeFilter.add(HeadlineType.VOAVIDEO);
            typeFilter.add(HeadlineType.SMALLVIDEO);
            BasicFavor.setTypeFilter(typeFilter);

            //微课初始化
            IMooc.init(getApplicationContext(), String.valueOf(201), "voa");
            IMooc.setEnableShare(false);
            ShareExecutor.getInstance().setRealExecutor(new MobShareExecutor());

            IMooc.setYoudaoId("edbd2c39ce470cd72472c402cccfb586");
            IMooc.setAdAppId(String.valueOf(Constant.AdAppId));

            IMooc.setYdsdkTemplateKey("0241","0246","0253","0250","");





            SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            Constant.useruid = Integer.parseInt(preferences.getString("useruid", "0"));

            Constant.vipStatus = Integer.parseInt(preferences.getString("VipStatus", "0"));


            Log.d("fang002222", Constant.useruid+"onCreate: "+Constant.vipStatus);

        }
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        context = null;
    }
}
