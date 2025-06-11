package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.EvaHistoryBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface EvaHistoryContract {

    interface EvaHistoryView extends LoadingView{
        void getEvaHistory(EvaHistoryBean evaHistoryBean);
    }

    interface EvaHistoryPresenter extends IBasePresenter<EvaHistoryView>{

        void getEvaHistory(int userId, String newstype, int newsid);
    }

    interface  EvaHistoryModel extends BaseModel{
        Disposable getEvaHistory(int userId, String newstype, int newsid,EvaHistoryCallBack evaHistoryCallBack);
    }

    interface EvaHistoryCallBack{

        void EvaHistorySuccess(EvaHistoryBean evaHistoryBean);

        void EvaHistoryError(Exception e);
    }
}
