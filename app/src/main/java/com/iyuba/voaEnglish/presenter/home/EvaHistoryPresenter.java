package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.EvaHistoryBean;
import com.iyuba.voaEnglish.model.home.EvaHistoryModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.EvaHistoryContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class EvaHistoryPresenter extends BasePresenter<EvaHistoryContract.EvaHistoryView, EvaHistoryContract.EvaHistoryModel> implements EvaHistoryContract.EvaHistoryPresenter {

    @Override
    public void getEvaHistory(int userId, String newstype, int newsid) {
        Disposable disposable = model.getEvaHistory(userId, newstype, newsid, new EvaHistoryContract.EvaHistoryCallBack() {
            @Override
            public void EvaHistorySuccess(EvaHistoryBean evaHistoryBean) {
                view.getEvaHistory(evaHistoryBean);
            }

            @Override
            public void EvaHistoryError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected EvaHistoryContract.EvaHistoryModel initModel() {
        return new EvaHistoryModel();
    }

}
