package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.LearningTimeBean;
import com.iyuba.voaEnglish.model.home.LearningTimeModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.LearningTimeContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class LearningTimePresenter extends BasePresenter<LearningTimeContract.LearningTimeView, LearningTimeContract.LearningTimeModel> implements LearningTimeContract.LearningTimePresenter {


    @Override
    public void getLearningTime(int uid, long day, int flg) {
        Disposable disposable = model.getLearningTime(uid, day, flg, new LearningTimeContract.LearningTimeCallBack() {
            @Override
            public void learningTimeSuccess(LearningTimeBean learningTimeBean) {
                view.getLearningTime(learningTimeBean);
            }

            @Override
            public void learningTimeError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected LearningTimeContract.LearningTimeModel initModel() {
        return new LearningTimeModel();
    }
}
