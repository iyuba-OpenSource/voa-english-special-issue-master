package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.RankingBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.BaseView;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface RankingContract {

    interface RankingView extends LoadingView {

        void getRanking(RankingBean rankingBean);

    }
    interface RankingPresenter extends IBasePresenter<RankingView>{
        void getRanking(int uid, String type, int total, int start, String topic, int topicid, String sign);

    }

    interface RankingModel extends BaseModel{
        Disposable getRanking(int uid, String type, int total, int start, String topic, int topicid, String sign, RankingCallBack rankingCallBack);
    }

    interface RankingCallBack{

        void successRanking(RankingBean rankingBean);

        void errorRanking(Exception e);
    }
}
