package com.iyuba.voaEnglish.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityClockBinding;
import com.iyuba.voaEnglish.databinding.ActivitySearchBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.HotWordBean;
import com.iyuba.voaEnglish.model.bean.HotWordSearchBean;
import com.iyuba.voaEnglish.model.bean.WordSearchBean;
import com.iyuba.voaEnglish.presenter.home.SearchPresenter;
import com.iyuba.voaEnglish.presenter.home.UidLoginPresenter;
import com.iyuba.voaEnglish.view.home.SearchContract;

public class SearchActivity extends AppCompatActivity implements SearchContract.SearchView {

    private ActivitySearchBinding binding;

    private SearchPresenter searchPresenter;

    private String word;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Network.getInstance().init();
        searchPresenter = new SearchPresenter();
        searchPresenter.attchView(this);

        //获取热词
        searchPresenter.getHotWord("voa");


        binding.search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取输入框的值
                word = binding.upSearch.getText().toString().trim();

                if ("".equals(word)){
                    Toast.makeText(SearchActivity.this, "请输入搜索关键词", Toast.LENGTH_SHORT).show();
                }else{
                    searchPresenter.getWordSearch("json",word,1,1000,0,"voa",0, Constant.useruid,201);

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
    public void getHotWord(HotWordBean hotWordBean) {
        //获取到，设置
        binding.firstWord.setText(hotWordBean.getData().get(0));
        binding.twoWord.setText(hotWordBean.getData().get(1));
        binding.threeWord.setText(hotWordBean.getData().get(2));
        binding.fourWord.setText(hotWordBean.getData().get(3));
        binding.fiveWord.setText(hotWordBean.getData().get(4));
        binding.sixWord.setText(hotWordBean.getData().get(5));
        binding.senveWord.setText(hotWordBean.getData().get(6));
        binding.eightWord.setText(hotWordBean.getData().get(7));
        binding.nineWord.setText(hotWordBean.getData().get(8));
        binding.shiWord.setText(hotWordBean.getData().get(9));

        //给每一个设置点击搜索事件
        binding.firstWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //携带参数跳转到界面
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(0));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        binding.twoWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(1));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.threeWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(2));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.fourWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(3));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.fiveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(4));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.sixWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(5));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.senveWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(6));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.eightWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(7));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.nineWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(8));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
        binding.shiWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("keyword", hotWordBean.getData().get(9));
                bundle.putInt("ketNum", 10);

                intent.putExtras(bundle);

                startActivity(intent);

            }
        });
    }

    @Override
    public void getHotSearch(HotWordSearchBean hotWordSearchBean) {

    }

    @Override
    public void getWordSearch(WordSearchBean wordSearchBean) {
        if(wordSearchBean.getTextToal()!=0||wordSearchBean.getTitleToal()!=0){
            Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);

            Bundle bundle = new Bundle();

            bundle.putString("keyword", word);

            intent.putExtras(bundle);

            startActivity(intent);
        }else{
            Toast.makeText(SearchActivity.this, "关键词错误", Toast.LENGTH_SHORT).show();
        }
    }
}