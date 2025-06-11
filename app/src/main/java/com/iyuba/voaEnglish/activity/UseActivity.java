package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityLoginBinding;
import com.iyuba.voaEnglish.databinding.ActivityUseBinding;

public class UseActivity extends AppCompatActivity {

    private ActivityUseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUseBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.web.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        String urlString = "https://ai.iyuba.cn/api/protocoluse.jsp?apptype=VOA英语特刊&company=1";
        binding.web.loadUrl(urlString);
    }
}