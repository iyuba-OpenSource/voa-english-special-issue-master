package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.SearchAdapter;
import com.iyuba.voaEnglish.adapter.VoaAdapter;
import com.iyuba.voaEnglish.adapter.VoaPageAdapter;
import com.iyuba.voaEnglish.adapter.WordSearchAdapter;
import com.iyuba.voaEnglish.databinding.ActivitySearchBinding;
import com.iyuba.voaEnglish.databinding.ActivitySearchResultBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.HotWordBean;
import com.iyuba.voaEnglish.model.bean.HotWordSearchBean;
import com.iyuba.voaEnglish.model.bean.WordSearchBean;
import com.iyuba.voaEnglish.presenter.home.SearchPresenter;
import com.iyuba.voaEnglish.view.home.SearchContract;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity implements SearchContract.SearchView {

    private ActivitySearchResultBinding binding;

    private SearchPresenter searchPresenter;

    private String keyword = null;

    private int num = 0;

    private MediaPlayer mediaPlayer;

    private LinearLayoutManager linearLayoutManager;

    private SearchAdapter searchAdapter;

    private WordSearchAdapter wordSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        searchPresenter = new SearchPresenter();
        searchPresenter.attchView(this);

        linearLayoutManager = new LinearLayoutManager(this);
        binding.searchHomeRv.setLayoutManager(linearLayoutManager);

        //获取传递过来的数据
        Bundle bundle = SearchResultActivity.this.getIntent().getExtras();
        if (bundle != null) {
            keyword = bundle.getString("keyword", null);
            num = bundle.getInt("ketNum", 0);
        }

        //判断是什么搜索
        if (keyword != null) {
            if (num == 0) {
                //输入单词搜索
                searchPresenter.getWordSearch("json",keyword,1,1000,0,"voa",0,Constant.useruid,201);
            } else {
                //热词搜索,隐藏掉音标栏
                binding.wordLan.setVisibility(View.GONE);
                try {
                    searchPresenter.getHotSearch("voa", URLEncoder.encode(keyword, "UTF-8"), Constant.useruid,201);
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
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
    public void getHotWord(HotWordBean hotWordBean) {

    }

    @Override
    public void getHotSearch(HotWordSearchBean hotWordSearchBean) {
        //设置adapter
        List<HotWordSearchBean.DataDTO> list = hotWordSearchBean.getData();

        searchAdapter = new SearchAdapter(this, list);

        binding.searchHomeRv.setAdapter(searchAdapter);

        searchAdapter.notifyDataSetChanged();
    }

    @Override
    public void getWordSearch(WordSearchBean wordSearchBean) throws UnsupportedEncodingException {
        //设置单词栏
        if(wordSearchBean.getTextToal()!=0||wordSearchBean.getTitleToal()!=0){
            binding.wordBofang.setVisibility(View.VISIBLE);
            binding.searchWord.setText(wordSearchBean.getWord());
            binding.wordYinbiao.setText(URLDecoder.decode(wordSearchBean.getPhAm(), "UTF-8"));
            binding.wordMean.setText(wordSearchBean.getDef());
            binding.wordBofang.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //设置音频地址，播放
                    mediaPlayer = MediaPlayer.create(SearchResultActivity.this, Uri.parse(wordSearchBean.getPhAmMp3()));
                    mediaPlayer.start();
                }
            });

            //设置adapter
            List<WordSearchBean.TitleDataDTO> list = wordSearchBean.getTitleData();

            wordSearchAdapter = new WordSearchAdapter(this, list);

            binding.searchHomeRv.setAdapter(wordSearchAdapter);

            searchAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(SearchResultActivity.this, "关键词错误", Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}