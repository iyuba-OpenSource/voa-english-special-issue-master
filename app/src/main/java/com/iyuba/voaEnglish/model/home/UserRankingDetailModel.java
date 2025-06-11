package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.UserRankingDetailBean;
import com.iyuba.voaEnglish.view.home.UserRankingDetailContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class UserRankingDetailModel implements UserRankingDetailContract.UserRankingDetailModel {
    @Override
    public Disposable getUserRankingDetail(String shuoshuoType, String topic, int topicId, int uid, String sign, UserRankingDetailContract.RankingDetailCallBack rankingDetailCallBack) {
        return Network
                .getRequest()
                .getUserRankingDetail(shuoshuoType,topic,topicId,uid,sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserRankingDetailBean>() {
                    @Override
                    public void accept(UserRankingDetailBean userRankingDetailBean) throws Exception {
                        rankingDetailCallBack.successRankingDetail(userRankingDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rankingDetailCallBack.errorRankingDetail((Exception) throwable);
                    }
                });
    }
}
