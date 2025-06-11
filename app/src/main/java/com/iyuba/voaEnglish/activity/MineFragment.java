package com.iyuba.voaEnglish.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.util.Log;
import com.iyuba.module.user.IyuUserManager;
import com.iyuba.module.user.User;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.databinding.FragmentMineBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.LearningTimeBean;
import com.iyuba.voaEnglish.model.bean.LogLoginBean;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.presenter.home.LearningTimePresenter;
import com.iyuba.voaEnglish.presenter.home.LogUserPresenter;
import com.iyuba.voaEnglish.presenter.home.UidLoginPresenter;
import com.iyuba.voaEnglish.view.home.LearningTimeContract;
import com.iyuba.voaEnglish.view.home.LogUserContract;
import com.iyuba.voaEnglish.view.home.UidLoginContract;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment implements LogUserContract.LogUserView, LearningTimeContract.LearningTimeView, UidLoginContract.UidLoginView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentMineBinding binding;

    private LogUserPresenter logUserPresenter;

    private LearningTimePresenter learningTimePresenter;

    private UidLoginPresenter uidLoginPresenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Network.getInstance().init();
        logUserPresenter = new LogUserPresenter();
        logUserPresenter.attchView(this);

        learningTimePresenter = new LearningTimePresenter();
        learningTimePresenter.attchView(this);

        uidLoginPresenter = new UidLoginPresenter();
        uidLoginPresenter.attchView(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMineBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        Glide.with(requireContext()).load("http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big").into(binding.userImg);
        binding.userUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), UseActivity.class);

                startActivity(intent);
            }
        });
        binding.userNameMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.useruid == 0) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                }
            }
        });

        binding.userPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(requireActivity(), PrivacyActivity.class);

                startActivity(intent);
            }
        });

        binding.userMoney.setText("钱包:" + Constant.money / 100);
        binding.userAmount.setText("爱语币:" + Constant.amount);


        onResume();

        binding.userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), LoginActivity.class);

                startActivity(intent);
            }
        });

        //关于
        binding.info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), InfoActivity.class);

                startActivity(intent);
            }
        });

        //退出
        binding.editLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.loginSuccess.setVisibility(View.GONE);
                binding.loginLog.setVisibility(View.GONE);
                Constant.useruid = 0;
                Constant.username = null;
                binding.userNameMine.setText("点击登录");
                binding.userPersonVip.setText("开通会员，特权更多");
                Glide.with(requireContext()).load("http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big").into(binding.userImg);
                binding.userMoney.setVisibility(View.INVISIBLE);
                binding.userAmount.setVisibility(View.INVISIBLE);
                binding.userMoney.setText("钱包:0.00");
                binding.userAmount.setText("爱语币:0.00");

                SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                IyuUserManager.getInstance().logout();

            }
        });
        binding.userVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.useruid == 0) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(requireActivity(), VIPActivity.class);
                    requireActivity().startActivity(intent);
                }


            }
        });

        //学习报告
        binding.learningReporter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.useruid == 0) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(requireActivity(), LearningReportActivity.class);
                    requireActivity().startActivity(intent);
                }

            }
        });

        binding.logLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWarning();

            }
        });

        //我的收藏
        binding.myCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.useruid == 0) {
                    Toast.makeText(requireContext(), "请先登录", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(requireActivity(), MyCollectActivity.class);
                    requireActivity().startActivity(intent);
                }
            }
        });

        binding.vipCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.useruid == 0) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(requireActivity(), VIPActivity.class);
                    requireActivity().startActivity(intent);
                }
            }
        });

        binding.integral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.useruid == 0) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(requireContext(), InterActivity.class);
                    startActivity(intent);
                }

            }
        });

        //钱包记录

        binding.userMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Constant.useruid == 0) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(requireContext(), MyMoneyDetailActivity.class);
                    startActivity(intent);
                }
            }
        });

        //我的单词
        binding.myWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.useruid == 0) {
                    Intent intent = new Intent(requireActivity(), LoginActivity.class);
                    requireActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(requireContext(), MyWordActivity.class);
                    startActivity(intent);
                }
            }
        });


        //打卡
        binding.dayClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进行提交学习记录，判断是否大于三分钟
                if (Constant.useruid != 0) {
                    Date sr = new Date();
                    long res1 = sr.getTime();
                    long res = (res1 - 8 * 60 * 1000) / (24 * 60 * 60 * 1000) ;
                    learningTimePresenter.getLearningTime(Constant.useruid,res, 1);
                } else {
                    Toast.makeText(requireContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //我的打卡
        binding.myClock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.useruid != 0) {
                    Intent intent = new Intent(requireActivity(), MyClockActivity.class);
                    requireActivity().startActivity(intent);
                } else {
                    Toast.makeText(requireContext(), "请先登录", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Timer mTimer = new Timer();

        Handler handler = new Handler();

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (Constant.vipStatus > 0 && Constant.useruid != 0) {
                            binding.userPersonVip.setText("到期时间:" + Constant.vipDate);
                        }
                    }
                });


            }
        };
        mTimer.schedule(timerTask, 1000);


        return view;
    }


    //重写onResume方法，返回执行刷新
    public void onResume() {

        super.onResume();

        if (Constant.useruid != 0) {
            binding.userLogin.setVisibility(View.GONE);
            binding.loginSuccess.setVisibility(View.VISIBLE);
            binding.loginLog.setVisibility(View.VISIBLE);
            binding.userNameMine.setText(Constant.username);
            binding.userMoney.setText("钱包:" + Constant.money / 100);
            binding.userAmount.setText("爱语币:" + Constant.amount);
            Glide.with(requireContext()).load("http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big").into(binding.userImg);
            binding.userMoney.setVisibility(View.VISIBLE);
            binding.userAmount.setVisibility(View.VISIBLE);
            String sign = MD5.md5("20001" + Constant.useruid + "iyubaV2");
            uidLoginPresenter.uidLogin("android", "json", 20001, Constant.useruid, Constant.useruid, 201, sign);

        }

        if (Constant.vipStatus > 0 && Constant.useruid != 0) {
            binding.userPersonVip.setText("到期时间:" + Constant.vipDate);
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
    public void logUser(LogLoginBean logLoginBean) {

        if (logLoginBean.getResult().equals("101")) {
            binding.userLogin.setVisibility(View.VISIBLE);
            binding.loginSuccess.setVisibility(View.GONE);
            binding.loginLog.setVisibility(View.GONE);
            Constant.useruid = 0;
            Constant.username = null;
            Constant.vipStatus = 0;
            binding.userNameMine.setText("点击登录");
            binding.userPersonVip.setText("开通会员，特权更多");
            Glide.with(requireContext()).load("http://api.iyuba.com.cn/v2/api.iyuba?protocol=10005&uid=" + Constant.useruid + "&size=big").into(binding.userImg);
            binding.userMoney.setVisibility(View.INVISIBLE);
            binding.userAmount.setVisibility(View.INVISIBLE);

            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("user", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

            Toast.makeText(requireContext(), "注销成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(requireContext(), "密码错误，请重新输入", Toast.LENGTH_SHORT).show();
            PressPwd();
        }

    }

    private void showWarning() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("提示");
        final View diaView = getLayoutInflater().inflate(R.layout.dialog_logout, null);
        builder.setView(diaView);
        builder.setPositiveButton("继续注销", (dialog, which) -> PressPwd());
        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        builder.show();
    }

    private void PressPwd() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("输入密码注销账号");
        final View diaView2 = getLayoutInflater().inflate(R.layout.dialog_logout2, null);
        final EditText editText = diaView2.findViewById(R.id.logout_pwd);
        builder.setView(diaView2);
        builder.setPositiveButton("确认注销", (dialog, which) -> {

            String pas = editText.getText().toString().trim();
            pas = MD5.md5(pas);
            String sign = MD5.md5("11005" + Constant.username + pas + "iyubaV2");

            logUserPresenter.logUser(11005, Constant.username, pas, "json", sign);

            IyuUserManager.getInstance().logout();
        });
        builder.setNegativeButton("取消", (dialog, which) -> {
        });
        builder.show();
    }

    @Override
    public void getLearningTime(LearningTimeBean learningTimeBean) {


        //判断是否大于180s
        if (Integer.parseInt(learningTimeBean.getTotalTime()) >= 180) {


            Intent intent = new Intent(requireActivity(), ClockActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("totalDays",Integer.parseInt(learningTimeBean.getTotalDays()));

            bundle.putInt("ranking",Integer.parseInt(learningTimeBean.getRanking()));

            bundle.putString("shareId",learningTimeBean.getShareId());

            intent.putExtras(bundle);


            requireActivity().startActivity(intent);
            //执行分享，若分享成功，


            //分享失败
        } else {
            Toast.makeText(requireContext(), "学习时长不足3分钟,您总共学习了"+learningTimeBean.getTotalTime(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void uidLogin(UidBean uidBean) {

        Constant.vipStatus = Integer.parseInt(uidBean.getVipStatus());

        Constant.money = uidBean.getMoney();//钱包

        Constant.amount = uidBean.getAmount();//爱语币

        //获取当前时间戳,也可以是你自已给的一个随机的或是别人给你的时间戳(一定是long型的数据)
        long timeStamp = uidBean.getExpireTime() * 1000L;//转化成长整型
        //要转成后的时间的格式
        SimpleDateFormat simpleDateFormat = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        // 时间戳转换成时间
        String vipDate = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            vipDate = simpleDateFormat.format(new Date(timeStamp));
        }

        Constant.vipDate = vipDate;//vip时间

        Constant.email = uidBean.getEmail();

        Constant.mobile = uidBean.getMobile();


        User user = new User();
        user.vipStatus = String.valueOf(Constant.vipStatus);
        if (Constant.vipStatus != 0) {
            user.vipExpireTime = uidBean.getExpireTime();
        }
        user.uid = Constant.useruid;
        user.credit = Integer.parseInt(uidBean.getCredits());
        user.name = Constant.username;
        user.imgUrl = Constant.user_img;
        user.email = Constant.email;
        user.mobile = Constant.mobile;
        user.iyubiAmount = (int) Constant.amount;
        IyuUserManager.getInstance().setCurrentUser(user);

    }
}