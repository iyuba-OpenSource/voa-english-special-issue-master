package com.iyuba.voaEnglish;

import android.net.wifi.aware.PublishConfig;
import android.text.SpannableString;

import com.iyuba.voaEnglish.model.bean.VoaDetailBean;

import java.util.List;

public class Constant {

    public static int useruid = 0;

    public static int AdAppId = 2011;

    public static String username = "未登录";

    public static String user_img = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&size=big&uid=13682864";

    public static String user_img_copy = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&size=big&uid=13682864";

    public static String email = null;

    public static String mobile = null;

    public static int scores = 0;

    //文章voaid
    public static int voaid = 0;

    public static String audioSound = null;

    //文章内容
    public static List<VoaDetailBean.VoatextDTO> voatextDTOList = null;

    public static boolean isEvaData = false;

    //动态创建,保存评测后返回内容
    public static SpannableString[] spannableString = new SpannableString[100];

    public static int[]  evaScore = new int[100];

    public static String[] orlVoaid = new String[10];

    public static String[] orlSound = new String[10];

    public static String[] audioURL = new String[100];

    public static String title = "VOA慢速英语";

    public static int BIAO_ZHI = 0;

    //用户vip，money

    public static int vipStatus = 0;

    public static double money = 0;

    public static double amount =0; //爱语币

    public static String vipDate = null;


    //public static int vipStatus = 0;

    public static int Eva_Sum = 0;

    public static boolean isInit =false;

   // public static  int text = 15;

    public static String pdf;

    public static  int AUDIO_MODEL = 0;

    //听力进度
    public static int SEN_NUM = 0;

    public static int WORD_NUM = 0;

    public static boolean WX_SHARE = false;

    public static int wkId;
    public static String wkPrice;

    public static String wkBody;


    public static final String AD_ADS1 = "ads1";//倍孜
    public static final String AD_ADS2 = "ads2";//穿山甲
    public static final String AD_ADS3 = "ads3";//百度
    public static final String AD_ADS4 = "ads4";//广点通优量汇
    public static final String AD_ADS5 = "ads5";//快手
    public static final String AD_ADS6 = "ads6";//瑞狮

    public static final String AD_Youdao = "youdao";


    public static int back_y = 0;
    public static int back_x = 0;
    public static boolean isAdShow = true;
    public static String oaid = "0";

    public static String AdDate ="" ;
    public static String channel = "other";
}
