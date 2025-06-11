package com.iyuba.voaEnglish.adapter.ad;

import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.iyuba.voaEnglish.R;
import com.iyuba.voaEnglish.util.ad.BannerAd;
import com.yd.saas.common.pojo.YdNativePojo;

import java.util.List;

public class DrawVideoAdAdapter extends BaseQuickAdapter<BannerAd, BaseViewHolder> {


    public DrawVideoAdAdapter(int layoutResId, @Nullable List<BannerAd> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, BannerAd bannerAd) {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bannerAd.getType());
        stringBuilder.append("请求量：" + bannerAd.getRequestCount() + ",");
        stringBuilder.append("展现量：" + bannerAd.getReturnCount() + ",");
        stringBuilder.append("点击量：" + bannerAd.getClickCount());

        baseViewHolder.setText(R.id.banner_ad_tv, stringBuilder.toString());


        YdNativePojo ydNativePojo = bannerAd.getYdNativePojo();

        if (ydNativePojo != null) {

            ViewParent viewParent = ydNativePojo.getAdView().getParent();
            ViewGroup viewGroup = (ViewGroup) viewParent;

            if (viewGroup != null) {

                viewGroup.removeAllViews();
            }

            FrameLayout banner_ad_ll_ad = baseViewHolder.getView(R.id.banner_ad_ll_ad);
            banner_ad_ll_ad.removeAllViews();
            banner_ad_ll_ad.addView(ydNativePojo.getAdView());
        }
    }
}
