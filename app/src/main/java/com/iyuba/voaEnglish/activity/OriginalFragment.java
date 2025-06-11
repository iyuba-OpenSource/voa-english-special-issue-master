package com.iyuba.voaEnglish.activity;

import static android.content.Context.MODE_PRIVATE;
import static com.efs.sdk.base.newsharedpreferences.SharedPreferencesUtils.getSharedPreferences;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iyuba.imooclib.data.model.Teacher;
import com.iyuba.module.toolbox.DensityUtil;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.NoNetworkPageAdapter;
import com.iyuba.voaEnglish.adapter.VoaPageAdapter;
import com.iyuba.voaEnglish.databinding.FragmentOriginalBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.AdEntryBean;
import com.iyuba.voaEnglish.model.bean.CollectBean;
import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.GetWordsBean;
import com.iyuba.voaEnglish.model.bean.JoinWordBookBean;
import com.iyuba.voaEnglish.model.bean.MyCollectBean;
import com.iyuba.voaEnglish.model.bean.PdfBean;
import com.iyuba.voaEnglish.model.bean.StudyRecordByTestModeBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.presenter.home.AdEntryPresenter;
import com.iyuba.voaEnglish.presenter.home.CollectPresenter;
import com.iyuba.voaEnglish.presenter.home.GetWordsPresenter;
import com.iyuba.voaEnglish.presenter.home.HomePresenter;
import com.iyuba.voaEnglish.presenter.home.JoinWordBookPresenter;
import com.iyuba.voaEnglish.presenter.home.MyCollectPresenter;
import com.iyuba.voaEnglish.presenter.home.PagePresenter;
import com.iyuba.voaEnglish.presenter.home.PdfPresenter;
import com.iyuba.voaEnglish.presenter.home.UpStudyRecordPresenter;
import com.iyuba.voaEnglish.sqlitedb.OriginalTableDb;
import com.iyuba.voaEnglish.view.home.AdEntryContract;
import com.iyuba.voaEnglish.view.home.CollectContract;
import com.iyuba.voaEnglish.view.home.GetWordsContract;
import com.iyuba.voaEnglish.view.home.HomeContract;
import com.iyuba.voaEnglish.view.home.JoinWordBookContract;
import com.iyuba.voaEnglish.view.home.MyCollectContract;
import com.iyuba.voaEnglish.view.home.PageContract;
import com.iyuba.voaEnglish.view.home.PdfContact;
import com.iyuba.voaEnglish.view.home.UpStudyRecordContract;
import com.yd.saas.base.interfaces.AdViewBannerListener;
import com.yd.saas.base.interfaces.AdViewSpreadListener;
import com.yd.saas.config.exception.YdError;
import com.yd.saas.ydsdk.YdBanner;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OriginalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OriginalFragment extends Fragment implements HomeContract.HomeView, MyCollectContract.MyCollectView, CollectContract.CollectView, PdfContact.PdfView, UpStudyRecordContract.UpStudyRecordView, GetWordsContract.GetWordsView, JoinWordBookContract.JoinWordBookView, AdEntryContract.AdEntryView, AdViewSpreadListener, AdViewBannerListener , PageContract.PageView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OriginalFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OriginalFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OriginalFragment newInstance(String param1, String param2) {
        OriginalFragment fragment = new OriginalFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    private FragmentOriginalBinding binding;

    private AdEntryPresenter adEntryPresenter;
    FrameLayout co_fl_ad;
    private LinearLayoutManager layoutManager;

    private LinearLayoutManager linearLayoutManager;

    private NoNetworkPageAdapter noNetworkPageAdapter;

    private HomePresenter homePresenter;

    private MyCollectPresenter myCollectPresenter;

    private CollectPresenter collectPresenter;

    private PdfPresenter pdfPresenter;

    private int voaid;

    private boolean iscollect;

    private int position;

    private boolean isADshow = true;

    private boolean isAdFirst = true; //再执行一次广告请求
    private boolean isIn = true;
    private int gColor = Color.parseColor("#1296db");

    private boolean isplay = true;

    private boolean isInPlay = true;

    private boolean isColl = false;

    private String sound;

    private MediaPlayer mediaPlayer =new MediaPlayer();

    private MediaPlayer wordMediaPlayer;

    private VoaPageAdapter voaPageAdapter;

    private HandlerThread handlerThread;

    private Handler handler;

    private PagePresenter pagePresenter;

    private Activity activity;

    private PopupWindow popupWindow;


    //取词
    private GetWordsPresenter getWordsPresenter;

    private PopupWindow popupWindowWord;


    private DecimalFormat decimalFormat;

    //判断此文章是否已收藏
    private boolean isCollect = false;

    //遮蔽层功能
    ImageView originalSpeedImage, originalCollectImage, originalSizeImage, originalPdfImage, originalModelImage;

    //收藏
    TextView originalCollectText;

    //倍速
    LinearLayout speed05,speed10,speed15,speed20;

    ImageView speedimage05, speedimage10,speedimage15,speedimage20;

    //字号调节
    LinearLayout smallSize, smallerSize, normalSize, bigSize, bigerSize;

    //字号选择图
    ImageView smallimage, smallerimage, normalimage, bigimage, bigerimage;



    private String adkey = "0136"; //csj  0087

    private String ad_title = "ads4";
    ImageView wordAudio;

    TextView wordDetail, wordPron, wordMeaning, wordCancel, wordBook;

    private JoinWordBookPresenter joinWordBookPresenter;


    //顺序，单曲，随机播放
    TextView originalModelText;

    LinearLayout changeSpeed, changeSize, pdfPage;

    //开始结束时间
    private String BeginTime, EndTime;

    int AUDIO_MODEL =0;

    private UpStudyRecordPresenter upStudyRecordPresenter;

    private float speed = 1;

    //数据库
    private OriginalTableDb originalTableDb;

    private SQLiteDatabase sqLiteDatabase;

    public void onDestroy() {

        super.onDestroy();

        upStuduyRecord();
        isADshow = false;
        if (handlerThread != null) {

            handlerThread.quit();
        }

        if (mediaPlayer != null) {

            mediaPlayer.release();
        }
        homePresenter.detachView();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Network.getInstance().init();
        homePresenter = new HomePresenter();
        homePresenter.attchView(this);

        myCollectPresenter = new MyCollectPresenter();
        myCollectPresenter.attchView(this);

        collectPresenter = new CollectPresenter();
        collectPresenter.attchView(this);

        pdfPresenter = new PdfPresenter();
        pdfPresenter.attchView(this);

        upStudyRecordPresenter = new UpStudyRecordPresenter();
        upStudyRecordPresenter.attchView(this);

        getWordsPresenter = new GetWordsPresenter();
        getWordsPresenter.attchView(this);

        joinWordBookPresenter = new JoinWordBookPresenter();
        joinWordBookPresenter.attchView(this);

        adEntryPresenter = new AdEntryPresenter();
        adEntryPresenter.attchView(this);

        pagePresenter=new PagePresenter();
        pagePresenter.attchView(this);

        decimalFormat = new DecimalFormat("#00");
        startHandler();

        //获取当前时间

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        BeginTime = formatter.format(date);

        SharedPreferences aud_model= getActivity().getSharedPreferences("AUDIO_MODEl", Context .MODE_PRIVATE);
        AUDIO_MODEL=aud_model.getInt("model",0);


    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = getActivity();

        originalTableDb = new OriginalTableDb(requireContext(), "originalTable.db", null, 1);


        sqLiteDatabase = originalTableDb.getReadableDatabase();


        Bundle bundle = requireActivity().getIntent().getExtras();


        if (bundle != null && isIn) {

            voaid = bundle.getInt("voaid");

            sound = bundle.getString("sound");

            position = bundle.getInt("position");

            iscollect = bundle.getBoolean("iscollect");

            isIn = false;
        }

        binding = FragmentOriginalBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        //广告
        co_fl_ad = binding.coFlAd.findViewById(R.id.co_fl_ad);


        linearLayoutManager = new LinearLayoutManager(requireContext());
        binding.homeNoNetwork.setLayoutManager(linearLayoutManager);


        //会员去广告
        if (Constant.vipStatus != 0) {

            co_fl_ad.setVisibility(View.GONE);
        } else {

            adEntryPresenter.getAdEntryAll(2011, 4, Constant.useruid);

        }


        if (isNetConnected(requireContext())) {
            View popupView = getLayoutInflater().inflate(R.layout.pop_original, null);
            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);


            changeSpeed = popupView.findViewById(R.id.change_speed);
            changeSize = popupView.findViewById(R.id.text_change_size);
            pdfPage = popupView.findViewById(R.id.pdf);
            originalCollectText = popupView.findViewById(R.id.original_collect_text);


            //倍速
            speed05 = popupView.findViewById(R.id.speed05);
            speed10 = popupView.findViewById(R.id.speed1);
            speed15 = popupView.findViewById(R.id.speed15);
            speed20 = popupView.findViewById(R.id.speed20);


            speedimage05 =popupView.findViewById(R.id.speed_image05);
            speedimage10 =popupView.findViewById(R.id.speed_image10);
            speedimage15 =popupView.findViewById(R.id.speed_image15);
            speedimage20 =popupView.findViewById(R.id.speed_image20);

            //字体
            smallSize = popupView.findViewById(R.id.text_small_size);
            smallerSize = popupView.findViewById(R.id.text_smaller_size);
            normalSize = popupView.findViewById(R.id.normal_size);
            bigerSize = popupView.findViewById(R.id.biger_size);
            bigSize = popupView.findViewById(R.id.big_size);

            smallimage = popupView.findViewById(R.id.text_small_image);
            smallerimage = popupView.findViewById(R.id.text_smaller_size_image);
            normalimage = popupView.findViewById(R.id.text_normal_size_image);
            bigerimage = popupView.findViewById(R.id.text_biger_size_image);
            bigimage = popupView.findViewById(R.id.text_big_image);


            //单曲播放等模式
            originalModelText = popupView.findViewById(R.id.original_model_text);
            originalModelImage = popupView.findViewById(R.id.original_model_image);

            if (AUDIO_MODEL == 0) {
                //单曲播放
                originalModelImage.setImageResource(R.drawable.bofangmodel);
                originalModelText.setText("单曲播放");

            } else if (AUDIO_MODEL == 1) {
                //单曲循环
                originalModelImage.setImageResource(R.drawable.danquxunhuan);
                originalModelText.setText("单曲循环");

            } else if (AUDIO_MODEL == 2) {
                //列表播放
                originalModelImage.setImageResource(R.drawable.shunxubofang);
                originalModelText.setText("列表循环");
            } else {
                //随机播放
                originalModelImage.setImageResource(R.drawable.suijibofang);
                originalModelText.setText("随机播放");

            }


            if (Constant.useruid != 0) {
                Date sr = new Date();
                long res1 = sr.getTime();
                long res = (res1 - 8 * 60 * 1000) / (24 * 60 * 60 * 1000) + 1;
                String sign = MD5.md5("iyuba" + Constant.useruid + "voa" + 201 + res);
                myCollectPresenter.getMyCollect(Constant.useruid, "voa", 201, 0, "json", sign);
            }

            while (position > 9) {
                position = position / 10;
            }
            layoutManager = new LinearLayoutManager(requireContext());

            binding.homeRv.setLayoutManager(layoutManager);

            //原文音乐播放
            if (iscollect) {
                AUDIO_MODEL= 0;
                Constant.AUDIO_MODEL =0;
                homePresenter.getVoaDetail("json", voaid);



                try {
                    mediaPlayer.setDataSource(Constant.audioSound);
//                    mediaPlayer.prepare();//同步
                    mediaPlayer.prepareAsync();//异步加载
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }


            } else {
                homePresenter.getVoaDetail("json", Constant.voaid);
                try {
                    mediaPlayer.setDataSource(Constant.audioSound);
//                    mediaPlayer.prepare();//同步
                    mediaPlayer.prepareAsync();//异步加载
                    mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            mediaPlayer.start();
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            binding.qnCnChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (voaPageAdapter.isChangeEn() == false) {
                        voaPageAdapter.setChangeEn(true);
                        voaPageAdapter.notifyDataSetChanged();
                        binding.qnCnChange.setImageResource(R.drawable.qen);
                    } else {
                        voaPageAdapter.setChangeEn(false);
                        voaPageAdapter.notifyDataSetChanged();
                        binding.qnCnChange.setImageResource(R.drawable.qcn);
                    }

                }
            });

            binding.speedPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Constant.useruid == 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可使用此功能")
                                .setMessage("是否去登录")
                                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(requireActivity(), LoginActivity.class);

                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    } else {
                        if (Constant.vipStatus > 0) {

                            if (!isplay) {

                                if (speed < 2) {
                                    speed = (float) (speed + 0.5);
                                } else {
                                    speed = 0.5F;
                                }

                                PlaybackParams playbackParams = null;
                                playbackParams = mediaPlayer.getPlaybackParams();
                                playbackParams.setSpeed(speed);
                                mediaPlayer.setPlaybackParams(playbackParams);

                                Toast.makeText(requireContext(), speed + "倍速", Toast.LENGTH_SHORT).show();

                            }

                        } else {
                            AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                    .setMessage("倍速需要VIP权限，是否去开通vip？")
                                    .setPositiveButton("去开通会员", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Intent intent = new Intent(requireActivity(), VIPActivity.class);

                                            startActivity(intent);
                                        }
                                    })

                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .create();
                            alertDialog2.show();
                        }
                    }


                }
            });

            //弹出更多功能弹窗
            binding.more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    popupWindow.showAtLocation(binding.oriLl, Gravity.BOTTOM, 0, 0);
                    WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
                    lp.alpha = 0.5f;
                    requireActivity().getWindow().setAttributes(lp);
                }
            });
            LinearLayout liubai = popupView.findViewById(R.id.liubai);
            liubai.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSpeed.setVisibility(View.GONE);
                    changeSize.setVisibility(View.GONE);
                    pdfPage.setVisibility(View.GONE);
                    popupWindow.dismiss();
                    WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    requireActivity().getWindow().setAttributes(lp);
                }
            });
            //收藏
            originalCollectImage = popupView.findViewById(R.id.original_collect_image);
            originalCollectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSpeed.setVisibility(View.GONE);
                    changeSize.setVisibility(View.GONE);
                    pdfPage.setVisibility(View.GONE);
                    //已经收藏
                    if (Constant.useruid != 0) {
                        if (isCollect) {
                            //取消收藏
                            collectPresenter.getCollect("Iyuba", 0, 201, Constant.useruid, "del", Constant.voaid, 0, "voa", "json");
                            originalCollectText.setText("收藏");
                            originalCollectImage.setImageResource(R.drawable.collect);
                            isCollect = false;
                        } else {
                            //收藏
                            collectPresenter.getCollect("Iyuba", 0, 201, Constant.useruid, "upt", Constant.voaid, 0, "voa", "json");
                            originalCollectText.setText("已收藏");
                            originalCollectImage.setImageResource(R.drawable.collect1);
                            isCollect = true;
                        }
                    } else {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可使用此功能")
                                .setMessage("是否去登录")
                                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(requireActivity(), LoginActivity.class);

                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    }
                }
            });
            //倍速
            originalSpeedImage = popupView.findViewById(R.id.original_speed_image);
            originalSpeedImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSize.setVisibility(View.GONE);
                    pdfPage.setVisibility(View.GONE);
                    if (Constant.useruid == 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可使用此功能")
                                .setMessage("是否去登录")
                                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(requireActivity(), LoginActivity.class);

                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    } else {
                        if (Constant.vipStatus > 0) {
                            changeSpeed.setVisibility(View.VISIBLE);


                            if (!isplay) {
                                speed05.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        speedimage05.setVisibility(View.VISIBLE);
                                        speedimage10.setVisibility(View.INVISIBLE);
                                        speedimage15.setVisibility(View.INVISIBLE);
                                        speedimage20.setVisibility(View.INVISIBLE);
                                        PlaybackParams playbackParams;
                                        playbackParams = mediaPlayer.getPlaybackParams();
                                        playbackParams.setSpeed(0.5F);
                                        mediaPlayer.setPlaybackParams(playbackParams);
                                        originalSpeedImage.setImageResource(R.drawable.hlaf);
                                    }
                                });
                                speed10.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        speedimage05.setVisibility(View.INVISIBLE);
                                        speedimage10.setVisibility(View.VISIBLE);
                                        speedimage15.setVisibility(View.INVISIBLE);
                                        speedimage20.setVisibility(View.INVISIBLE);
                                        PlaybackParams playbackParams;
                                        playbackParams = mediaPlayer.getPlaybackParams();
                                        playbackParams.setSpeed(1.0F);
                                        mediaPlayer.setPlaybackParams(playbackParams);
                                        originalSpeedImage.setImageResource(R.drawable.speed1);
                                    }
                                });
                                speed15.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        speedimage05.setVisibility(View.INVISIBLE);
                                        speedimage10.setVisibility(View.INVISIBLE);
                                        speedimage15.setVisibility(View.VISIBLE);
                                        speedimage20.setVisibility(View.INVISIBLE);
                                        PlaybackParams playbackParams;
                                        playbackParams = mediaPlayer.getPlaybackParams();
                                        playbackParams.setSpeed(1.5F);
                                        mediaPlayer.setPlaybackParams(playbackParams);
                                        originalSpeedImage.setImageResource(R.drawable.onefive);
                                    }
                                });
                                speed20.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        speedimage05.setVisibility(View.INVISIBLE);
                                        speedimage10.setVisibility(View.INVISIBLE);
                                        speedimage15.setVisibility(View.INVISIBLE);
                                        speedimage20.setVisibility(View.VISIBLE);
                                        PlaybackParams playbackParams;
                                        playbackParams = mediaPlayer.getPlaybackParams();
                                        playbackParams.setSpeed(2.0F);
                                        mediaPlayer.setPlaybackParams(playbackParams);
                                        originalSpeedImage.setImageResource(R.drawable.two);
                                    }
                                });
                            }

                        } else {
                            AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                    .setMessage("倍速需要VIP权限，是否去开通vip？")
                                    .setPositiveButton("去开通会员", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Intent intent = new Intent(requireActivity(), VIPActivity.class);

                                            startActivity(intent);
                                        }
                                    })

                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .create();
                            alertDialog2.show();
                        }
                    }

                }
            });

            //调整字号
            originalSizeImage = popupView.findViewById(R.id.original_size_image);
            originalSizeImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    changeSpeed.setVisibility(View.GONE);
                    pdfPage.setVisibility(View.GONE);
                    if (Constant.useruid == 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可使用此功能")
                                .setMessage("是否去登录")
                                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(requireActivity(), LoginActivity.class);

                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    } else {

                        changeSize.setVisibility(View.VISIBLE);

                        //调整字符点击事件
                        smallSize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                smallimage.setVisibility(View.VISIBLE);
                                smallerimage.setVisibility(View.INVISIBLE);
                                normalimage.setVisibility(View.INVISIBLE);
                                bigerimage.setVisibility(View.INVISIBLE);
                                bigimage.setVisibility(View.INVISIBLE);

                                voaPageAdapter.setTextSize(11);
                                voaPageAdapter.notifyDataSetChanged();
                            }
                        });
                        smallerSize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                smallimage.setVisibility(View.INVISIBLE);
                                smallerimage.setVisibility(View.VISIBLE);
                                normalimage.setVisibility(View.INVISIBLE);
                                bigerimage.setVisibility(View.INVISIBLE);
                                bigimage.setVisibility(View.INVISIBLE);

                                voaPageAdapter.setTextSize(13);
                                voaPageAdapter.notifyDataSetChanged();
                            }
                        });
                        normalSize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                smallimage.setVisibility(View.INVISIBLE);
                                smallerimage.setVisibility(View.INVISIBLE);
                                normalimage.setVisibility(View.VISIBLE);
                                bigerimage.setVisibility(View.INVISIBLE);
                                bigimage.setVisibility(View.INVISIBLE);

                                voaPageAdapter.setTextSize(15);
                                voaPageAdapter.notifyDataSetChanged();
                            }
                        });
                        bigerSize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                smallimage.setVisibility(View.INVISIBLE);
                                smallerimage.setVisibility(View.INVISIBLE);
                                normalimage.setVisibility(View.INVISIBLE);
                                bigerimage.setVisibility(View.VISIBLE);
                                bigimage.setVisibility(View.INVISIBLE);
                                voaPageAdapter.setTextSize(17);
                                voaPageAdapter.notifyDataSetChanged();
                            }
                        });
                        bigSize.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                smallimage.setVisibility(View.INVISIBLE);
                                smallerimage.setVisibility(View.INVISIBLE);
                                normalimage.setVisibility(View.INVISIBLE);
                                bigerimage.setVisibility(View.INVISIBLE);
                                bigimage.setVisibility(View.VISIBLE);
                                voaPageAdapter.setTextSize(19);
                                voaPageAdapter.notifyDataSetChanged();
                            }
                        });

//                        } else {
//                            AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
//                                    .setMessage("调整字号需要VIP权限，是否去开通vip？")
//                                    .setPositiveButton("去开通会员", new DialogInterface.OnClickListener() {//添加"Yes"按钮
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                                            Intent intent = new Intent(requireActivity(), VIPActivity.class);
//
//                                            startActivity(intent);
//                                        }
//                                    })
//
//                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//
//                                        }
//                                    })
//                                    .create();
//                            alertDialog2.show();
//                        }
                    }
                }
            });

            originalModelImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSpeed.setVisibility(View.GONE);
                    changeSize.setVisibility(View.GONE);
                    pdfPage.setVisibility(View.GONE);
                    if (!isplay) {


                            //判断是不是从收藏那边跳转过来
                            if (!iscollect) {
                                Constant.AUDIO_MODEL = Constant.AUDIO_MODEL + 1;
                            }

                            if (Constant.AUDIO_MODEL > 3) {
                                Constant.AUDIO_MODEL = 0;
                            }

                        //获取SharedPreferences对象
                        SharedPreferences audio_model = getActivity().getSharedPreferences("AUDIO_MODEl",Context.MODE_PRIVATE);
                        //获取Editor对象的引用
                        SharedPreferences.Editor editor = audio_model.edit();
                        //将获取过来的值放入文件
                        editor.putInt("model", Constant.AUDIO_MODEL);
                        // 提交数据
                        editor.commit();

                        SharedPreferences aud_model= getActivity().getSharedPreferences("AUDIO_MODEl", Context .MODE_PRIVATE);
                        AUDIO_MODEL=aud_model.getInt("model",0);



                        if (AUDIO_MODEL == 0) {
                            //单曲播放
                            originalModelImage.setImageResource(R.drawable.bofangmodel);
                            originalModelText.setText("单曲播放");

                        } else if (AUDIO_MODEL == 1) {
                            //单曲循环
                            originalModelImage.setImageResource(R.drawable.danquxunhuan);
                            originalModelText.setText("单曲循环");

                        } else if (AUDIO_MODEL == 2) {
                            //列表播放
                            originalModelImage.setImageResource(R.drawable.shunxubofang);
                            originalModelText.setText("列表循环");
                        } else {
                            //随机播放
                            originalModelImage.setImageResource(R.drawable.suijibofang);
                            originalModelText.setText("随机播放");

                        }
                    }

                }
            });

            //导出pdf
            originalPdfImage = popupView.findViewById(R.id.original_pdf_image);
            originalPdfImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeSpeed.setVisibility(View.GONE);
                    changeSize.setVisibility(View.GONE);


                    if (Constant.useruid == 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可使用此功能")
                                .setMessage("是否去登录")
                                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(requireActivity(), LoginActivity.class);

                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    } else {
                        if (Constant.vipStatus > 0) {

                            AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                    .setMessage("请选择您要导出的PDF格式？")
                                    .setPositiveButton("纯英文", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            pdfPresenter.getPdf("voa", Constant.voaid, 1);
                                        }
                                    })

                                    .setNegativeButton("中英双语", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            pdfPresenter.getPdf("voa", Constant.voaid, 0);
                                        }
                                    })
                                    .create();
                            alertDialog2.show();


                        } else {
                            AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                    .setMessage("导出PDF需要VIP权限，是否去开通VIP？")
                                    .setPositiveButton("去开通会员", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            Intent intent = new Intent(requireActivity(), VIPActivity.class);

                                            startActivity(intent);
                                        }
                                    })

                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    })
                                    .create();
                            alertDialog2.show();
                        }
                    }
                }
            });

            binding.pagePlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isplay) {
                        binding.pagePlay.setImageResource(R.drawable.bofang);

                        mediaPlayer.start();
                        //执行进度条语句的关键
                        handler.sendEmptyMessage(1);
                        isplay = false;


                    } else {

                        binding.pagePlay.setImageResource(R.drawable.zanting);
                        mediaPlayer.pause();
                        isplay = true;
                    }

                }
            });


            binding.originalSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                    if (mediaPlayer != null) {

                        mediaPlayer.seekTo(seekBar.getProgress());
                    }
                }
            });

            binding.downPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //获取时存在延迟
                    if (voaPageAdapter.getPositionPlay() == voaPageAdapter.getItemCount() - 1) {
                        VoaDetailBean.VoatextDTO dataDTO = voaPageAdapter.getItem(0);
                        mediaPlayer.seekTo((int) dataDTO.getTiming() * 1000);
                    } else {
                        VoaDetailBean.VoatextDTO dataDTO = voaPageAdapter.getItem(voaPageAdapter.getPositionPlay() + 1);
                        mediaPlayer.seekTo((int) dataDTO.getTiming() * 1000);
                    }
                }
            });
            binding.upPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (voaPageAdapter.getPositionPlay() != 0) {
                        VoaDetailBean.VoatextDTO dataDTO = voaPageAdapter.getItem(voaPageAdapter.getPositionPlay() - 1);
                        mediaPlayer.seekTo((int) dataDTO.getTiming() * 1000);

                    } else {
                        VoaDetailBean.VoatextDTO dataDTO = voaPageAdapter.getItem(0);
                        mediaPlayer.seekTo((int) dataDTO.getTiming() * 1000);
                    }

                }
            });
        } else {
            //查询数据库，显示无网络预加载的内容
//            binding.oriNoNetwork.setVisibility(View.VISIBLE);
//            Cursor cursor = sqLiteDatabase.query("originalTable", null, "voaid=" + voaid, null, null, null, null);
//            List<Map<String, String>> list = new ArrayList<>();
//
//
//            while (cursor.moveToNext()) {
//
//                @SuppressLint("Range") String pageEn = cursor.getString(cursor.getColumnIndex("pageEn"));
//                @SuppressLint("Range") String pageCn = cursor.getString(cursor.getColumnIndex("pageCn"));
//
//                Map<String, String> map = new HashMap<>();
//                map.put("pageEn", pageEn);
//                map.put("pageCn", pageCn);
//
//                list.add(map);
//
//            }
//
//            //设置adapter
//
//            noNetworkPageAdapter = new NoNetworkPageAdapter(requireContext(), list);
//            binding.homeNoNetwork.setAdapter(noNetworkPageAdapter);
//            noNetworkPageAdapter.notifyDataSetChanged();
        }


        return view;
    }

    //播放进度条设置

    private void startHandler() {

        handlerThread = new HandlerThread("检测播放与recler的位置");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {


                if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                    int cp = mediaPlayer.getCurrentPosition();
                    int dur = mediaPlayer.getDuration();
                    setViewText(getTimeStr(cp), getTimeStr(dur), cp, dur);
                }
                handler.sendEmptyMessageDelayed(1, 50);
                return false;
            }
        });
    }

    private synchronized void setViewText(String cp, String dur, long cpInt, long durInt) {

        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                binding.originalTvPlayTime.setText(cp);
                binding.originalTvDuration.setText(dur);

                binding.originalSb.setProgress((int) cpInt);
                binding.originalSb.setMax((int) durInt);
                //判断播放完毕，跳转到开始  播放到最后
                if (cp.equals(dur)) {

                    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    EndTime = formatter1.format(date);

                    String sign = MD5.md5(Constant.useruid + BeginTime + EndTime);

                    upStudyRecordPresenter.UpStudyRecord("json", Constant.useruid + "", 201, BeginTime, EndTime, "voa", Constant.voaid + "", "1", "ios", "ios", "127.0.0.1", sign, "1", Constant.SEN_NUM + "", Constant.WORD_NUM + "", 1);
                    //执行完毕
                    if (AUDIO_MODEL== 0) {
                        //单曲结束
                        isplay = true;

                        mediaPlayer.seekTo(0);
                        binding.pagePlay.setImageResource(R.drawable.zanting);
                        mediaPlayer.pause();


                    } else if (AUDIO_MODEL== 1) {
                        //单曲循环
                        mediaPlayer.seekTo(0);
                        binding.originalSb.setProgress(0);
                        binding.originalTvPlayTime.setText("00:00");
                    } else if (AUDIO_MODEL== 2) {
                        position = position + 1;
                        //列表循环播放，从新加载一下此界面
                        while (position > 9) {
                            position = position / 10;
                        }
                        mediaPlayer.pause();
                        handler.sendEmptyMessage(0);
                        homePresenter.getVoaDetail("json", Integer.parseInt(Constant.orlVoaid[position]));
                        Constant.voaid = Integer.parseInt(Constant.orlVoaid[position]);
                        Constant.audioSound = Constant.orlSound[position];

                        pagePresenter.getDetailPage("iphone","json",Constant.voaid);
                        mediaPlayer = MediaPlayer.create(requireActivity(), Uri.parse("http://staticvip.iyuba.cn/sounds/voa" + Constant.orlSound[position]));
                        mediaPlayer.seekTo(0);
                        binding.originalSb.setProgress(0);
                        binding.originalTvPlayTime.setText("00:00");

                    } else {
                        //随机播放
                        mediaPlayer.pause();
                        handler.sendEmptyMessage(0);
                        Random random = new Random();
                        position = random.nextInt(10);
                        homePresenter.getVoaDetail("json", Integer.parseInt(Constant.orlVoaid[position]));
                        Constant.voaid = Integer.parseInt(Constant.orlVoaid[position]);
                        Constant.audioSound = Constant.orlSound[position];
                        pagePresenter.getDetailPage("iphone","json",Constant.voaid);
                        mediaPlayer = MediaPlayer.create(requireActivity(), Uri.parse("http://staticvip.iyuba.cn/sounds/voa" + Constant.orlSound[position]));
                        mediaPlayer.seekTo(0);
                        binding.originalSb.setProgress(0);
                        binding.originalTvPlayTime.setText("00:00");
                    }

                }

                int s = (int) (cpInt / 1000);

                //i,size;

                for (int i = 0, size = voaPageAdapter.getItemCount(); i < size; i++) {

                    VoaDetailBean.VoatextDTO dataDTO = voaPageAdapter.getItem(i);
                    if (s < Double.parseDouble(dataDTO.getEndTiming() + "") && s > Double.parseDouble(dataDTO.getTiming() + "")) {

                        if (voaPageAdapter.getPositionPlay() != i) {
                            voaPageAdapter.setPositionPlay(i);
                            moveToPosition(binding.homeRv, i);
                            voaPageAdapter.notifyDataSetChanged();
                            break;
                        }

                    }


                }


            }
        });
    }


    /**
     * 同步播放
     *
     * @param rv
     * @param position
     */
    private void moveToPosition(RecyclerView rv, int position) {

        int firstItem = rv.getChildLayoutPosition(rv.getChildAt(0));
        int lastItem = rv.getChildLayoutPosition(rv.getChildAt(rv.getChildCount() - 1));
        if (position < firstItem || position > lastItem) {
            rv.smoothScrollToPosition(position);
        } else {
            int movePosition = position - firstItem;
            int top = rv.getChildAt(movePosition).getTop();
            rv.smoothScrollBy(0, top);
        }
    }

    /**
     * 获取播放时间
     *
     * @param mill
     * @return
     */
    private String getTimeStr(long mill) {

        long s = mill / 1000;

        long ts = s % 60;
        long tm = s / 60;


        String cpStr = decimalFormat.format(ts);
        String durStr = decimalFormat.format(tm);

        return durStr + ":" + cpStr;
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
    public void getVoaTitle(VoaBean voaBean, int pages) {

    }

    @Override
    public void getVoaDetail(VoaDetailBean voaDetailBean) {

        //将获取到的数据返回到数据库中

        List<VoaDetailBean.VoatextDTO> list = voaDetailBean.getVoatext();

        //将列表的英文取出来

//        //数据库操作
//        for (int i = 0; i < list.size(); i++) {
//            ContentValues contentValues = new ContentValues();
//
//            contentValues.put("voaid", voaid);
//            contentValues.put("paraid", list.get(i).getParaId());
//            contentValues.put("indexid", list.get(i).getIdIndex());
//            contentValues.put("pageEn", list.get(i).getSentence());
//            contentValues.put("pageCn", list.get(i).getSentenceCn());
//
//            sqLiteDatabase.insert("OriginalTable", null, contentValues);
//        }


        Constant.voatextDTOList = list;



        voaPageAdapter = new VoaPageAdapter(requireContext(), list);

        binding.homeRv.setAdapter(voaPageAdapter);

        binding.pagePlay.setImageResource(R.drawable.bofang);

        //要延时的程序
        mediaPlayer.start();
        //执行进度条语句的关键
        handler.sendEmptyMessage(1);
        isplay = false;

        voaPageAdapter.setCallBack(new VoaPageAdapter.CallBack() {
            @Override
            public void clickWord(String s) {
                //触发取词
                getWordsPresenter.getWordsMes(s, "json");
            }
        });


    }

    public void changePage() {

        binding.pagePlay.setImageResource(R.drawable.zanting);
        mediaPlayer.pause();
        isplay = true;

    }

    @Override
    public void getMyCollect(MyCollectBean myCollectBean) {


        for (int i = 0; i < myCollectBean.getData().size(); i++) {
            if (myCollectBean.getData().get(i).getVoaid().equals(Constant.voaid + "")) {
                originalCollectText.setText("已收藏");
                originalCollectImage.setImageResource(R.drawable.collect1);
                isCollect = true;

            }
        }

    }

    @Override
    public void getCollect(CollectBean collectBean) {
//        if(collectBean.getResult()==1){
//            if(isCollect){
//                Toast.makeText(requireActivity(), "取消收藏成功", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(requireActivity(), "收藏成功", Toast.LENGTH_SHORT).show();
//            }
//        }

    }

    @Override
    public void getPdf(PdfBean pdfBean) {

        if (pdfBean.getCode() == 200) {

            String path = pdfBean.getPath();
            AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                    .setTitle("获取Pdf成功")
                    .setMessage(path)
                    .setPositiveButton("下载", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                            Uri uri = Uri.parse(pdfBean.getPath());
                            Intent intent = new Intent();
                            intent.setAction("android.intent.action.VIEW");
                            intent.setData(uri);
                            startActivity(intent);


                        }
                    })

                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .create();
            alertDialog2.show();
        }

    }


    @Override
    public void getWordsMes(GetWordsBean getWordsBean) {

        if (getWordsBean.getResult() == 1) {
            //取词成功，生成屏蔽层
            View popView = getLayoutInflater().inflate(R.layout.pop_words, null);
            popupWindowWord = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            popupWindowWord.showAtLocation(binding.oriLl, Gravity.BOTTOM, 0, 0);
            WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
            lp.alpha = 0.5f;
            requireActivity().getWindow().setAttributes(lp);

            //获取屏蔽层控件
            wordAudio = popView.findViewById(R.id.word_audio);
            wordDetail = popView.findViewById(R.id.word_detail);
            wordPron = popView.findViewById(R.id.word_pron);
            wordMeaning = popView.findViewById(R.id.word_meaning);
            wordCancel = popView.findViewById(R.id.word_cancel);
            wordBook = popView.findViewById(R.id.word_book);

            wordDetail.setText(getWordsBean.getKey());
            if (!getWordsBean.getPron().equals("")) {
                wordPron.setText("[" + getWordsBean.getPron() + "]");
            }

            wordMeaning.setText(getWordsBean.getDef());
            //单词播放
            wordAudio.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    wordMediaPlayer = MediaPlayer.create(requireActivity(), Uri.parse(getWordsBean.getAudio()));
                    wordMediaPlayer.start();
                }
            });
            //取消
            wordCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindowWord.dismiss();
                    WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
                    lp.alpha = 1.0f;
                    requireActivity().getWindow().setAttributes(lp);
                }
            });
            //加入生词本
            wordBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Constant.useruid == 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可使用此功能")
                                .setMessage("是否去登录")
                                .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(requireActivity(), LoginActivity.class);

                                        startActivity(intent);
                                    }
                                })

                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .create();
                        alertDialog2.show();
                    } else {
                        joinWordBookPresenter.joinWordBook("Iyuba", Constant.useruid, "insert", getWordsBean.getKey(), "json");
                    }

                }
            });


        }

    }

    @Override
    public void joinWordBook(JoinWordBookBean joinWordBookBean) {
        if (joinWordBookBean.getResult() == 1) {
            Toast.makeText(requireActivity(), "加入成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireActivity(), "加入失败", Toast.LENGTH_SHORT).show();
        }
    }


    //判断是否存在网络
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

    public void upStuduyRecord() {

        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        EndTime = formatter1.format(date);

        String sign = MD5.md5(Constant.useruid + BeginTime + EndTime);


        upStudyRecordPresenter.UpStudyRecord("json", Constant.useruid + "", 201, BeginTime, EndTime, "voa", Constant.voaid + "", "0", "ios", "ios", "127.0.0.1", sign, "1", Constant.SEN_NUM + "", Constant.WORD_NUM + "", 1);


    }

    @Override
    public void UpStudyRecord(StudyRecordByTestModeBean studyRecordByTestModeBean) {

        Log.d("fang777","发钱");

//        if (Integer.parseInt(studyRecordByTestModeBean.getReward()) > 0) {
//            android.app.AlertDialog alertDialog2 = new android.app.AlertDialog.Builder(requireContext())
//                    .setTitle("提示消息")
//                    .setMessage("本次学习获得" + (Double.parseDouble(studyRecordByTestModeBean.getReward()) / 100) + "元红包奖励,已自动存入您的钱包账户。" + "\n" + "红包可在[爱语吧]微信公众号提现。继续学习领取更多奖励吧!")
//                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
//                        }
//                    })
//                    .create();
//            alertDialog2.show();
//        }


    }

    @Override
    public void onReceived(View view) {

        if (isADshow) {
            co_fl_ad.removeAllViews();
            co_fl_ad.addView(view);
            co_fl_ad.setVisibility(View.VISIBLE);
        }
        Log.d("banner111", "onReceived");
    }

    @Override
    public void onAdExposure() {

    }

    @Override
    public void onAdDisplay() {

    }

    @Override
    public void onAdClose() {

    }

    @Override
    public void onAdClick(String s) {

    }

    @Override
    public void onClosed() {
        co_fl_ad.setVisibility(View.GONE);

        isADshow = false;
        Log.d("banner111", "onClosed");

    }

    @Override
    public void onAdFailed(YdError ydError) {

        Log.d("fang887744", "onAdFailed: "+ydError.getMsg());

        if (isAdFirst) {

            isAdFirst = false;

            if (ad_title.equals(Constant.AD_ADS1)) {
                adkey = "0136";
            } else if (ad_title.equals(Constant.AD_ADS2)) {
                //穿山甲
                adkey = "0136";
            } else if (ad_title.equals(Constant.AD_ADS3)) {
                adkey = "0136";
            } else if (ad_title.equals(Constant.AD_ADS4)) {
                //优良回
                adkey = "0244";
            } else if (ad_title.equals(Constant.AD_ADS5)) {
                adkey = "0244";
            } else if (ad_title.equals(Constant.AD_ADS6)) {
                //瑞狮
                adkey = "0244";
            }


            Log.d("fang014", "banner: " + adkey);
            if (isAdded()) {

                if (activity != null) {
                    DisplayMetrics displayMetrics = requireActivity().getResources().getDisplayMetrics();
                    int width = displayMetrics.widthPixels;
                    int height = DensityUtil.dp2px(requireActivity(), 65);

                    YdBanner mBanner = new YdBanner.Builder(requireActivity())
                            .setKey(adkey)
                            .setWidth(width)
                            .setHeight(height)
                            .setMaxTimeoutSeconds(5)
                            .setBannerListener(this)
                            .build();

                    mBanner.requestBanner();

                }
            }
        }
    }

    @Override
    public void getAdEntryAll(List<AdEntryBean> adEntryBean) {



        AdEntryBean.DataDTO dataDTO = adEntryBean.get(0).getData();
        String type = dataDTO.getType();
        if (!dataDTO.getTitle().equals("")){
            ad_title = dataDTO.getTitle();
        }


        if (type.equals(Constant.AD_ADS1) || type.equals(Constant.AD_ADS2) || type.equals(Constant.AD_ADS3)
                || type.equals(Constant.AD_ADS4) || type.equals(Constant.AD_ADS5) || type.equals(Constant.AD_ADS6)) {


            if (type.equals(Constant.AD_ADS1)) {
                adkey = "0136";
            } else if (type.equals(Constant.AD_ADS2)) {
                //穿山甲
                adkey = "0136";
            } else if (type.equals(Constant.AD_ADS3)) {
                adkey = "0136";
            } else if (type.equals(Constant.AD_ADS4)) {
                //优良回
                adkey = "0244";
            } else if (type.equals(Constant.AD_ADS5)) {
                adkey = "0244";
            } else if (type.equals(Constant.AD_ADS6)) {
                //瑞狮
                adkey = "0244";
            }


            Log.d("fang014", "banner: "+adkey);

            DisplayMetrics displayMetrics = requireActivity().getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = DensityUtil.dp2px(requireActivity(), 65);

            YdBanner mBanner = new YdBanner.Builder(requireActivity())
                    .setKey(adkey)
                    .setWidth(width)
                    .setHeight(height)
                    .setMaxTimeoutSeconds(5)
                    .setBannerListener(this)
                    .build();

            mBanner.requestBanner();

        }

    }

    @Override
    public void getDetailPage(DetailPageBean detailPageBean) {

        Constant.title = detailPageBean.getData().get(0).getTitle();


//        VoaDetailActivity voaDetailActivity = (VoaDetailActivity) getActivity();
//        voaDetailActivity.TitleChange();

        //切换标题
        ((VoaDetailActivity) Objects.requireNonNull(getActivity())).TitleChange();

    }

    @Override
    public void getVoaLike(VoaBean voaBean) {

    }





}