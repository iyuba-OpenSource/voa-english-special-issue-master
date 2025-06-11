package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.RankingBean;
import com.iyuba.voaEnglish.view.home.RankingContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RankingModel implements RankingContract.RankingModel {
    @Override
    public Disposable getRanking(int uid, String type, int total, int start, String topic, int topicid, String sign, RankingContract.RankingCallBack rankingCallBack) {
        return Network
                .getRequest()
                .getRanking(uid,type, total,start, topic, topicid, sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RankingBean>() {
                    @Override
                    public void accept(RankingBean rankingBean) throws Exception {

                        rankingCallBack.successRanking(rankingBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        rankingCallBack.errorRanking((Exception) throwable);
                    }
                });
    }
}
