package com.iyuba.voaEnglish.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.VoaDetailAdapter;
import com.iyuba.voaEnglish.databinding.FragmentEvaluationBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.ComposeBean;
import com.iyuba.voaEnglish.model.bean.EvaBean;
import com.iyuba.voaEnglish.model.bean.EvaHistoryBean;
import com.iyuba.voaEnglish.model.bean.UploadBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.presenter.home.EvaHistoryPresenter;
import com.iyuba.voaEnglish.presenter.home.EvaPresenter;
import com.iyuba.voaEnglish.presenter.home.HomePresenter;
import com.iyuba.voaEnglish.view.home.EvaContract;
import com.iyuba.voaEnglish.view.home.EvaHistoryContract;
import com.iyuba.voaEnglish.view.home.HomeContract;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EvaluationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EvaluationFragment extends Fragment implements HomeContract.HomeView, EvaContract.EvaView, EvaHistoryContract.EvaHistoryView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private MediaPlayer mediaPlayer;

    private MediaPlayer mediaPlayerCompose;

    public boolean globalPlay = false;

    public int pos = -1;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EvaluationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EvaluationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EvaluationFragment newInstance(String param1, String param2) {
        EvaluationFragment fragment = new EvaluationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    private FragmentEvaluationBinding binding;

    private LinearLayoutManager layoutManager;

    HomePresenter homePresenter;

    private EvaHistoryPresenter evaHistoryPresenter;

    private int voaid;

    private String sound;


    VoaDetailAdapter voaDetailAdapter;

    private MediaRecorder mRecorder;

    File mFileName = null;

    EvaPresenter evaPresenter;

    int score = 0;
    List<EvaBean.DataDTO.WordsDTO> list_word = null;
    String audioPath = null;



    private int RESULT_CODE_STARTAUDIO;

    MediaPlayer mediaPlayerEva;

    private PopupWindow popupWindow;

    private String composeAudios = null;

    private int composeSum = 0;

    private int EvaCom = 0;

    private boolean ifCompose = false;

    private boolean ifUpList = false;

    private boolean isplay = true;
    //定时器


    private boolean isprepar = false;

    private boolean ispreparEva = false;


    private int composeScore = 0;


    private DecimalFormat decimalFormatCom;

    private HandlerThread handlerThreadCom;

    private Handler handlerCom;

    private int[] size = new int[100];

    private String[] audioUrl = new String[100];

    private int[] composescore = new int[100];

    private int evaHistorySum = 0;

    private double evaHisScore = 0;

    private String evaHisAudio = null;


    //进度条

    public void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();

        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

            mediaPlayer.pause();
        }


        try {
            FileInputStream fis = new FileInputStream(audioPath);
            FileChannel channel = fis.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
            }
            byte[] dataByte = byteBuffer.array();
            channel.close(); //这里需要关闭channel，否则就会打印A resource failed to call close
            //return dataByte;
        } catch (Throwable t) {
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Constant.audioURL = null;

        //网络请求，单例模式

        Network.getInstance().init();
        homePresenter = new HomePresenter();
        homePresenter.attchView(this);

        evaPresenter = new EvaPresenter();
        evaPresenter.attchView(this);

        evaHistoryPresenter = new EvaHistoryPresenter();
        evaHistoryPresenter.attchView(this);

        decimalFormatCom = new DecimalFormat("#00");
        startHandlerCom();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = requireActivity().getIntent().getExtras();

        voaid = 16840;

        sound = "/202303/16840.mp3";

        if (bundle != null) {

            voaid = bundle.getInt("voaid");

            sound = bundle.getString("sound");
        }

        binding = FragmentEvaluationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

//        homePresenter.getVoaDetail("json", Constant.voaid);
        //进入首先执行评测历史功能

        mediaPlayerCompose = new MediaPlayer();
        //合成和上传功能
        binding.compose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EvaCom < 2) {
                    if (evaHistorySum < 2) {
                        Toast.makeText(requireActivity(), "合成需要至少评测两句", Toast.LENGTH_SHORT).show();
                    } else {
                        //历史评测
                        evaPresenter.composeUp(evaHisAudio, "voa");
                        ifCompose = true;
                        Toast.makeText(requireActivity(), "合成成功", Toast.LENGTH_SHORT).show();
                        binding.composeBar.setVisibility(View.VISIBLE);
                        binding.compose.setVisibility(View.GONE);
                        binding.pagePlay.setVisibility(View.VISIBLE);
                        binding.tryListen.setText((Math.round(evaHisScore / composeSum)) + "分");
                        binding.tryListen.setVisibility(View.VISIBLE);
                    }

                } else {
                    String audio = null;
                    int xscore = 0;
                    for (int i = 0; i < audioUrl.length; i++) {
                        if (audioUrl[i] != null) {
                            if (audio == null) {
                                audio = audioUrl[i];
                            } else {
                                audio = audio + "," + audioUrl[i];
                            }
                        }
                        xscore = xscore + composescore[i];
                    }


                    evaPresenter.composeUp(audio, "voa");
                    ifCompose = true;
                    Toast.makeText(requireActivity(), "合成成功", Toast.LENGTH_SHORT).show();
                    binding.composeBar.setVisibility(View.VISIBLE);
                    binding.compose.setVisibility(View.GONE);
                    binding.pagePlay.setVisibility(View.VISIBLE);
                    binding.tryListen.setText((xscore / EvaCom) + "分");
                    binding.tryListen.setVisibility(View.VISIBLE);
                }
            }
        });




        layoutManager = new LinearLayoutManager(requireContext());

        binding.homeRv.setLayoutManager(layoutManager);


        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                isprepar = true;
            }
        });

        try {
            mediaPlayer.setDataSource(Constant.audioSound);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        mediaPlayerEva = new MediaPlayer();


        //进度条
        binding.originalSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                if (mediaPlayerCompose != null) {

                    mediaPlayerCompose.seekTo(seekBar.getProgress());
                }
            }
        });


        return view;
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
    public void getEvaHistory(EvaHistoryBean evaHistoryBean) {

        evaHisAudio = evaHistoryBean.getData().get(0).getUrl();

        evaHisScore = Double.parseDouble(evaHistoryBean.getData().get(0).getScore()) *20;

        for (int i = 0; i < evaHistoryBean.getData().size(); i++) {
            evaHisAudio = evaHisAudio + "," + evaHistoryBean.getData().get(i).getUrl();
            evaHisScore = evaHisScore + Double.parseDouble(evaHistoryBean.getData().get(0).getScore())*20;
        }


        //获得评测历史，传入adapter
        voaDetailAdapter.setEvaHistoryList(evaHistoryBean.getData());

        evaHistorySum = evaHistoryBean.getSize();

        composeSum = evaHistoryBean.getSize();

        voaDetailAdapter.notifyDataSetChanged();


    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {


            if (voaDetailAdapter == null) {

                return;
            }
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {

                mediaPlayer.pause();
            }
            voaDetailAdapter.setIsplay(false);
            voaDetailAdapter.notifyDataSetChanged();
        }
    };

    @Override
    public void getVoaDetail(VoaDetailBean voaDetailBean) {

        if (Constant.useruid != 0) {
            evaHistoryPresenter.getEvaHistory(Constant.useruid, "voa", Constant.voaid);
        }

        List<VoaDetailBean.VoatextDTO> list = voaDetailBean.getVoatext();


        //初始化Constant.spannableString
        Constant.spannableString = new SpannableString[list.size()];
        Constant.evaScore = new int[list.size()];
        Constant.audioURL = new String[list.size()];
        size = new int[list.size()];
        audioUrl = new String[list.size()];
        composescore = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Constant.evaScore[i] = 0;
            size[i] = -1;
        }
        voaDetailAdapter = new VoaDetailAdapter(requireContext(), list);

        binding.homeRv.setAdapter(voaDetailAdapter);
        binding.homeRv.setItemAnimator(null);

        voaDetailAdapter.notifyDataSetChanged();

        voaDetailAdapter.setCallBack(new VoaDetailAdapter.CallBack() {

            public void clickText(int position) throws IOException {
                if (voaDetailAdapter.isEva() == true) {
                    mRecorder.stop();
                    voaDetailAdapter.setEva(false);
                }
                if (voaDetailAdapter.isEvaPlay() == true) {
                    clickListen(position);
                }
                if (voaDetailAdapter.isIsplay() == true) {
                    clickPlay(position);
                }

                voaDetailAdapter.setHide(true);
                voaDetailAdapter.setShow(false);
                voaDetailAdapter.setPosition(position);
                voaDetailAdapter.notifyDataSetChanged();
            }


            @Override
            public void clickPlay(int position) throws IOException {

                if (voaDetailAdapter.isEva() == true) {
                    clickEva(position);
                }
                if (voaDetailAdapter.isEvaPlay() == true) {
                    clickListen(position);
                }

                //获取点击的数组返回值
                VoaDetailBean.VoatextDTO voa = voaDetailAdapter.getItem(position);
                //Handler用法


                Handler handler = new Handler();
                if (isprepar) {

                    if (voaDetailAdapter != null && !voaDetailAdapter.isIsplay()) {
                        //定时器，写入播放暂停时间
                        double time = voa.getEndTiming() - voa.getTiming();

                        mediaPlayer.seekTo((int) voa.getTiming() * 1000);
                        mediaPlayer.start();
                        voaDetailAdapter.setIsplay(true);
                        voaDetailAdapter.setPosition(position);
                        voaDetailAdapter.notifyDataSetChanged();
                        handler.postDelayed(runnable, (long) time * 1000 + 2000);

                    } else {
                        if (runnable != null) {
                            handler.removeCallbacks(runnable);
                        }
                        mediaPlayer.pause();
                        handler.post(runnable);
                    }

                }


            }

            @Override
            public void clickEva(int position) throws IOException {

                if (voaDetailAdapter.isIsplay() == true) {
                    clickPlay(position);
                }
                if (voaDetailAdapter.isEvaPlay() == true) {
                    clickListen(position);
                }

                if (PackageManager.PERMISSION_GRANTED == ContextCompat.
                        checkSelfPermission(requireContext(), android.Manifest.permission.RECORD_AUDIO)) {

                    if (Constant.useruid == 0) {
                        AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                .setTitle("登录后才可评测")
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


                        VoaDetailBean.VoatextDTO voa = voaDetailAdapter.getItem(position);


                        if (!voaDetailAdapter.isEva()) {
                            //判断是否满足评测条件

                            if (Constant.vipStatus > 0 || composeSum < 3 || Constant.spannableString[position] != null || evaHistorySum < 3) {


                                voaDetailAdapter.setEva(true);

                                voaDetailAdapter.setPosition(position);

                                voaDetailAdapter.notifyDataSetChanged();

                                mRecorder = new MediaRecorder();
                                //设置录入音频源

                                mFileName = new File(requireActivity().getExternalCacheDir().getAbsolutePath() + "/audio_test.amr");

                                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                                //设置输出格式
                                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);
                                //设置输出文件
                                mRecorder.setOutputFile(mFileName.getAbsolutePath());
                                //设置音频编码
                                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
                                //准备编码
                                try {
                                    mRecorder.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                //开始录制
                                mRecorder.start();
                                mRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                                    @Override
                                    public void onError(MediaRecorder mr, int what, int extra) {
                                        //mRecorder.reset();
                                        //Log.d("chen", "zzy:  录制出错");
                                    }
                                });

                            } else {
                                AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                                        .setTitle("非会员只能评测三句")
                                        .setMessage("是否去开通会员")
                                        .setPositiveButton("去开通会员", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(requireActivity(), VIPActivity.class);

                                                startActivity(intent);
                                            }
                                        })

                                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(requireActivity(), "非会员最多评测三句", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .create();
                                alertDialog2.show();
                                //跳转到开通会员界面

                            }


                        } else {
                            voaDetailAdapter.setEva(false);
                            voaDetailAdapter.setPosition(position);
                            voaDetailAdapter.notifyDataSetChanged();

                            pos = voaDetailAdapter.getPosition();
                            //Post请求

                            mRecorder.stop();
                            MediaType type = MediaType.parse("application/octet-stream");
                            RequestBody fileBody = RequestBody.create(type, mFileName);


                            RequestBody requestBody = new MultipartBody.Builder()
                                    .setType(MultipartBody.FORM)
                                    .addFormDataPart("sentence", voa.getSentence())
                                    .addFormDataPart("paraId", voa.getParaId())
                                    .addFormDataPart("newsId", voaid + "")
                                    .addFormDataPart("IdIndex", voa.getIdIndex())
                                    .addFormDataPart("type", "voa")
                                    .addFormDataPart("appId", "428")
                                    .addFormDataPart("wordId", "0")
                                    .addFormDataPart("flg", "1")
                                    .addFormDataPart("userId", Constant.useruid + "")
                                    .addFormDataPart("file", mFileName.getName(), fileBody)
                                    .build();

                            evaPresenter.getEvaData(requestBody);


                            //评测遮蔽层
                            View popupView = getLayoutInflater().inflate(R.layout.pop, null);
                            popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
                            popupWindow.showAsDropDown(popupView, popupView.getWidth(), popupView.getHeight()); //相对某个控件，有偏移

                            WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
                            lp.alpha = 0.5f;
                            requireActivity().getWindow().setAttributes(lp);


                        }

                    }


                } else {
                    //提示用户开户权限音频

                    android.app.AlertDialog alertDialog2 = new android.app.AlertDialog.Builder(requireContext())
                            .setTitle("系统信息")
                            .setMessage("录音权限将用于上传用户录音,语音评测打分,是否同意开启权限?")
                            .setPositiveButton("同意", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                    String[] perms = {"android.permission.RECORD_AUDIO"};
                                    ActivityCompat.requestPermissions(getActivity(), perms, RESULT_CODE_STARTAUDIO);

                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(requireActivity(), "权限未开启,可自行前往权限设置中打开", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create();
                    alertDialog2.show();

                }


            }

            @Override
            public void clickListen(int position) throws IOException {

                if (voaDetailAdapter.isIsplay() == true) {
                    clickPlay(position);
                }
                if (voaDetailAdapter.isEva() == true) {
                    clickEva(position);
                }

                if (!voaDetailAdapter.isEvaPlay()) {
                    voaDetailAdapter.setEvaPlay(true);
                    voaDetailAdapter.setPosition(position);
                    voaDetailAdapter.notifyDataSetChanged();

                    if (Constant.audioURL[position] != null) {

                        //mediaPlayerEva = MediaPlayer.create(requireActivity(), Uri.parse("http://userspeech.iyuba.cn/voa/" + audioPath));

                        mediaPlayerEva = MediaPlayer.create(requireActivity(), Uri.parse("http://userspeech.iyuba.cn/voa/" + Constant.audioURL[position]));

                        mediaPlayerEva.start();


                        mediaPlayerEva.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mediaPlayer) {
                                voaDetailAdapter.setEvaPlay(false);
                                voaDetailAdapter.setPosition(position);
                                voaDetailAdapter.notifyDataSetChanged();
                            }
                        });

                    }
                } else {
                    voaDetailAdapter.setEvaPlay(false);
                    voaDetailAdapter.setPosition(position);
                    voaDetailAdapter.notifyDataSetChanged();

                    if (audioPath != null) {
                        mediaPlayerEva.pause();
                    }

                }


            }

            @Override
            public void clickUpload(int position) {

                VoaDetailBean.VoatextDTO voa = voaDetailAdapter.getItem(position);

                //点击发送网络请求

                evaPresenter.uploadList("android", "json", 60003, "voa", Constant.useruid, Constant.username, voaid, voa.getIdIndex(), voa.getParaId(), Constant.evaScore[position], 2, audioPath,1,201);
            }

        });


    }

    @Override
    public void getEvaData(EvaBean evaBean) throws IOException {


        if (composeAudios == null) {
            audioUrl[voaDetailAdapter.getPosition()] = evaBean.getData().getUrl();
            composescore[voaDetailAdapter.getPosition()] = (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);
            if (size[voaDetailAdapter.getPosition()] == -1) {
                composeAudios = evaBean.getData().getUrl();
                composeSum++;
                EvaCom++;
                evaHistorySum++;
                size[voaDetailAdapter.getPosition()] = voaDetailAdapter.getPosition();
                composeScore = composeScore + (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);
                Log.d("fang778877", "getEvaData: "+composeScore);
            }
        } else {
            audioUrl[voaDetailAdapter.getPosition()] = evaBean.getData().getUrl();
            composescore[voaDetailAdapter.getPosition()] = (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);
            if (size[voaDetailAdapter.getPosition()] == -1) {
                composeAudios = composeAudios + "," + evaBean.getData().getUrl();
                composeSum++;
                EvaCom++;
                evaHistorySum++;
                composeScore = composeScore + (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);
                Log.d("fang778877", "getPosition: "+composeScore);
                size[voaDetailAdapter.getPosition()] = voaDetailAdapter.getPosition();
            }

        }


        List<EvaBean.DataDTO.WordsDTO> list = evaBean.getData().getWords();

        String url = evaBean.getData().getUrl();

        voaDetailAdapter.setAudioUrl(url);

        audioPath = evaBean.getData().getUrl();

        score = (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);

        Constant.scores = (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);

        list_word = evaBean.getData().getWords();

        String result = evaBean.getResult();


        if (result.equals("1")) {

            Toast.makeText(requireContext(), "评测成功", Toast.LENGTH_SHORT).show();

            Constant.audioURL[voaDetailAdapter.getPosition()] = evaBean.getData().getUrl();
            Constant.evaScore[voaDetailAdapter.getPosition()] = (int) (Double.parseDouble(evaBean.getData().getTotalScore()) * 20);

            //把背景还原
            popupWindow.dismiss();
            WindowManager.LayoutParams lp = requireActivity().getWindow().getAttributes();
            lp.alpha = 1.0f;
            requireActivity().getWindow().setAttributes(lp);
            binding.pagePlay.setVisibility(View.GONE);
            binding.compose.setVisibility(View.VISIBLE);

            voaDetailAdapter.setEvaList(list);
            voaDetailAdapter.setShow(true);

            voaDetailAdapter.setPosition(voaDetailAdapter.getPosition());

            voaDetailAdapter.notifyDataSetChanged();

        } else {
            Toast.makeText(requireContext(), "评测失败", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void uploadList(UploadBean uploadBean) {

        String result = uploadBean.getResultCode();

        if (result.equals("501")) {
            Toast.makeText(requireContext(), "上传成功", Toast.LENGTH_SHORT).show();
            if (Integer.parseInt(uploadBean.getReward()) > 0) {
                android.app.AlertDialog alertDialog2 = new android.app.AlertDialog.Builder(requireContext())
                        .setTitle("提示消息")
                        .setMessage("本次学习获得" + (Double.parseDouble(uploadBean.getReward()) / 100) + "元红包奖励,已自动存入您的钱包账户。" + "\n" + "红包可在[爱语吧]微信公众号提现。继续学习领取更多奖励吧!")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .create();
                alertDialog2.show();
            }
        } else {
            Toast.makeText(requireContext(), "上传失败,请重新评测后重试", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void composeUp(ComposeBean composeBean) {

        mediaPlayerCompose = MediaPlayer.create(requireActivity(), Uri.parse("http://userspeech.iyuba.cn/voa/" + composeBean.getUrl()));
        binding.upList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifCompose) {
                    int xscore = 0;
                    for (int i = 0; i < composescore.length; i++) {
                        xscore = xscore + composescore[i];
                    }
                    int scoresCom = (xscore / composeSum);
                    evaPresenter.uploadList("android", "json", 60003, "voa", Constant.useruid, Constant.username, Constant.voaid, "0", "0", scoresCom, 4, composeBean.getUrl(),1,201);
                    ifUpList = true;
                }
            }
        });


        binding.pagePlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ifCompose) {
                    //原文音乐播放

                    if (isplay) {

                        mediaPlayerCompose.start();
                        //执行进度条语句的关键
                        handlerCom.sendEmptyMessage(1);
                        isplay = false;

                        //监听播放完毕

                    } else {
                        //binding.pagePlay.setImageResource(R.drawable.zanting);
                        mediaPlayerCompose.pause();
                        isplay = true;
                    }
                }
            }
        });


    }

    private void startHandlerCom() {

        handlerThreadCom = new HandlerThread("检测播放与recler的位置");
        handlerThreadCom.start();
        handlerCom = new Handler(handlerThreadCom.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {


                if (mediaPlayerCompose != null && mediaPlayerCompose.isPlaying()) {

                    int cp = mediaPlayerCompose.getCurrentPosition();

                    int dur = mediaPlayerCompose.getDuration();
                    setViewText(getTimeStr(cp), getTimeStr(dur), cp, dur);
                }
                handlerCom.sendEmptyMessageDelayed(1, 50);
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
                //判断播放完毕，跳转到开始
                if (cp.equals(dur)) {
                    //binding.pagePlay.setImageResource(R.drawable.zanting);
                    isplay = true;
                    mediaPlayerCompose.seekTo(0);
                    binding.originalSb.setProgress(0);
                    binding.originalTvPlayTime.setText("00:00");
                    mediaPlayerCompose.pause();

                }


            }
        });
    }

    private String getTimeStr(long mill) {

        long s = mill / 1000;

        long ts = s % 60;
        long tm = s / 60;


        String cpStr = decimalFormatCom.format(ts);
        String durStr = decimalFormatCom.format(tm);

        return durStr + ":" + cpStr;
    }

    public void onResume() {

        super.onResume();
        //evaHistoryPresenter.getEvaHistory(Constant.useruid, "voa", Constant.voaid);
        //Log.d("chen","xxzx");
        //homePresenter.getVoaDetail("json", Constant.voaid);
    }

    public void geteva(){

        homePresenter.getVoaDetail("json", Constant.voaid);
    }

    //评测历史记录

}