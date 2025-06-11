package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.CollectBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface CollectContract {

    interface CollectView extends LoadingView{
        void getCollect(CollectBean collectBean);
    }


    interface CollectPresenter extends IBasePresenter<CollectView>{
        void getCollect(String groupName, int sentenceFlg, int appId, int userId, String type, int voaId, int sentenceId, String topic, String format);
    }

    interface CollectModel extends BaseModel{
        Disposable getCollect(String groupName, int sentenceFlg, int appId, int userId, String type, int voaId, int sentenceId, String topic, String format,CollectCallBack collectCallBack);
    }

    interface CollectCallBack{
        void successCollect(CollectBean collectBean);

        void errorCollect(Exception e);
    }
}
