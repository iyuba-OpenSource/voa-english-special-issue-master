package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.GetWordsBean;
import com.iyuba.voaEnglish.model.home.GetWordsModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.GetWordsContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class GetWordsPresenter extends BasePresenter<GetWordsContract.GetWordsView, GetWordsContract.GetWordsModel> implements GetWordsContract.GetWordsPresenter {


    @Override
    public void getWordsMes(String q, String format) {
        Disposable disposable = model.getWordsMes(q, format, new GetWordsContract.GetWordsCallBack() {
            @Override
            public void getWordsSuccess(GetWordsBean getWordsBean) {
                view.getWordsMes(getWordsBean);
            }

            @Override
            public void getWordsError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }
    @Override
    protected GetWordsContract.GetWordsModel initModel() {
        return new GetWordsModel();
    }

}
