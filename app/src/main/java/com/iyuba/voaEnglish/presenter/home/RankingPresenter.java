package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.RankingBean;
import com.iyuba.voaEnglish.model.home.RankingModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.RankingContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class RankingPresenter extends BasePresenter<RankingContract.RankingView,RankingContract.RankingModel> implements RankingContract.RankingPresenter {


    @Override
    public void getRanking(int uid, String type, int total, int start, String topic, int topicid, String sign) {
        Disposable disposable = model.getRanking(uid, type, total, start, topic, topicid, sign, new RankingContract.RankingCallBack() {
            @Override
            public void successRanking(RankingBean rankingBean) {
                view.getRanking(rankingBean);
            }

            @Override
            public void errorRanking(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected RankingContract.RankingModel initModel() {
        return new RankingModel();
    }
}
