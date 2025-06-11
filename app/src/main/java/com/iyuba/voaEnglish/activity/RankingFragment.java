package com.iyuba.voaEnglish.activity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.RankingAdapter;
import com.iyuba.voaEnglish.adapter.VoaPageAdapter;
import com.iyuba.voaEnglish.databinding.FragmentPageBinding;
import com.iyuba.voaEnglish.databinding.FragmentRankingBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.RankingBean;
import com.iyuba.voaEnglish.presenter.home.RankingPresenter;
import com.iyuba.voaEnglish.view.home.RankingContract;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RankingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankingFragment extends Fragment implements RankingContract.RankingView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RankingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RankingFragment newInstance(String param1, String param2) {
        RankingFragment fragment = new RankingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentRankingBinding binding;

    RankingPresenter rankingPresenter;

    LinearLayoutManager layoutManager;

    private int voaid = 16840;

    RankingAdapter rankingAdapter;

    String uid = null;

    String voaId = null;

    String date = null;

    String sign = null;

    private int isFrist = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Network.getInstance().init();
        rankingPresenter = new RankingPresenter();
        rankingPresenter.attchView(this);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = requireActivity().getIntent().getExtras();

        if (bundle != null) {

            voaid = bundle.getInt("voaid");

        }

        onResume();

        binding = FragmentRankingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        uid = Constant.useruid + "";

        voaId = voaid+"";

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(calendar.YEAR);

        int month = calendar.get(calendar.MONTH)+1;

        int day = calendar.get(calendar.DAY_OF_MONTH);

        date = (year+"")+"-"+(month+"")+"-"+(day+"");


        sign = MD5.md5(uid + "voa" +voaId+"0"+"500"+date);

        rankingPresenter.getRanking(Constant.useruid, "D", 500, 0, "voa", Constant.voaid, sign);


        layoutManager = new LinearLayoutManager(requireContext());


        binding.homeRv.setLayoutManager(layoutManager);


        return view;
    }

    @Override
    public void getRanking(RankingBean rankingBean) {


        List<RankingBean.DataDTO> list = rankingBean.getData();

        rankingAdapter = new RankingAdapter(requireContext(), list);

        binding.homeRv.setAdapter(rankingAdapter);




        binding.mineRank.setText(rankingBean.getMyranking()+"");
        binding.mineName.setText(rankingBean.getMyname()+"");
        binding.mineSentence.setText("句子数："+rankingBean.getMycount()+"");
        binding.mineScore.setText("总分："+rankingBean.getMyscores()+"");

        Glide.with(requireContext()).load(rankingBean.getMyimgSrc()).into(binding.mineImg);
        if(rankingBean.getMycount() == 0){
            binding.mineAvg.setText("平均分："+0+"");
        }else{
            binding.mineAvg.setText("平均分："+(rankingBean.getMyscores()/rankingBean.getMycount())+"");
        }

        binding.myRanking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireContext(), UserRankingListActivity.class);

                Bundle bundle = new Bundle();

                bundle.putInt("uid",rankingBean.getMyid());

                bundle.putString("img",rankingBean.getMyimgSrc());

                bundle.putString("name", rankingBean.getMyname());

                bundle.putInt("score",rankingBean.getMyscores());

                bundle.putInt("sentence",rankingBean.getMycount());

                intent.putExtras(bundle);
                
                requireActivity().startActivity(intent);
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

    //重写刷新方法
    public void onResume() {

        super.onResume();
        rankingPresenter.getRanking(Constant.useruid, "D", 500, 0, "voa", Constant.voaid, sign);
    }
}