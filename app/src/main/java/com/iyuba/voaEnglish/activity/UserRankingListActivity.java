package com.iyuba.voaEnglish.activity;

import android.icu.util.Calendar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.adapter.UserRankingDetailAdapter;
import com.iyuba.voaEnglish.databinding.ActivityUserListBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.UserRankingDetailBean;
import com.iyuba.voaEnglish.presenter.home.UserRankingDetailPresenter;
import com.iyuba.voaEnglish.view.home.UserRankingDetailContract;

import java.io.IOException;
import java.util.List;

/*
排行榜详情
 */

public class UserRankingListActivity extends AppCompatActivity implements UserRankingDetailContract.UserRankingDetailView {

    private ActivityUserListBinding binding;

    private LinearLayoutManager layoutManager;

    private UserRankingDetailPresenter userRankingDetailPresenter;

    private int uid, score, sentence;

    private String img, name;

    private boolean isprepar = false;

    private UserRankingDetailAdapter userRankingDetailAdapter;

    MediaPlayer mediaPlayer = new MediaPlayer();

    public void onDestroy() {

        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.toolbar.toolbarIvTitle.setText(Constant.title);

        //以后别忘记写，好几次错误了

        userRankingDetailPresenter = new UserRankingDetailPresenter();
        userRankingDetailPresenter.attchView(this);

        Bundle bundle = this.getIntent().getExtras();

        uid = bundle.getInt("uid");

        score = bundle.getInt("score");

        sentence = bundle.getInt("sentence");

        img = bundle.getString("img");

        name = bundle.getString("name");


        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(calendar.YEAR);

        int month = calendar.get(calendar.MONTH) + 1;

        String monthCopy = month + "";

        if (monthCopy.length() == 1) {
            monthCopy = "0" + monthCopy;
        }

        int day = calendar.get(calendar.DAY_OF_MONTH);

        String dayCopy = day + "";
        if (dayCopy.length() == 1) {
            dayCopy = "0" + dayCopy;
        }


        String date = (year + "") + "-" + monthCopy + "-" + dayCopy;

        String sign = MD5.md5(uid + "getWorksByUserId" + date);

//        Log.d("chen",Constant.voaid+"xxx"+uid+date);


        userRankingDetailPresenter.getUserRankingDetail("2,4", "voa", Constant.voaid, uid, sign);


        layoutManager = new LinearLayoutManager(this);

        binding.homeRv.setLayoutManager(layoutManager);


        binding.rankingName.setText(name);

        binding.rankingScore.setText("总分：" + score + "");

        binding.rankingSentence.setText("句子数：" + sentence + "");

        Glide.with(this).load(img).into(binding.rankingImage);

        if (sentence == 0) {
            binding.rankingAvg.setText("平均分：" + 0 + "");
        }else{
            binding.rankingAvg.setText("平均分：" + (score / sentence) + "");
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
    public void getUserRankingDetail(UserRankingDetailBean userRankingDetailBean) {

        //写入适配器，判断paraid和idindex是否相同来判断是否显示


        List<UserRankingDetailBean.DataDTO> list = userRankingDetailBean.getData();
        for (int i = 0; i < list.size(); i++) {
            Log.d("fang777",list.get(i).getParaid()+","+list.get(i).getIdIndex()+"句子数"+list.size());
            Log.d("fang777",list.get(i).getScore()+"**"+"句子数"+list.size());

        }

        userRankingDetailAdapter = new UserRankingDetailAdapter(UserRankingListActivity.this, list);

        binding.homeRv.setAdapter(userRankingDetailAdapter);
        //userRankingDetailAdapter.notifyDataSetChanged();


        userRankingDetailAdapter.setCallBack(new UserRankingDetailAdapter.CallBack() {

            @Override
            public void clickPlay(int position) {

                //mediaPlayer = ;

                UserRankingDetailBean.DataDTO rankingData = userRankingDetailAdapter.getItem(position);


                //if(isprepar){
                if (!userRankingDetailAdapter.isPlay()) {


                    String src = "http://userspeech.iyuba.cn/voa/" + rankingData.getShuoShuo();

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            isprepar = true;
                        }
                    });

                    try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(src);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mediaPlayer) {
                            mediaPlayer.start();
                        }
                    });
                    userRankingDetailAdapter.setPlay(true);
                    userRankingDetailAdapter.setChoosePosition(position);
                    userRankingDetailAdapter.notifyDataSetChanged();


                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            userRankingDetailAdapter.setPlay(false);
                            userRankingDetailAdapter.setChoosePosition(position);
                            userRankingDetailAdapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    mediaPlayer.pause();
                    mediaPlayer.release();
                    userRankingDetailAdapter.setPlay(false);
                    userRankingDetailAdapter.setChoosePosition(position);
                    userRankingDetailAdapter.notifyDataSetChanged();
                }

            }
            //}
        });


    }
}