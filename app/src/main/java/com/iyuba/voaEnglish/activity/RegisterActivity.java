package com.iyuba.voaEnglish.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.iyuba.voaEnglish.databinding.ActivityRegisterBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.IsRegisterBean;
import com.iyuba.voaEnglish.model.bean.RegisterBean;
import com.iyuba.voaEnglish.presenter.home.IsRegisterPresenter;
import com.iyuba.voaEnglish.presenter.home.RegisterPresenter;
import com.iyuba.voaEnglish.view.home.IsRegisterContact;
import com.iyuba.voaEnglish.view.home.RegisterContact;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.wrapper.TokenVerifyResult;

public class RegisterActivity extends AppCompatActivity implements IsRegisterContact.IsRegisterView, RegisterContact.RegisterView {

    private ActivityRegisterBinding binding;

    private IsRegisterPresenter isRegisterPresenter;

    private RegisterPresenter registerPresenter;

    private String phone, username, pas, pasOk, code;

    private int codeTime = 60;

    private boolean isTime = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Network.getInstance().init();
        isRegisterPresenter = new IsRegisterPresenter();
        isRegisterPresenter.attchView(this);

        registerPresenter = new RegisterPresenter();
        registerPresenter.attchView(this);

        //隐私政策
        SpannableString spannable = new SpannableString("我已阅读并同意隐私政策和使用条款");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(RegisterActivity.this, PrivacyActivity.class);
                startActivity(intent);
            }
        };
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent = new Intent(RegisterActivity.this, UseActivity.class);
                startActivity(intent);
            }
        };
        spannable.setSpan(clickableSpan, 7, 11, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannable.setSpan(clickableSpan1, 12, 16, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //写入格式
        binding.privacyAndUse.setText(spannable);
        binding.privacyAndUse.setMovementMethod(LinkMovementMethod.getInstance());


        SMSSDK.registerEventHandler(eh);

        binding.getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = binding.userPhone.getText().toString().trim();
                if(phone!=null){
                    codeTime = 60;
                    phone = binding.userPhone.getText().toString().trim();
                    isRegisterPresenter.isRegister("json", "android", 10009, phone);
                }


            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.privacyAndUse.isChecked()) {
                    pas = binding.userPas.getText().toString().trim();
                    pasOk = binding.userPasOk.getText().toString().trim();
                    code = binding.userCode.getText().toString().trim();
                    phone = binding.userPhone.getText().toString().trim();
                    username = binding.username.getText().toString().trim();

                    if (pas.equals(pasOk)) {
                        //执行判断是否已经注册
                        username = binding.username.getText().toString().trim();


                        if (username.length() > 15 || username.length() < 3) {
                            Toast.makeText(RegisterActivity.this, "用户名需在3-15字符", Toast.LENGTH_SHORT).show();
                        } else if (pas.length() > 15 || pas.length() < 6) {
                            Toast.makeText(RegisterActivity.this, "密码需在6-15字符", Toast.LENGTH_SHORT).show();
                        } else {
                            //输入验证码后
                            submitVerificationCode("86", phone, code);


                        }


                    } else {
                        binding.userPasOk.setText("");
                        Toast.makeText(RegisterActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "请先同意隐私政策", Toast.LENGTH_SHORT).show();
                }

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
    public void isRegister(IsRegisterBean isRegisterBean) {

        if (isRegisterBean.getResult().equals("101")) {
            Toast.makeText(RegisterActivity.this, "手机号已注册", Toast.LENGTH_SHORT).show();
        } else {
            //获取验证码

            if (isTime) {
                getVerificationCode("507161", "86", phone);
                isTime = false;
            }


            Handler handler = new Handler();

            Timer timer = new Timer();

            TimerTask timerTask1 = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.getCode.setText(codeTime + "s后重新获取");
                            codeTime = codeTime - 1;
                            if (codeTime <= 0) {
                                binding.getCode.setText("重新获取");
                                isTime = true;
                                handler.removeCallbacksAndMessages(null);
                                timer.cancel();
                            }
                        }
                    });
                    //延时执行内容


                }
            };
            timer.schedule(timerTask1, 0, 1000);
        }

    }

    @Override
    public void userRegister(RegisterBean registerBean) {

        if (registerBean.getResult().equals("111")) {
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        } else if (registerBean.getResult().equals("0")) {
            Toast.makeText(RegisterActivity.this, "服务器异常", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("00")) {
            Toast.makeText(RegisterActivity.this, "内部处理错误", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("000")) {
            Toast.makeText(RegisterActivity.this, "用户名或密码未填写", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("104")) {
            Toast.makeText(RegisterActivity.this, "邮箱非法", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("110")) {
            Toast.makeText(RegisterActivity.this, "服务器错误", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("112")) {
            Toast.makeText(RegisterActivity.this, "用户名已注册", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("113")) {
            Toast.makeText(RegisterActivity.this, "邮箱已注册", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("114")) {
            Toast.makeText(RegisterActivity.this, "用户名需在3-15字符", Toast.LENGTH_SHORT).show();
        } else if (registerBean.getResult().equals("115")) {
            Toast.makeText(RegisterActivity.this, "手机号已注册", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
        }


    }

    public static void getVerificationCode(String plat, String country, String phone) {
        SMSSDK.getVerificationCode(plat, country, phone);
    }


    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            // TODO 此处为子线程！不可直接处理UI线程！处理后续操作需传到主线程中操作！
            if (result == SMSSDK.RESULT_COMPLETE) {
                //成功回调
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    String sign = MD5.md5("11002" + username + MD5.md5(pas) + "iyubaV2");
                    try {
                        username = URLEncoder.encode(username, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    }
                    registerPresenter.userRegister(11002, username, MD5.md5(pas), phone, "voa", "android", "json", 201, sign);
                    //提交短信、语音验证码成功
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                    //获取短信验证码成功
                } else if (event == SMSSDK.EVENT_GET_VERIFY_TOKEN_CODE) {
                    //本机验证获取token成功
                    TokenVerifyResult tokenVerifyResult = (TokenVerifyResult) data;


                    //SMSSDK.login(phoneNum,tokenVerifyResult);
                } else if (event == SMSSDK.EVENT_VERIFY_LOGIN) {

                    //本机验证登陆成功
                }
            } else if (result == SMSSDK.RESULT_ERROR) {
                //失败回调
            } else {
                //其他失败回调
                ((Throwable) data).printStackTrace();
            }
        }
    };

    public static void submitVerificationCode(String country, String phone, String code) {
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(eh);
        super.onDestroy();

    }
}