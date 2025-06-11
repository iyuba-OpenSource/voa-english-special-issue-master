package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.LearningTimeBean;
import com.iyuba.voaEnglish.view.home.LearningTimeContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LearningTimeModel implements LearningTimeContract.LearningTimeModel {
    @Override
    public Disposable getLearningTime(int uid, long day, int flg, LearningTimeContract.LearningTimeCallBack learningTimeCallBack) {
        return Network
                .getRequest()
                .getLearningTime(uid, day, flg)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LearningTimeBean>() {
                    @Override
                    public void accept(LearningTimeBean learningTimeBean) throws Exception {
                        learningTimeCallBack.learningTimeSuccess(learningTimeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        learningTimeCallBack.learningTimeError((Exception) throwable);
                    }
                });
    }
}
