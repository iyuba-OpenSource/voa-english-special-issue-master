package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.CollectBean;
import com.iyuba.voaEnglish.model.home.CollectModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.CollectContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class CollectPresenter extends BasePresenter<CollectContract.CollectView, CollectContract.CollectModel> implements CollectContract.CollectPresenter {


    @Override
    public void getCollect(String groupName, int sentenceFlg, int appId, int userId, String type, int voaId, int sentenceId, String topic, String format) {
        Disposable disposable = model.getCollect(groupName, sentenceFlg, appId, userId, type, voaId, sentenceId, topic, format, new CollectContract.CollectCallBack() {
            @Override
            public void successCollect(CollectBean collectBean) {
                view.getCollect(collectBean);
            }

            @Override
            public void errorCollect(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
    }

    @Override
    protected CollectContract.CollectModel initModel() {
        return new CollectModel();
    }
}
