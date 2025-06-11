package com.iyuba.voaEnglish.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.ReadAdpter;
import com.iyuba.voaEnglish.databinding.FragmentPageBinding;
import com.iyuba.voaEnglish.databinding.FragmentReadBinding;
import com.iyuba.voaEnglish.model.bean.ReadBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.presenter.home.HomePresenter;
import com.iyuba.voaEnglish.presenter.home.ReadPresenter;
import com.iyuba.voaEnglish.view.home.HomeContract;
import com.iyuba.voaEnglish.view.home.ReadContract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**原文阅读
 */
public class ReadFragment extends Fragment implements ReadContract.ReadView , HomeContract.HomeView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean check = true;  //完成阅读提交时判断是否是第一次,只有第一次有钱
    private boolean isFirst = true;
    private FragmentReadBinding binding;

    private HomePresenter homePresenter;

    private ReadPresenter readPresenter;

    private ReadAdpter readAdpter;

    private int wordCount = 0;

    private String beginTime, endTime;

    private Date date1,date2;

    private RecyclerView readRv;

    public ReadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReadFragment newInstance(String param1, String param2) {
        ReadFragment fragment = new ReadFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        homePresenter =new HomePresenter();
        homePresenter.attchView(this);

        readPresenter =new ReadPresenter();
        readPresenter.attchView(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentReadBinding.inflate(inflater, container, false);
        View view = binding.getRoot();




       LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());

        binding.readRv.setLayoutManager(layoutManager);




//        SimpleDateFormat formatter9 = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date(System.currentTimeMillis());
//        String time = formatter9.format(date);
//
//
//        String sign = MD5.md5(Constant.uid+"102iyuba"+time);
//
//        isIncreaseMoneyPresenter.isIncreaseMoney(102,Constant.uid,222,Constant.voaId,sign);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date1 = new Date(System.currentTimeMillis());
        beginTime = formatter.format(date1);

        binding.completeRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date2 = new Date(System.currentTimeMillis());
                endTime = formatter1.format(date2);

                if (Constant.useruid == 0) {
                    AlertDialog alertDialog2 = new AlertDialog.Builder(requireContext())
                            .setTitle("您还未登录")
                            .setMessage("是否去登录")
                            .setPositiveButton("去登录", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                    Intent intent =new Intent(requireContext(),LoginActivity.class);
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


                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date2 = new Date(System.currentTimeMillis());
                    endTime = formatter.format(date2);
                    //判断登录后，判断阅读时间后，根据阅读速度判断是否执行上传
                    long time = date2.getTime() - date1.getTime();
                    double min = Double.parseDouble(time + "") / (1000 * 60);
                    long _min = time / 1000 / 60;
                    long _s = time / 1000 - _min * 60;

                    if (wordCount / min >= 600) {

                        Toast.makeText(requireActivity(), "阅读速度过快,请用正常速度阅读", Toast.LENGTH_SHORT).show();

                    } else {
                        if (isFirst) {
                            int x = (int) (wordCount / min);
                            android.app.AlertDialog alertDialog3 = new android.app.AlertDialog.Builder(requireContext())
                                    .setTitle("阅读报告")
                                    .setMessage("当前阅读统计:" + "\n" + "文章单词数:" + wordCount + "\n" + "阅读时长:" + _min + "分" + _s + "秒" + "\n" + "阅读速度:" + x + "单词/分钟" + "\n" + "是否确认提交此阅读记录?")
                                    .setPositiveButton("提交", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            Log.d("fang777",Constant.uid+"*"+startTime+"*"+endTime+"*"+Constant.orivoaid+"*"+wordCount+"单词");
                                            readPresenter.getRead("json", String.valueOf(Constant.useruid), beginTime, endTime, "VOA英语特刊", "voa", Constant.voaid + "", 201, "android", "02:00:00:00:00:00", 1, wordCount, 0, "web", 1);

                                        }
                                    })

                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加取消
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                                Toast.makeText(requireActivity(), "您还未登录", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .create();
                            alertDialog3.show();

                            isFirst = false;
                        }


                    }
                }
        }
        });

        binding.setEnCn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {

                if (readAdpter!=null){
                    if (readAdpter.isChangeEn()) {
                        readAdpter.setChangeEn(false);  //点击显示中文
                        readAdpter.notifyDataSetChanged();  //刷新每个item里的内容

                    } else {
                        readAdpter.setChangeEn(true);  //不显示
                        readAdpter.notifyDataSetChanged();
                    }
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
    public void getRead(ReadBean readBean) {
        if (readBean.getResult().equals("1")) {
            if (check) {
                if (Integer.parseInt(readBean.getReward()) > 0) {
                    android.app.AlertDialog alertDialog2 = new android.app.AlertDialog.Builder(requireContext())
                            .setTitle("提示消息")
                            .setMessage("本次学习获得" + (Double.parseDouble(readBean.getReward()) / 100) + "元红包奖励,已自动存入您的钱包账户。" + "\n" + "红包可在[爱语吧]微信公众号提现。继续学习领取更多奖励吧!")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {//添加"Yes"按钮
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Toast.makeText(requireActivity(), "这是确定按钮", Toast.LENGTH_SHORT).show();

                                }
                            })
                            .create();
                    alertDialog2.show();
                }
                check = false;
            }

        }
    }

    @Override
    public void getVoaTitle(VoaBean voaBean, int pages) {

    }

    @Override
    public void getVoaDetail(VoaDetailBean voaDetailBean) {

        List<VoaDetailBean.VoatextDTO> list = voaDetailBean.getVoatext();

        for (int i = 0; i < list.size(); i++) {
            wordCount = wordCount + splitWord(list.get(i).getSentence()).size();
        }

        int m = 1;

        List<String> listCn = new ArrayList<>();

        List<String> listEn = new ArrayList<>();

        String en = null, cn = null;

        for (int i = 0; i < list.size(); i++) {
            if (Integer.parseInt(list.get(i).getParaId()) == m) {
//                Log.d("fang888",list.get(i).getParaId()+"**"+list.get(i).getSentence());
                if (en == null) {

                    en = list.get(i).getSentence() + " ";

                    cn = list.get(i).getSentenceCn();
                } else {
                    en = en + list.get(i).getSentence() + " ";

                    cn = cn + list.get(i).getSentenceCn();
                }
            } else {
                m = m + 1;
                listEn.add(en);
                listCn.add(cn);
                en = null;
                cn = null;
                i--;

            }
//            if ((list.get(list.size()-1).getParaId().equals(m+""))){
//                listEn.add(list.get(list.size(i)).getSentence());
//                listCn.add(list.get(list.size(i)).getSentenceCn());
//            }
        }
        listEn.add(en);
        listCn.add(cn);


//        for (int i = 0; i < listEn.size(); i++) {
//            if (listEn.get(i) == null) {
//                listEn.remove(i);
//                listCn.remove(i);
//            }
//        }
        readAdpter = new ReadAdpter(listEn, listCn, requireActivity());
        readRv = getActivity().findViewById(R.id.read_rv);

        // 定义一个线性布局管理器    注意这个
        LinearLayoutManager manager = new LinearLayoutManager(requireActivity());
        // 设置布局管理器
        readRv.setLayoutManager(manager);

        readRv.setAdapter(readAdpter);
    }

    public List<String> splitWord(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return new ArrayList<>();
        }
        List<String> words = new ArrayList<>();
        Pattern pattern = Pattern.compile("[a-zA-Z-']+");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            words.add(matcher.group(0));
        }
        return words;
    }

    public void getRead(){

        homePresenter.getVoaDetail("json", Constant.voaid);
    }
}