package com.iyuba.voaEnglish.view.home;



import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.StudyRecordByTestModeBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface UpStudyRecordContract {
    interface UpStudyRecordView extends LoadingView {
        void UpStudyRecord(StudyRecordByTestModeBean studyRecordByTestModeBean);
    }

    interface UpStudyRecordPresenter extends IBasePresenter<UpStudyRecordView> {
        void UpStudyRecord(String format, String uid,  int appId, String BeginTime
                , String EndTime,String Lesson, String LessonId, String EndFlg, String Device,  String platform
                ,  String IP,  String sign,  String TestMode,  String TestNumber, String TestWords,int rewardVersion);

    }
    interface UpStudyRecordModel extends BaseModel {

        Disposable UpStudyRecord(String format, String uid,  int appId, String BeginTime
                , String EndTime,String Lesson, String LessonId, String EndFlg, String Device,  String platform
                ,  String IP,  String sign,  String TestMode,  String TestNumber, String TestWords,int rewardVersion, CallBackUpStudyRecord callBackUpStudyRecord);
    }

    interface CallBackUpStudyRecord{

        void successUpStudyRecord(StudyRecordByTestModeBean studyRecordByTestModeBean);

        void errorUpStudyRecord(Exception e);

    }
}
