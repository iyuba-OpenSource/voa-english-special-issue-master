package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.MyCollectBean;
import com.iyuba.voaEnglish.model.home.MyCollectModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.MyCollectContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class MyCollectPresenter extends BasePresenter<MyCollectContract.MyCollectView, MyCollectContract.MyCollectModel> implements MyCollectContract.MyCollectPresenter {

    @Override
    public void getMyCollect(int userid, String topic, int appid, int sentenceFlg, String format, String sign) {
        Disposable disposable = model.getMyCollect(userid, topic, appid, sentenceFlg, format, sign, new MyCollectContract.MyCollectCallBack() {
            @Override
            public void MyCollectSuccess(MyCollectBean myCollectBean) {
                view.getMyCollect(myCollectBean);
            }

            @Override
            public void MyCollectError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected MyCollectContract.MyCollectModel initModel() {
        return new MyCollectModel();
    }

}
