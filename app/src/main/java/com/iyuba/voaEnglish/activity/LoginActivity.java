package com.iyuba.voaEnglish.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.iyuba.module.user.IyuUserManager;
import com.iyuba.module.user.User;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityLoginBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.model.bean.UserPwdLoginBean;
import com.iyuba.voaEnglish.presenter.home.UidLoginPresenter;
import com.iyuba.voaEnglish.presenter.home.UserPresenter;
import com.iyuba.voaEnglish.view.home.UidLoginContract;
import com.iyuba.voaEnglish.view.home.UserContract;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

public class LoginActivity extends AppCompatActivity implements UserContract.UserView, UidLoginContract.UidLoginView {


    private ActivityLoginBinding binding;

    private UserPresenter userPresenter;

    private UidLoginContract.UidLoginPresenter uidLoginPresenter;

    private boolean isClick = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //网络请求，单例模式

        Network.getInstance().init();
        userPresenter = new UserPresenter();
        userPresenter.attchView(this);


        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);


        SpannableString spannable = new SpannableString("我已阅读并同意隐私政策和使用条款");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(LoginActivity.this, UseActivity.class);
                startActivity(intent);
            }
        };
        spannable.setSpan(clickableSpan, 7, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannable.setSpan(clickableSpan1, 12, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //写入格式
        binding.privacyLoginUse.setText(spannable);
        binding.privacyLoginUse.setMovementMethod(LinkMovementMethod.getInstance());

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.privacyLoginUse.isChecked()){
                    String username = binding.username.getText().toString().trim();
                    String password = binding.password.getText().toString().trim();

                    String pwd = MD5.md5(password);
                    String sign = MD5.md5("11001" + username + pwd + "iyubaV2");

                    try {
                        username = URLEncoder.encode(username, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }


                    userPresenter.getUserLogin(username, pwd, "voa", "", "json", 201, "11001", sign);
                }else{
                    Toast.makeText(LoginActivity.this, "请先同意隐私政策", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //查看密码
        binding.clickPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isClick) {
                    binding.clickPas.setImageResource(R.drawable.clickpas1);
                    binding.password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isClick = false;
                } else {
                    binding.clickPas.setImageResource(R.drawable.clickpas);
                    binding.password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isClick = true;
                }

            }
        });

        //忘记密码
        binding.forgotPas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(intent);
            }
        });

        //注册界面跳转
        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
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
    public void getUserLogin(UserPwdLoginBean userPwdLoginBean) {

        if (userPwdLoginBean.getResult().equals("101")) {

            Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();


            Constant.useruid = userPwdLoginBean.getUid();

            Constant.username = userPwdLoginBean.getUsername();



            Constant.user_img = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big";

            String sign = MD5.md5("20001" + Constant.useruid + "iyubaV2");
            uidLoginPresenter.uidLogin("android", "json", 20001, Constant.useruid, Constant.useruid, 201, sign);
            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("name", userPwdLoginBean.getUsername()).putString("VipStatus",  userPwdLoginBean.getVipStatus()).putString("useruid", userPwdLoginBean.getUid() + "").putString("img", userPwdLoginBean.getImgSrc()).putString("amount", userPwdLoginBean.getAmount()).putString("money", userPwdLoginBean.getMoney()).commit();



        } else if (userPwdLoginBean.getResult().equals("102")) {
            Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
            // 清空用户名和密码文本框
            binding.password.setText("");
            //让密码文本框获取焦点
            binding.password.requestFocus();
        } else if (userPwdLoginBean.getResult().equals("103")) {
            Toast.makeText(LoginActivity.this, "密码不正确", Toast.LENGTH_SHORT).show();
            // 清空用户名和密码文本框
            binding.password.setText("");
            //让密码文本框获取焦点
            binding.password.requestFocus();
        } else if (userPwdLoginBean.getResult().equals("0")) {
            Toast.makeText(LoginActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
            // 清空用户名和密码文本框
            binding.password.setText("");
            //让密码文本框获取焦点
            binding.password.requestFocus();
        } else if (userPwdLoginBean.getResult().equals("00")) {
            Toast.makeText(LoginActivity.this, "内部处理错误", Toast.LENGTH_SHORT).show();
            // 清空用户名和密码文本框
            binding.password.setText("");
            //让密码文本框获取焦点
            binding.password.requestFocus();
        } else if (userPwdLoginBean.getResult().equals("000")) {
            Toast.makeText(LoginActivity.this, "用户名或密码未填写", Toast.LENGTH_SHORT).show();
            // 清空用户名和密码文本框
            binding.password.setText("");
            //让密码文本框获取焦点
            binding.password.requestFocus();
        } else {
            Toast.makeText(LoginActivity.this, "登录失败.", Toast.LENGTH_SHORT).show();
            // 清空用户名和密码文本框
            binding.password.setText("");
            //让密码文本框获取焦点
            binding.password.requestFocus();
        }
    }


    @Override
    public void uidLogin(UidBean uidBean) {

        Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

        Constant.money = uidBean.getMoney();//钱包

        Constant.amount = uidBean.getAmount();//爱语币

        //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
        //要转成后的时间的格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        // 时间戳转换成时间
        String vipDate = simpleDateFormat.format(new Date(timeStamp));

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
        user.imgUrl = "http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big";
        user.email = Constant.email;
        user.mobile = Constant.mobile;
        user.iyubiAmount = (int) Constant.amount;
        IyuUserManager.getInstance().setCurrentUser(user);

        finish();


    }
}