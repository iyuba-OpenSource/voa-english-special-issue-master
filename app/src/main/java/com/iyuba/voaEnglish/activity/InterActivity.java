package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityInterBinding;
import com.iyuba.voaEnglish.databinding.ActivityStartBinding;
import com.iyuba.voaEnglish.model.MD5;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class InterActivity extends AppCompatActivity {

    private WebView webView;
    private ActivityInterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        //EventBus.getDefault().register(this);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        WebSettings webSettings = binding.web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        String sign = MD5.md5("iyuba" + Constant.useruid + "camstory");



        binding.web.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });

        binding.web.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int progress) {
                InterActivity.this.setProgress(progress * 100);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {


                return false;
            }

        });
        String user ;

        try {
            user = URLEncoder.encode(Constant.username, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        String urlString = "http://m.iyuba.cn/mall/index.jsp?uid=" + Constant.useruid + "&username=" + user + "&appid=201&sign=" + sign;

        binding.web.loadUrl(urlString);
    }
}