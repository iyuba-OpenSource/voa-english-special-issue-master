package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.UserRankingDetailBean;
import com.iyuba.voaEnglish.model.home.UserRankingDetailModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.UserRankingDetailContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class UserRankingDetailPresenter extends BasePresenter<UserRankingDetailContract.UserRankingDetailView, UserRankingDetailContract.UserRankingDetailModel> implements UserRankingDetailContract.UserRankingDetailPresenter {


    @Override
    public void getUserRankingDetail(String shuoshuoType, String topic, int topicId, int uid, String sign) {

        Disposable disposable = model.getUserRankingDetail(shuoshuoType, topic, topicId, uid, sign, new UserRankingDetailContract.RankingDetailCallBack() {
            @Override
            public void successRankingDetail(UserRankingDetailBean userRankingDetailBean) {
                view.getUserRankingDetail(userRankingDetailBean);
            }

            @Override
            public void errorRankingDetail(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected UserRankingDetailContract.UserRankingDetailModel initModel() {
        return new UserRankingDetailModel();
    }
}
