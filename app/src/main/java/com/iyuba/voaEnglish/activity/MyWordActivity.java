package com.iyuba.voaEnglish.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.MyWordAdapter;
import com.iyuba.voaEnglish.databinding.ActivityMyWordBinding;
import com.iyuba.voaEnglish.model.bean.JoinWordBookBean;
import com.iyuba.voaEnglish.model.bean.MyWordBean;
import com.iyuba.voaEnglish.presenter.home.JoinWordBookPresenter;
import com.iyuba.voaEnglish.presenter.home.MyWordPresenter;
import com.iyuba.voaEnglish.view.home.JoinWordBookContract;
import com.iyuba.voaEnglish.view.home.MyWordContract;
import com.sdiyuba.rewardgold.util.PullUpLoading;

import java.util.List;

public class MyWordActivity extends AppCompatActivity implements MyWordContract.MyWordView, JoinWordBookContract.JoinWordBookView {

    private ActivityMyWordBinding binding;

    private MyWordAdapter myWordAdpter;

    private MyWordPresenter myWordPresenter;

    private PullUpLoading wordPullUpLoading;

    private JoinWordBookPresenter joinWordPresenter;

    MediaPlayer mediaPlayer = new MediaPlayer();

    private boolean isloadmore = true;

    private boolean isDel = false;

    int size = 0;
    private int page = 1;

    private boolean isplaying = true;


    public void onDestroy() {

        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myWordPresenter = new MyWordPresenter();
        myWordPresenter.attchView(this);

        joinWordPresenter = new JoinWordBookPresenter();
        joinWordPresenter.attchView(this);

        binding = ActivityMyWordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        myWordPresenter.getMyWord(Constant.useruid, 1, 10, "json");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.wordRv.setLayoutManager(linearLayoutManager);


        binding.toorbar0.toolbarIvTitle.setText("我的生词");
        binding.toorbar0.toolbarIvSearch.setImageResource(R.drawable.shanchu);
        binding.toorbar0.toolbarIvSearch.setVisibility(View.VISIBLE);

        binding.toorbar0.toolbarIvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog2 = new AlertDialog.Builder(MyWordActivity.this)
                        .setTitle("提示")
                        .setMessage("长按单词删除，即可从生词本中删除")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .create();
                alertDialog2.show();
            }
        });

        //上滑加载
        wordPullUpLoading = new PullUpLoading(linearLayoutManager) {
            @Override
            public void onLoadMore() {

                if (page<=size){
                    page =page+1;
                    binding.loadingLoadend.setVisibility(View.VISIBLE);
                    binding.wordRv.postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            if (myWordAdpter == null) {
                                return;
                            }


                            myWordPresenter.getMyWord(Constant.useruid, page, 10, "json");
                            binding.loadingLoadend.setVisibility(View.GONE);
                        }
                    }, 1000);

                }


            }
        };
        binding.wordRv.addOnScrollListener(wordPullUpLoading);


        // 返回
        binding.toorbar0.toolbarIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void getMyWord(MyWordBean myWordBean) {

        size  = myWordBean.getTotalPage();
        if (isloadmore) {
            if (wordPullUpLoading.isLoading()) {   //执行上拉刷新


                List<MyWordBean.DataDTO> list = myWordBean.getData();

                if (myWordAdpter == null) {

                    myWordAdpter = new MyWordAdapter(this,list);
                    binding.wordRv.setAdapter(myWordAdpter);

                } else {


                    //注意  !添加是 adpter.getDatas ;
                    List<MyWordBean.DataDTO> dataDTOS = myWordAdpter.getList();
                    dataDTOS.addAll(list);   //添加进去 ,并且刷新

                    myWordAdpter.notifyDataSetChanged();
                }


            } else {

                List<MyWordBean.DataDTO> list = myWordBean.getData();

                if (myWordAdpter == null) {
                    myWordAdpter = new MyWordAdapter( this,list);
                    binding.wordRv.setAdapter(myWordAdpter);
                }
                myWordAdpter.notifyDataSetChanged();

            }
            wordPullUpLoading.setLoading(false);
            isloadmore = false;

        } else {
            if (!(myWordBean.getTotalPage() == page - 1)) {

                if (wordPullUpLoading.isLoading()) {   //执行上拉刷新


                    List<MyWordBean.DataDTO> list = myWordBean.getData();

                    if (myWordAdpter == null) {

                        myWordAdpter = new MyWordAdapter( this,list);
                        binding.wordRv.setAdapter(myWordAdpter);

                    } else {


                        //注意  !添加是 adpter.getDatas ;
                        List<MyWordBean.DataDTO> dataDTOS = myWordAdpter.getList();
                        dataDTOS.addAll(list);   //添加进去 ,并且刷新

                        myWordAdpter.notifyDataSetChanged();
                    }


                } else {

                    List<MyWordBean.DataDTO> list = myWordBean.getData();

                    if (myWordAdpter == null) {
                        myWordAdpter = new MyWordAdapter( this,list);
                        binding.wordRv.setAdapter(myWordAdpter);
                    }
                    myWordAdpter.notifyDataSetChanged();

                }
                wordPullUpLoading.setLoading(false);

            }else{

            }

        }


        //这里不是触发,只是承接adpter里设置得样式点击事件   在这里设置触发事件以后得操作(网络请求等)
        myWordAdpter.setCallBack(new MyWordAdapter.CallBack() {
            @Override
            public void clickDelWord() {
                //删除生词
                if (myWordAdpter!=null){
                    isloadmore = true;
                    joinWordPresenter.joinWordBook("Iyuba", Constant.useruid, "delete", myWordAdpter.getWordKey(), "json");

                }
            }

            @Override
            public void clickListenWord() {
                if (isplaying){

                    //筛选有的音频为空时闪退
                    if (myWordAdpter.getWordKeyAudio().length()>5){
                        if (mediaPlayer != null) {
                            mediaPlayer = MediaPlayer.create(MyWordActivity.this, Uri.parse(myWordAdpter.getWordKeyAudio()));
                            mediaPlayer.start();
                            isplaying = false;
                            //监听播放完成
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // 播放完成后的逻辑处理
                                    isplaying = true;

                                }
                            });
                        } else {
                            // 处理 mediaPlayer 为空的情况

                            mediaPlayer =new MediaPlayer();
                            mediaPlayer = MediaPlayer.create(MyWordActivity.this, Uri.parse(myWordAdpter.getWordKeyAudio()));
                            mediaPlayer.start();
                            isplaying = false;
                            //监听播放完成
                            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    // 播放完成后的逻辑处理
                                    isplaying = true;

                                }
                            });
                        }

                    }


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
    public void joinWordBook(JoinWordBookBean joinWordBookBean) {
        if (joinWordBookBean.getResult() == 1) {
            isDel = true;
            myWordAdpter = null;
            page=1;
            //重新请求生词本
            myWordPresenter.getMyWord(Constant.useruid, 1, 10, "json");

            Toast.makeText(MyWordActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MyWordActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }
}