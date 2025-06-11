package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.LearningTimeBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface LearningTimeContract {


    interface LearningTimeView extends LoadingView{

        void getLearningTime(LearningTimeBean learningTimeBean);
    }

    interface LearningTimePresenter extends IBasePresenter<LearningTimeView>{

        void getLearningTime(int uid, long day, int flg);
    }

    interface LearningTimeModel extends BaseModel{

        Disposable getLearningTime(int uid, long day, int flg,LearningTimeCallBack learningTimeCallBack);
    }

    interface LearningTimeCallBack{

        void learningTimeSuccess(LearningTimeBean learningTimeBean);

        void learningTimeError(Exception e);
    }
}
