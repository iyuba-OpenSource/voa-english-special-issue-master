package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.MyCollectBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface MyCollectContract {

    interface MyCollectView extends LoadingView{
        void getMyCollect(MyCollectBean myCollectBean);
    }

    interface MyCollectPresenter extends IBasePresenter<MyCollectView>{
        void getMyCollect(int userid, String topic, int appid, int sentenceFlg, String format, String sign);
    }

    interface MyCollectModel extends BaseModel{
        Disposable getMyCollect(int userid, String topic, int appid, int sentenceFlg, String format, String sign,MyCollectCallBack myCollectCallBack);
    }

    interface MyCollectCallBack{
        void MyCollectSuccess(MyCollectBean myCollectBean);

        void MyCollectError(Exception e);
    }
}
