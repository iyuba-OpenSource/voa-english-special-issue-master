package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.EvaHistoryBean;
import com.iyuba.voaEnglish.view.home.EvaHistoryContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class EvaHistoryModel implements EvaHistoryContract.EvaHistoryModel {
    @Override
    public Disposable getEvaHistory(int userId, String newstype, int newsid, EvaHistoryContract.EvaHistoryCallBack evaHistoryCallBack) {
        return Network
                .getRequest()
                .getEvaHistory("https://ai.iyuba.cn/api/getVoaTestRecord.jsp",userId,newstype,newsid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EvaHistoryBean>() {
                    @Override
                    public void accept(EvaHistoryBean evaHistoryBean) throws Exception {
                        evaHistoryCallBack.EvaHistorySuccess(evaHistoryBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        evaHistoryCallBack.EvaHistoryError((Exception) throwable);
                    }
                });

    }
}
