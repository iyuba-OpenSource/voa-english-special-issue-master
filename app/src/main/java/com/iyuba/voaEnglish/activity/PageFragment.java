package com.iyuba.voaEnglish.activity;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.rgb;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;


import com.iyuba.headlinelibrary.util.ScreenUtil;
import com.iyuba.module.toolbox.DensityUtil;
import com.iyuba.sdk.data.iyu.IyuNative;
import com.iyuba.sdk.data.ydsdk.YDSDKTemplateNative;
import com.iyuba.sdk.data.youdao.YDNative;
import com.iyuba.sdk.mixnative.MixAdRenderer;
import com.iyuba.sdk.mixnative.MixNative;
import com.iyuba.sdk.mixnative.MixViewBinder;
import com.iyuba.sdk.mixnative.PositionLoadWay;
import com.iyuba.sdk.mixnative.StreamType;
import com.iyuba.sdk.nativeads.NativeAdPositioning;
import com.iyuba.sdk.nativeads.NativeEventListener;
import com.iyuba.sdk.nativeads.NativeRecyclerAdapter;
import com.iyuba.sdk.nativeads.NativeResponse;
import com.iyuba.voaEnglish.Constant;
import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.adapter.NoNetWorkVoaAdapter;
import com.iyuba.voaEnglish.adapter.VoaAdapter;
import com.iyuba.voaEnglish.databinding.FragmentPageBinding;
import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.AdEntryBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.presenter.PullUpLoading;
import com.iyuba.voaEnglish.presenter.home.AdEntryPresenter;
import com.iyuba.voaEnglish.presenter.home.HomePresenter;
import com.iyuba.voaEnglish.sqlitedb.VoaPageTableDb;
import com.iyuba.voaEnglish.view.home.AdEntryContract;
import com.iyuba.voaEnglish.view.home.HomeContract;
import com.youdao.sdk.nativeads.RequestParameters;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * 首页列表
 */
public class PageFragment extends Fragment implements HomeContract.HomeView, AdEntryContract.AdEntryView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public PageFragment() {
        // Required empty public constructor
    }

    public static PageFragment newInstance(String param1, String param2) {
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private FragmentPageBinding binding;


    private HomePresenter homePresenter;

    private int pages = 1;

    private int parentID = 0;

    private VoaAdapter voaAdapter;

    private NoNetWorkVoaAdapter noNetWorkVoaAdapter;


    private LinearLayoutManager layoutManager;

    private LinearLayoutManager layoutManager1;

    private PullUpLoading pullUpLoading;

    private int gray = Color.parseColor("#b1b1b1");


    //数据库
    private VoaPageTableDb voaPageTableDb;

    private SQLiteDatabase sqLiteDatabase;

    private boolean isFirstRequest = true;
    private  int userid;
    private AdEntryPresenter adEntryPresenter;
    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.detachView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //网络请求，单例模式

        userid= (Constant.useruid);

        homePresenter = new HomePresenter();
        homePresenter.attchView(this);


        adEntryPresenter= new AdEntryPresenter();
        adEntryPresenter.attchView(this);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();


        layoutManager1 = new LinearLayoutManager(requireContext());

        binding.networkHomeRv.setLayoutManager(layoutManager1);


        //若有数据库，则打开数据库，没有则创建数据库

        voaPageTableDb = new VoaPageTableDb(requireContext(), "pageTable.db", null, 1);


        sqLiteDatabase = voaPageTableDb.getReadableDatabase();


        //判断是否存在网络，若有网络，则执行下面网络刷新，否则判断本地数据库是否存在，若存在，则查询

        if (isNetConnected(requireContext())) {

            //下拉刷新

            binding.swiperRefersh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //isUpDown = false;

                    isFirstRequest = true;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, 1, 10, parentID);

                    binding.swiperRefersh.setRefreshing(false);
                }
            });

            layoutManager = new LinearLayoutManager(requireContext());

            binding.homeRv.setLayoutManager(layoutManager);


            //上滑加载

            pullUpLoading = new PullUpLoading(layoutManager) {


                @Override
                public void onLoadMore() {

                    //Toast.makeText(requireContext(), "加载中...", Toast.LENGTH_SHORT).show();

                    binding.loadingLoadend.setVisibility(View.VISIBLE);
                    //binding.loading.setVisibility(View.VISIBLE);


                    binding.homeRv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (voaAdapter == null) {
                                return;
                            }


                            homePresenter.getVoaTitle("iphone", "json", 104, 0, ++pages, 10, parentID);
                            //binding.loading.setVisibility(View.GONE);
                            binding.loadingLoadend.setVisibility(View.GONE);
                        }
                    }, 1000);


                }


            };

            //上拉加载
            binding.homeRv.addOnScrollListener(pullUpLoading);

            //设置点击事件

            binding.top.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    parentID = 0;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.us.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.all.setTextColor(BLACK);

                }
            });
            binding.top.us.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    isFirstRequest = true;
                    parentID = 1;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.us.setTextColor(BLACK);

                }
            });
            binding.top.world.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 2;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.world.setTextColor(BLACK);
                }
            });
            binding.top.life.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 3;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.life.setTextColor(BLACK);
                }
            });
            binding.top.amuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 4;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.amuse.setTextColor(BLACK);
                }
            });
            binding.top.health.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 5;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.health.setTextColor(BLACK);
                }
            });
            binding.top.education.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 6;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.education.setTextColor(BLACK);
                }
            });
            binding.top.business.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 7;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.business.setTextColor(BLACK);
                }
            });
            binding.top.science.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 8;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.science.setTextColor(BLACK);
                }
            });
            binding.top.history.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 9;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.word.setTextColor(gray);
                    binding.top.history.setTextColor(BLACK);
                }
            });
            binding.top.word.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isFirstRequest = true;
                    parentID = 10;
                    pages = 1;
                    homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
                    binding.top.all.setTextColor(gray);
                    binding.top.world.setTextColor(gray);
                    binding.top.life.setTextColor(gray);
                    binding.top.amuse.setTextColor(gray);
                    binding.top.health.setTextColor(gray);
                    binding.top.education.setTextColor(gray);
                    binding.top.business.setTextColor(gray);
                    binding.top.science.setTextColor(gray);
                    binding.top.history.setTextColor(gray);
                    binding.top.us.setTextColor(gray);
                    binding.top.word.setTextColor(BLACK);
                }
            });
        } else {
            //无网络
            //查询数据库

//            binding.network.setVisibility(View.VISIBLE);
//            binding.swiperRefersh.setVisibility(View.GONE);
//            Cursor cursor = sqLiteDatabase.query("pageTable", null, null, null, null, null, "createTime desc");
//
//
//            List<Map<String, String>> list = new ArrayList<>();
//
//            while (cursor.moveToNext()) {
//
//                @SuppressLint("Range") String title = cursor.getString(cursor.getColumnIndex("title"));
//                @SuppressLint("Range") String titleCn = cursor.getString(cursor.getColumnIndex("titleCn"));
//                @SuppressLint("Range") String voaid = cursor.getString(cursor.getColumnIndex("voaid"));
//                @SuppressLint("Range") String readNum = cursor.getString(cursor.getColumnIndex("readNum"));
//                @SuppressLint("Range") String createTime = cursor.getString(cursor.getColumnIndex("createTime"));
//
//                Map<String, String> map = new HashMap<>();
//                map.put("voaid", voaid);
//                map.put("title", title);
//                map.put("titleCn", titleCn);
//                map.put("readNum", readNum);
//                map.put("createTime", createTime);
//
//                list.add(map);
//
//
//            }
//
//
//            //设置adapter，
//
//            noNetWorkVoaAdapter = new NoNetWorkVoaAdapter(requireContext(), list);
//            binding.networkHomeRv.setAdapter(noNetWorkVoaAdapter);
//            noNetWorkVoaAdapter.notifyDataSetChanged();
//

        }


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (isNetConnected(requireContext())) {
            homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
            binding.top.all.setTextColor(BLACK);
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
    public void getVoaTitle(VoaBean voaBean, int pages) {

//        for (int i = 0; i < voaBean.getData().size(); i++) {
//            ContentValues contentValues = new ContentValues();
//            contentValues.put("voaid", Integer.parseInt(voaBean.getData().get(i).getVoaId()));
//            contentValues.put("title", voaBean.getData().get(i).getTitle());
//            contentValues.put("titleCn", voaBean.getData().get(i).getTitleCn());
//            contentValues.put("readNum", voaBean.getData().get(i).getReadCount());
//            contentValues.put("createTime", voaBean.getData().get(i).getCreatTime());
//            sqLiteDatabase.insert("pageTable", null, contentValues);
//
//        }


        //将获取的东西存储到常量中
        for (int i = 0; i < voaBean.getData().size(); i++) {
            Constant.orlVoaid[i] = voaBean.getData().get(i).getVoaId();
            Constant.orlSound[i] = voaBean.getData().get(i).getSound();
        }


//        if (pullUpLoading.isLoading()) {   //执行上拉刷新
//
//            List<VoaBean.DataDTO> list = voaBean.getData();
////                Constant.oriendboaid =list.get(9).getNewsId();
//            if (voaAdapter == null) {
//                voaAdapter = new VoaAdapter(requireContext()list);
//                binding.homeRv.setAdapter(voaAdapter);
//
//            } else {
////                    Constant.oriendboaid =list.get(9).getNewsId();
//                List<VoaBean.DataDTO> dataDTOS = voaAdapter.getDatas();
//                dataDTOS.addAll(list);   //添加进去 ,并且刷新
//                voaAdapter.notifyDataSetChanged();
//            }
//
//        } else {
//
//            List<VoaBean.DataDTO> list = voaBean.getData();
//
////                Constant.oriendboaid =list.get(9).getNewsId();
//            if (voaAdapter == null) {
//                voaAdapter = new VoaAdapter(getActivity(), list);
//                binding.homeRv.setAdapter(voaAdapter);
//            }
//            voaAdapter.notifyDataSetChanged();
//
//        }
//        pullUpLoading.setLoading(false);


        if (pullUpLoading.isLoading()) {

            List<VoaBean.DataDTO> list = voaBean.getData();
            //控制定位光标  2行代码
            if (voaAdapter == null) {

                voaAdapter = new VoaAdapter(getActivity(), list);
                binding.homeRv.setAdapter(voaAdapter);
            } else {

                List<VoaBean.DataDTO> dataDTOS = voaAdapter.getDatas();
                dataDTOS.addAll(list);
                voaAdapter.notifyDataSetChanged();
            }


        } else {

//            List<VoaBean.DataDTO> list = voaBean.getData();
//            if (voaAdapter == null) {
//                voaAdapter = new VoaAdapter(getActivity(), list);
//                binding.homeRv.setAdapter(voaAdapter);
//            } else {
//
//                voaAdapter.getDatas().clear();
//                voaAdapter.setDatas(list);
//                voaAdapter.notifyDataSetChanged();
//            }


            List<VoaBean.DataDTO> list = voaBean.getData();

            voaAdapter = new VoaAdapter(getActivity(), list);
            binding.homeRv.setAdapter(voaAdapter);
        }
        pullUpLoading.setLoading(false);

//
//        if (pullUpLoading.isLoading()) {   //执行上拉刷新
//
//            List<VoaBean.DataDTO> list = voaBean.getData();
//
//            if (voaAdapter == null) {
//
//                voaAdapter = new VoaAdapter(getActivity(), list);
//                binding.homeRv.setAdapter(voaAdapter);
//
//            } else {
//
//                List<VoaBean.DataDTO> dataDTOS = voaBean.getData();
//                dataDTOS.addAll(list);   //添加进去 ,并且刷新
//
//
//                voaAdapter.notifyDataSetChanged();
//            }
//
//        } else {
//
//            List<homeBean.DataDTO> list = homebean.getData();
//
//            voaAdapter = new VoaAdapter(getActivity(), list);
//            binding.homeRv.setAdapter(voaAdapter);
//
//        }
//        pullUpLoading.setLoading(false);


        Log.d("fang789654", "getVoaTitle: "+Constant.vipStatus);
        if (isFirstRequest) {
            isFirstRequest = false;
            //会员去广告
            if (Constant.vipStatus == 0 ) {


                adEntryPresenter.getAdEntryAll(Constant.AdAppId,2,Constant.useruid);
            }

        }

    }

    @Override
    public void getVoaDetail(VoaDetailBean voaDetailBean) {

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

    @Override
    public void getAdEntryAll(List<AdEntryBean> adEntryBean) {


        OkHttpClient mClient = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        EnumSet<RequestParameters.NativeAdAsset> desiredAssets = EnumSet.of(
                RequestParameters.NativeAdAsset.TITLE,
                RequestParameters.NativeAdAsset.TEXT,
                RequestParameters.NativeAdAsset.ICON_IMAGE,
                RequestParameters.NativeAdAsset.MAIN_IMAGE,
                RequestParameters.NativeAdAsset.CALL_TO_ACTION_TEXT);
        RequestParameters requestParameters = new RequestParameters.RequestParametersBuilder()
                .location(null)
                .keywords(null)
                .desiredAssets(desiredAssets)
                .build();


        YDNative ydNative = new YDNative(requireActivity(), "edbd2c39ce470cd72472c402cccfb586", requestParameters);

        IyuNative iyuNative = new IyuNative(requireActivity(), "2011", mClient);

        iyuNative.setBase("http://192.168.101.105:8777/getAdEntryAll");
        iyuNative.setImageUrlPrefix("http://192.168.101.105:8777/static/image/");


        // csj - ylh - ks -bd  0224  0229 0236 0233
        YDSDKTemplateNative csjTemplateNative = new YDSDKTemplateNative(requireActivity(), "0241");
        YDSDKTemplateNative ylhTemplateNative = new YDSDKTemplateNative(requireActivity(), "0246");
        YDSDKTemplateNative ksTemplateNative = new YDSDKTemplateNative(requireActivity(), "0253");
        YDSDKTemplateNative bdTemplateNative = new YDSDKTemplateNative(requireActivity(), "0250");

        //添加key
        HashMap<Integer, YDSDKTemplateNative> ydsdkMap = new HashMap<>();
        ydsdkMap.put(StreamType.TT, csjTemplateNative);
        ydsdkMap.put(StreamType.GDT, ylhTemplateNative);
        ydsdkMap.put(StreamType.KS, ksTemplateNative);
        ydsdkMap.put(StreamType.BAIDU, bdTemplateNative);


        MixNative mixNative = new MixNative(ydNative, iyuNative, ydsdkMap);
        PositionLoadWay loadWay = new PositionLoadWay();

        Log.d("fang789654", "getAdEntryAllComplete: " + adEntryBean.get(0).getData().getFirstLevel() + "//" + adEntryBean.get(0).getData().getSecondLevel() + "//" + adEntryBean.get(0).getData().getThirdLevel());


        loadWay.setStreamSource(new int[]{
                Integer.parseInt(adEntryBean.get(0).getData().getFirstLevel()),
                Integer.parseInt(adEntryBean.get(0).getData().getSecondLevel()),
                Integer.parseInt(adEntryBean.get(0).getData().getThirdLevel())
        });
        mixNative.setLoadWay(loadWay);


        int dp8px = DensityUtil.dp2px(requireContext(), 8.0f);
        int dp16px = DensityUtil.dp2px(requireContext(), 16.0f);
        int screenWidth = ScreenUtil.getScreenWidth(requireContext());
        int viewWidth = screenWidth - (2 * dp8px);
        int adWidth =  viewWidth;
        int picWidth = (int) (viewWidth * 0.43);
        int picHeight = (picWidth * 2) / 3;
        int adHeight = picHeight + dp16px;
        mixNative.setWidthHeight(adWidth, 0);

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        WindowManager windowManager = (WindowManager) requireActivity().getSystemService(Context.WINDOW_SERVICE);
//        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
//        mixNative.setWidthHeight(displayMetrics.widthPixels, DensityUtil.dp2px(MyApplication.getContext(), 120));


        int startPosition = 3;
        int positionInterval = 5;
        NativeAdPositioning.ClientPositioning positioning = new NativeAdPositioning.ClientPositioning();

        positioning.addFixedPosition(startPosition);
        positioning.enableRepeatingPositions(positionInterval);
        NativeRecyclerAdapter mAdAdapter = new NativeRecyclerAdapter(requireActivity(), voaAdapter, positioning);




        mAdAdapter.setNativeEventListener(new NativeEventListener() {
            @Override
            public void onNativeImpression(View view, NativeResponse nativeResponse) {

                Log.d("fang789654", "onNativeImpression: ");


            }

            @Override
            public void onNativeClick(View view, NativeResponse nativeResponse) {

                Log.d("fang789654", "onNativeClick: ");



            }
        });
        mAdAdapter.setAdSource(mixNative);

        //关闭广告

        mixNative.setYDSDKTemplateNativeClosedListener(new MixNative.YDSDKTemplateNativeClosedListener() {
            @Override
            public void onClosed(View view) {

                View itemView = (View) view.getParent();
                RecyclerView.ViewHolder viewHolder = binding.homeRv.getChildViewHolder(itemView);
                int position =viewHolder.getBindingAdapterPosition();
                mAdAdapter.removeAdsWithAdjustedPosition(position);




            }
        });

        MixViewBinder mixViewBinder = new MixViewBinder.Builder(R.layout.item_homeadline)
                .templateContainerId(R.id.item0)
                .nativeContainerId(R.id.kuangjia)
                .nativeImageId(R.id.photo)
                .nativeTitleId(R.id.item11)
                .build();

        MixAdRenderer mixAdRenderer = new MixAdRenderer(mixViewBinder);
        mAdAdapter.registerAdRenderer(mixAdRenderer);
        binding.homeRv.setAdapter(mAdAdapter);
        mAdAdapter.loadAds();

    }

    //判断网络网络是否可用

    @Override
    public void onResume() {

//        checkAdShow();
//
//        Log.d("fang555555", "onResume: ");

        super.onResume();


    }

    public void checkAdShow(){

        if (userid  != (Constant.useruid)){

            isFirstRequest = true;
            userid = Constant.useruid;


            Log.d("fang555555", "checkAdShow: ");
            parentID = 0;
            pages = 1;
            homePresenter.getVoaTitle("iphone", "json", 104, 0, pages, 10, parentID);
            binding.top.us.setTextColor(gray);
            binding.top.world.setTextColor(gray);
            binding.top.life.setTextColor(gray);
            binding.top.amuse.setTextColor(gray);
            binding.top.health.setTextColor(gray);
            binding.top.education.setTextColor(gray);
            binding.top.business.setTextColor(gray);
            binding.top.science.setTextColor(gray);
            binding.top.history.setTextColor(gray);
            binding.top.word.setTextColor(gray);
            binding.top.all.setTextColor(BLACK);


        }

    }



}