package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.UserRankingDetailBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface UserRankingDetailContract {

    interface UserRankingDetailView extends LoadingView{

        void getUserRankingDetail(UserRankingDetailBean userRankingDetailBean);
    }

    interface UserRankingDetailPresenter extends IBasePresenter<UserRankingDetailView>{

        void getUserRankingDetail(String shuoshuoType, String topic, int topicId, int uid, String sign);
    }

    interface UserRankingDetailModel extends BaseModel{

        Disposable getUserRankingDetail(String shuoshuoType, String topic, int topicId, int uid, String sign, RankingDetailCallBack rankingDetailCallBack);
    }


    interface RankingDetailCallBack{

        void successRankingDetail(UserRankingDetailBean userRankingDetailBean);

        void errorRankingDetail(Exception e);
    }

}
