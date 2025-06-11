package com.iyuba.voaEnglish.activity;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.rgb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.ActivityVoaDetailBinding;
import com.iyuba.voaEnglish.model.bean.EvaHistoryBean;
import com.iyuba.voaEnglish.view.home.EvaHistoryContract;


//此活动需要四个fragment
public class VoaDetailActivity extends AppCompatActivity implements EvaHistoryContract.EvaHistoryView {

    private ActivityVoaDetailBinding binding;


    ///声明四个fragment

    private IntroductionFragment introductionFragment;

    private OriginalFragment originalFragment;

    private EvaluationFragment evaluationFragment;

    private RankingFragment rankingFragment;

    private ReadFragment readFragment;




    private int RESULT_CODE_STARTAUDIO;


    private int gray = Color.parseColor("#b1b1b1");





    //存在会导致程序崩溃
    protected void onDestroy() {
        super.onDestroy();
        //获取结束时间，并上传听力进度
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        binding = ActivityVoaDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.toolbar.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        Log.d("fang55","getactivity");
        binding.toolbar.toolbarIvTitle.setText(Constant.title);

        if(!isNetConnected(this)){
            binding.evaluation.setVisibility(View.GONE);
            binding.ranking.setVisibility(View.GONE);
        }


        //获取当前时间


        binding.original.setTextColor(BLACK);
        replaceFragment("ORI");


        binding.introduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.introduction.setTextColor(BLACK);
                binding.original.setTextColor(gray);
                binding.evaluation.setTextColor(gray);
                binding.ranking.setTextColor(gray);
                binding.read.setTextColor(gray);
                replaceFragment("INT");
            }
        });
        binding.original.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.original.setTextColor(BLACK);
                binding.introduction.setTextColor(gray);
                binding.evaluation.setTextColor(gray);
                binding.ranking.setTextColor(gray);
                binding.read.setTextColor(gray);
                replaceFragment("ORI");
            }
        });
        binding.evaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.evaluation.setTextColor(BLACK);
                binding.introduction.setTextColor(gray);
                binding.original.setTextColor(gray);
                binding.ranking.setTextColor(gray);
                binding.read.setTextColor(gray);
                originalFragment.upStuduyRecord();
                evaluationFragment.geteva();
                replaceFragment("EVA");

            }
        });
        binding.ranking.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (Constant.useruid == 0){
                    AlertDialog alertDialog2 = new AlertDialog.Builder(VoaDetailActivity.this)
                            .setTitle("登录后才可看排行榜")
                            .setMessage("是否去登录")
                            .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(VoaDetailActivity.this, LoginActivity.class);

                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(VoaDetailActivity.this, "您还未登录", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();
                }else{
                    binding.ranking.setTextColor(BLACK);
                    binding.introduction.setTextColor(gray);
                    binding.original.setTextColor(gray);
                    binding.evaluation.setTextColor(gray);
                    binding.read.setTextColor(gray);

                    replaceFragment("RAN");
                }
            }
        });

        binding.read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.evaluation.setTextColor(gray);
                binding.introduction.setTextColor(gray);
                binding.original.setTextColor(gray);
                binding.ranking.setTextColor(gray);
                binding.read.setTextColor(BLACK);
                readFragment.getRead();
                replaceFragment("READ");

            }
        });

    }

    private void replaceFragment(String name) {

        FragmentManager fragmentManager = getSupportFragmentManager();



        introductionFragment = (IntroductionFragment) fragmentManager.findFragmentByTag("INT");
        if (introductionFragment == null) {

            introductionFragment = IntroductionFragment.newInstance(null, null);
        }
        originalFragment = (OriginalFragment) fragmentManager.findFragmentByTag("ORI");
        if (originalFragment == null) {

            originalFragment = OriginalFragment.newInstance(null, null);
        }
        evaluationFragment = (EvaluationFragment) fragmentManager.findFragmentByTag("EVA");
        if (evaluationFragment == null) {

            evaluationFragment = EvaluationFragment.newInstance(null, null);
        }
        rankingFragment = (RankingFragment) fragmentManager.findFragmentByTag("RAN");
        if (rankingFragment == null) {

            rankingFragment = RankingFragment.newInstance(null, null);
        }
        readFragment = (ReadFragment) fragmentManager.findFragmentByTag("READ");
        if (readFragment == null) {

            readFragment = ReadFragment.newInstance(null, null);
        }

        FragmentTransaction transaction = fragmentManager.beginTransaction();




        if (!introductionFragment.isAdded()) {

            transaction.add(R.id.frameLayout_detail, introductionFragment, "INT");
        }

        if (!originalFragment.isAdded()) {

            transaction.add(R.id.frameLayout_detail, originalFragment, "ORI");
        }
        if (!evaluationFragment.isAdded()) {

            transaction.add(R.id.frameLayout_detail, evaluationFragment, "EVA");
        }
        if (!rankingFragment.isAdded()) {

            transaction.add(R.id.frameLayout_detail, rankingFragment, "RAN");
        }

        if (!readFragment.isAdded()) {

            transaction.add(R.id.frameLayout_detail, readFragment, "READ");
        }

        if (name.equals("ywad")) {

            transaction.show(introductionFragment);
            transaction.hide(originalFragment);
            transaction.hide(evaluationFragment);
            transaction.hide(rankingFragment);
            transaction.hide(readFragment);


        } else if (name.equals("rdad")) {

            transaction.show(introductionFragment);
            transaction.hide(originalFragment);
            transaction.hide(evaluationFragment);
            transaction.hide(rankingFragment);
            transaction.hide(readFragment);


        }else if (name.equals("INT")) {

            transaction.show(introductionFragment);
            transaction.hide(originalFragment);
            transaction.hide(evaluationFragment);
            transaction.hide(rankingFragment);
            transaction.hide(readFragment);


        } else if(name.equals("ORI")){
            transaction.hide(introductionFragment);
            transaction.show(originalFragment);
            transaction.hide(evaluationFragment);
            transaction.hide(rankingFragment);
            transaction.hide(readFragment);


        } else if(name.equals("READ")){
            transaction.hide(introductionFragment);
            transaction.hide(originalFragment);
            transaction.hide(evaluationFragment);
            transaction.hide(rankingFragment);
            transaction.show(readFragment);


        }else if(name.equals("EVA")){

            transaction.hide(introductionFragment);
            transaction.hide(originalFragment);
            transaction.show(evaluationFragment);
            transaction.hide(rankingFragment);
            transaction.hide(readFragment);

            originalFragment.changePage();
            evaluationFragment.onResume();

        }else{
            transaction.hide(introductionFragment);
            transaction.hide(originalFragment);
            transaction.hide(evaluationFragment);
            transaction.show(rankingFragment);
            transaction.hide(readFragment);


            originalFragment.changePage();
            rankingFragment.onResume();
        }
        transaction.commit();

    }
    private boolean isNetConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
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
    public void getEvaHistory(EvaHistoryBean evaHistoryBean) {

    }

    public void TitleChange(){


        binding.toolbar.toolbarIvTitle.setText(Constant.title);

    }

//    @Override
//    public void onResume() {
//        super.onResume();  // Always call the superclass method first
//
//        binding.toolbar.toolbarIvTitle.setText(Constant.title);
//
//    }


}