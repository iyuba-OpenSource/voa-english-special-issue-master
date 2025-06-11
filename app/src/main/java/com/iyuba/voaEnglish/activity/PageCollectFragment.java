package com.iyuba.voaEnglish.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.adapter.PageCollectAdapter;
import com.iyuba.voaEnglish.adapter.VoaAdapter;
import com.iyuba.voaEnglish.databinding.FragmentPageCollectBinding;
import com.iyuba.voaEnglish.model.MD5;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.CollectBean;
import com.iyuba.voaEnglish.model.bean.MyCollectBean;
import com.iyuba.voaEnglish.presenter.home.CollectPresenter;
import com.iyuba.voaEnglish.presenter.home.HomePresenter;
import com.iyuba.voaEnglish.presenter.home.MyCollectPresenter;
import com.iyuba.voaEnglish.view.home.CollectContract;
import com.iyuba.voaEnglish.view.home.MyCollectContract;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PageCollectFragment extends Fragment implements MyCollectContract.MyCollectView, CollectContract.CollectView {


    private FragmentPageCollectBinding binding;

    private MyCollectPresenter myCollectPresenter;

    private CollectPresenter collectPresenter;

    private PageCollectAdapter pageCollectAdapter;

    private RecyclerView.LayoutManager layoutManager;

    public static PageCollectFragment newInstance() {
        return new PageCollectFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Network.getInstance().init();
        myCollectPresenter = new MyCollectPresenter();
        myCollectPresenter.attchView(this);

        collectPresenter = new CollectPresenter();
        collectPresenter.attchView(this);


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPageCollectBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        //使用adapter时写，布局管理器
        layoutManager = new LinearLayoutManager(requireContext());

        binding.scRv.setLayoutManager(layoutManager);

        if (Constant.useruid != 0) {

            Date sr = new Date();
            long res1 = sr.getTime();
            long res = (res1 - 8 * 60 * 1000) / (24 * 60 * 60 * 1000) + 1;
            String sign = MD5.md5("iyuba" + Constant.useruid + "voa" + 201 + res);
            myCollectPresenter.getMyCollect(Constant.useruid, "voa", 201, 0, "json", sign);
        }


        binding.scWrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //isUpDown = false;

                if (Constant.useruid != 0) {

                    Date sr = new Date();
                    long res1 = sr.getTime();
                    long res = (res1 - 8 * 60 * 1000) / (24 * 60 * 60 * 1000) + 1;
                    String sign = MD5.md5("iyuba" + Constant.useruid + "voa" + 201 + res);
                    myCollectPresenter.getMyCollect(Constant.useruid, "voa", 201, 0, "json", sign);
                }

                binding.scWrl.setRefreshing(false);
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
    public void getMyCollect(MyCollectBean myCollectBean) {


        List<MyCollectBean.DataDTO> list = myCollectBean.getData();
        pageCollectAdapter = new PageCollectAdapter(requireContext(), list);
        binding.scRv.setAdapter(pageCollectAdapter);
        pageCollectAdapter.notifyDataSetChanged();

        pageCollectAdapter.setCallBack(new PageCollectAdapter.CallBack() {
            @Override
            public void clickDel() {

                if(pageCollectAdapter.isDel()){
                    collectPresenter.getCollect("Iyuba", 0, 201, Constant.useruid, "del", pageCollectAdapter.getPageVoaid(), 0, "voa", "json");

                    if (Constant.useruid != 0) {

                        Date sr = new Date();
                        long res1 = sr.getTime();
                        long res = (res1 - 8 * 60 * 1000) / (24 * 60 * 60 * 1000) + 1;
                        String sign = MD5.md5("iyuba" + Constant.useruid + "voa" + 201 + res);
                        myCollectPresenter.getMyCollect(Constant.useruid, "voa", 201, 0, "json", sign);
                    }

                    pageCollectAdapter.setDel(false);
                }


            }
        });


    }

    @Override
    public void getCollect(CollectBean collectBean) {


    }
}