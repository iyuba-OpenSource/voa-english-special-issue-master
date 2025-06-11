package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.MyWordBean;
import com.iyuba.voaEnglish.model.home.MyWordModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.MyWordContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class MyWordPresenter extends BasePresenter<MyWordContract.MyWordView, MyWordContract.MyWordModel> implements MyWordContract.MyWordPresenter {


    @Override
    public void getMyWord(int u, int pageNumber, int pageCounts, String format) {
        Disposable disposable = model.getMyWord(u, pageNumber, pageCounts, format, new MyWordContract.MyWordCallBack() {
            @Override
            public void myWordSuccess(MyWordBean myWordBean) {
                view.getMyWord(myWordBean);
            }

            @Override
            public void myWordError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected MyWordContract.MyWordModel initModel() {
        return new MyWordModel();
    }
}
