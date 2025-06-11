package com.iyuba.voaEnglish;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.iyuba.headlinelibrary.HeadlineType;
import com.iyuba.headlinelibrary.ui.title.DropdownTitleFragmentNew;
import com.iyuba.imooclib.event.ImoocBuyVIPEvent;
import com.iyuba.imooclib.event.ImoocPayCourseEvent;
import com.iyuba.imooclib.ui.mobclass.MobClassFragment;
import com.iyuba.module.user.IyuUserManager;
import com.iyuba.module.user.User;
import com.iyuba.voaEnglish.activity.LoginActivity;
import com.iyuba.voaEnglish.activity.MineFragment;
import com.iyuba.voaEnglish.activity.PageFragment;
import com.iyuba.voaEnglish.activity.SearchActivity;
import com.iyuba.voaEnglish.activity.VIPActivity;
import com.iyuba.voaEnglish.activity.WKbuyActivity;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.presenter.home.UidLoginPresenter;
import com.iyuba.voaEnglish.view.home.UidLoginContract;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UidLoginContract.UidLoginView {

    private LinearLayout linearLayout_page, linearLayout_mine, linearLayout_video, linearLayout_course;


    private ImageView image_page, image_mine, tooba_back, image_video, toobar_search, image_course;

    private TextView text_page, text_mine, text_video, text_course;


    private PageFragment pageFragment;

    private MineFragment mineFragment;

    private DropdownTitleFragmentNew dropdownTitleFragmentNew;

    private MobClassFragment mobClassFragment;


    private int changeColor = Color.parseColor("#1296DB");

    View toobar;

    TextView titleMid;

    UidLoginContract.UidLoginPresenter uidLoginPresenter;

    //1709187100

    int GoalTime = 1709188841;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Constant.Eva_Sum = 0;



        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);

        EventBus.getDefault().register(this);

        onResume();


        setContentView(R.layout.activity_main);
        tooba_back = findViewById(R.id.toolbar_iv_back);
        toobar_search = findViewById(R.id.toolbar_iv_search);
        titleMid = findViewById(R.id.toolbar_iv_title);
        toobar = findViewById(R.id.toolbar);
        tooba_back.setVisibility(View.INVISIBLE);
        toobar_search.setVisibility(View.VISIBLE);





//        Handler mHandlerss = new Handler();
//        mHandlerss.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
////                String packageName = "com.sdiyuba.tedenglish"; // 目标应用程序的包名
////                Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
////                if (intent != null) {
////                    startActivity(intent);
////                }
//                Intent intent =new Intent(MainActivity.this, AdDetailActivity.class);
//                startActivity(intent);
//            }
//        },15000); //触发进入软件默认进入
//


        Log.d("fang789654", "onCreate: "+Constant.useruid);
        if (Constant.useruid!=0){
            String sign = MD5.md5("20001" + Constant.useruid + "iyubaV2");
            uidLoginPresenter.uidLogin("android", "json", 20001, Constant.useruid, Constant.useruid, 201, sign);

        }



        //广告
        titleMid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                uidCheck();

            }
        });

        toobar_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击跳转到搜索页面
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);

            }
        });




        linearLayout_page = findViewById(R.id.id_tab_page);

        linearLayout_page.setOnClickListener(this);

        linearLayout_mine = findViewById(R.id.id_tab_mine);

        linearLayout_mine.setOnClickListener(this);

        linearLayout_video = findViewById(R.id.id_tab_video);

        linearLayout_video.setOnClickListener(this);

        linearLayout_course = findViewById(R.id.id_tab_course);

        linearLayout_course.setOnClickListener(this);

        image_page = findViewById(R.id.page);

        image_mine = findViewById(R.id.mine);

        image_video = findViewById(R.id.video);

        image_course = findViewById(R.id.course);

        text_page = findViewById(R.id.text_page);

        text_mine = findViewById(R.id.text_mine);

        text_video = findViewById(R.id.text_video);

        text_course = findViewById(R.id.text_course);

        image_page.setImageResource(R.drawable.tj1);

        text_page.setTextColor(changeColor);

//        image_course.setImageResource(R.drawable.course1);
//        text_course.setTextColor(changeColor);
//
//        toobar.setVisibility(View.GONE);
//        replaceFragment("CE");

//        image_video.setImageResource(R.drawable.video1);
//        text_video.setTextColor(changeColor);


        long currentTimeMillis = System.currentTimeMillis()/1000;

        if (currentTimeMillis>=GoalTime){

            linearLayout_course.setVisibility(View.VISIBLE);
        }
        replaceFragment("HOME");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void fromMoocBuyGoldenVip(ImoocBuyVIPEvent event) {
        if(Constant.useruid!=0){
            Intent intent = new Intent(this, VIPActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("vipType",2);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "您还未登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.id_tab_page:
                image_page.setImageResource(R.drawable.tj1);
                text_page.setTextColor(changeColor);
                image_mine.setImageResource(R.drawable.wd);
                text_mine.setTextColor(Color.BLACK);
                image_video.setImageResource(R.drawable.video);
                text_video.setTextColor(Color.BLACK);
                text_course.setTextColor(Color.BLACK);
                image_course.setImageResource(R.drawable.course);
                toobar.setVisibility(View.VISIBLE);
                toobar_search.setVisibility(View.VISIBLE);
                replaceFragment("HOME");

                pageFragment.checkAdShow();
                break;
            case R.id.id_tab_mine:
                replaceFragment("ME");
                image_page.setImageResource(R.drawable.tj);
                text_page.setTextColor(Color.BLACK);

                image_mine.setImageResource(R.drawable.wd1);
                text_mine.setTextColor(changeColor);

                image_video.setImageResource(R.drawable.video);
                text_video.setTextColor(Color.BLACK);

                text_course.setTextColor(Color.BLACK);
                image_course.setImageResource(R.drawable.course);

                toobar.setVisibility(View.VISIBLE);
                toobar_search.setVisibility(View.GONE);
                break;
            case R.id.id_tab_video:
                replaceFragment("VE");
                image_page.setImageResource(R.drawable.tj);
                text_page.setTextColor(Color.BLACK);
                image_mine.setImageResource(R.drawable.wd);
                text_mine.setTextColor(Color.BLACK);
                image_video.setImageResource(R.drawable.video1);
                text_video.setTextColor(changeColor);
                text_course.setTextColor(Color.BLACK);
                image_course.setImageResource(R.drawable.course);
                toobar.setVisibility(View.GONE);

                break;
            case R.id.id_tab_course:
                replaceFragment("CE");

                image_page.setImageResource(R.drawable.tj);
                text_page.setTextColor(Color.BLACK);

                image_mine.setImageResource(R.drawable.wd);
                text_mine.setTextColor(Color.BLACK);

                image_video.setImageResource(R.drawable.video);
                text_video.setTextColor(Color.BLACK);

                text_course.setTextColor(changeColor);
                image_course.setImageResource(R.drawable.course1);

                toobar.setVisibility(View.GONE);
                break;
        }


    }

    //完成动态切换代码
    private void replaceFragment(String name) {

        FragmentManager fragmentManager = getSupportFragmentManager();




        pageFragment = (PageFragment) fragmentManager.findFragmentByTag("HOME");
        if (pageFragment == null) {

            pageFragment = PageFragment.newInstance(null, null);
        }
        mineFragment = (MineFragment) fragmentManager.findFragmentByTag("ME");
        if (mineFragment == null) {

            mineFragment = MineFragment.newInstance(Constant.username, null);
        }

        dropdownTitleFragmentNew = (DropdownTitleFragmentNew) fragmentManager.findFragmentByTag("VE");
        if (dropdownTitleFragmentNew == null) {

            String[] types = {HeadlineType.SMALLVIDEO,HeadlineType.MEIYU, HeadlineType.VOAVIDEO, HeadlineType.TOPVIDEOS,  HeadlineType.TED};

            Bundle bundle = DropdownTitleFragmentNew.buildArguments(10, types, false);
            dropdownTitleFragmentNew = DropdownTitleFragmentNew.newInstance(bundle);

        }

        mobClassFragment = (MobClassFragment) fragmentManager.findFragmentByTag("CE");
        if (mobClassFragment == null) {
            ArrayList<Integer> tempList = new ArrayList<>();

            tempList.add(-2);
//            tempList.add(-1);
            tempList.add(2);
            tempList.add(3);
            tempList.add(4);
            tempList.add(7);
            tempList.add(8);
            tempList.add(9);
            tempList.add(21);
            tempList.add(22);
//            tempList.add(28);
//            tempList.add(52);
            tempList.add(91);

            Bundle bundle = MobClassFragment.buildArguments(3, false, tempList);
            mobClassFragment = MobClassFragment.newInstance(bundle);
        }


        FragmentTransaction transaction = fragmentManager.beginTransaction();




        if (!pageFragment.isAdded()) {

            transaction.add(R.id.frameLayout_page, pageFragment, "HOME");
        }
        if (!mineFragment.isAdded()) {

            transaction.add(R.id.frameLayout_page, mineFragment, "ME");
        }

        if (!dropdownTitleFragmentNew.isAdded()) {

            transaction.add(R.id.frameLayout_page, dropdownTitleFragmentNew, "VE");

        }

        if (!mobClassFragment.isAdded()) {
            transaction.add(R.id.frameLayout_page, mobClassFragment, "CE");
        }


        if (name.equals("homead")) {

            transaction.show(pageFragment);
            transaction.hide(mineFragment);
            transaction.hide(dropdownTitleFragmentNew);
            transaction.hide(mobClassFragment);

        }else if (name.equals("HOME")) {

            transaction.show(pageFragment);
            transaction.hide(mineFragment);
            transaction.hide(dropdownTitleFragmentNew);
            transaction.hide(mobClassFragment);

        } else if (name.equals("ME")) {

            transaction.hide(pageFragment);
            transaction.show(mineFragment);
            transaction.hide(dropdownTitleFragmentNew);
            transaction.hide(mobClassFragment);

        } else if (name.equals("VE")) {

            transaction.hide(pageFragment);
            transaction.hide(mineFragment);
            transaction.show(dropdownTitleFragmentNew);
            transaction.hide(mobClassFragment);

        } else {
            transaction.hide(pageFragment);
            transaction.hide(mineFragment);
            transaction.hide(dropdownTitleFragmentNew);
            transaction.show(mobClassFragment);

        }
        transaction.commit();
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


        Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

        Constant.money = uidBean.getMoney();//钱包

        Constant.amount = uidBean.getAmount();//爱语币

        //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
        //要转成后的时间的格式
        SimpleDateFormat simpleDateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        // 时间戳转换成时间
        String vipDate = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vipDate = simpleDateFormat.format(new Date(timeStamp));
        }

        Constant.vipDate = vipDate;//vip时间

        Constant.email = uidBean.getEmail();

        Constant.mobile = uidBean.getMobile();


        User user = new User();
        user.vipStatus = String.valueOf(Constant.vipStatus);
        if (Constant.vipStatus != 0) {
            user.vipExpireTime = uidBean.getExpireTime();
        }
        user.uid = Constant.useruid;
        user.credit = Integer.parseInt(uidBean.getCredits());
        user.name = Constant.username;
        user.imgUrl = Constant.user_img;
        user.email = Constant.email;
        user.mobile = Constant.mobile;
        user.iyubiAmount = (int) Constant.amount;
        IyuUserManager.getInstance().setCurrentUser(user);

    }


    public void onResume() {

        super.onResume();


    }

    //微课直购
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ImoocPayCourseEvent event) {
        // TODO Go Pay Order to buy course
        Constant.wkId = event.courseId;
        Constant.wkPrice = event.price;
        Constant.wkBody = event.body;
        Intent intent = new Intent(this, WKbuyActivity.class);
        startActivity(intent);

    }



}