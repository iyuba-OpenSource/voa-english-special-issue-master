package com.iyuba.voaEnglish.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.VoaAdapter;
import com.iyuba.voaEnglish.adapter.VoaDetailAdapter;
import com.iyuba.voaEnglish.databinding.FragmentEvaluationBinding;
import com.iyuba.voaEnglish.databinding.FragmentIntroductionBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.presenter.home.HomePresenter;
import com.iyuba.voaEnglish.presenter.home.PagePresenter;
import com.iyuba.voaEnglish.view.home.HomeContract;
import com.iyuba.voaEnglish.view.home.PageContract;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IntroductionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IntroductionFragment extends Fragment implements PageContract.PageView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IntroductionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IntroductionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IntroductionFragment newInstance(String param1, String param2) {
        IntroductionFragment fragment = new IntroductionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentIntroductionBinding binding;

    private LinearLayoutManager layoutManager;

    VoaAdapter voaAdapter;

    PagePresenter pagePresenter;


    private int voaid;

    public void onDestroy() {
        super.onDestroy();
        pagePresenter.detachView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //网络请求，单例模式

        Network.getInstance().init();
        pagePresenter = new PagePresenter();
        pagePresenter.attchView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = requireActivity().getIntent().getExtras();

        voaid = 16840;


        if (bundle != null) {

            voaid = bundle.getInt("voaid");

        }

        binding = FragmentIntroductionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        pagePresenter.getDetailPage("iphone", "json", voaid);
        pagePresenter.getVoaLike("IOS", "json", 22, 0, 0, 0);


        layoutManager = new LinearLayoutManager(requireContext());

        binding.homeRv.setLayoutManager(layoutManager);

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
    public void getDetailPage(DetailPageBean detailPageBean) {

        List<DetailPageBean.DataDTO> list = detailPageBean.getData();

        Glide.with(requireActivity()).load(list.get(0).getPic()).into(binding.iamgeInt);

        binding.titleCnInt.setText(list.get(0).getTitleCn());

    }

    @Override
    public void getVoaLike(VoaBean voaBean) {
        List<VoaBean.DataDTO> listLike = voaBean.getData();

        voaAdapter = new VoaAdapter(getActivity(), listLike);
        binding.homeRv.setAdapter(voaAdapter);
    }
}