package com.iyuba.voaEnglish.view.home;




import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.HearingDetailBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterDetailBean;
import com.iyuba.voaEnglish.model.bean.SpokenDetailBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface LearningReportDetailContract {

    interface LearningReportDetailView extends LoadingView {
        void getHearingDetail(HearingDetailBean hearingDetailBean);

        void getSpokenDetail(SpokenDetailBean spokenDetailBean);

        void getReadReporterDetail(ReadReporterDetailBean readReporterDetailBean);


    }

    interface LearningReportDetailPresenter extends IBasePresenter<LearningReportDetailView> {
        void getHearingDetail(int NumPerPage,  int Pageth,  int TestMode, String uid,  String sign);

        void getSpokenDetail(String userId, String newstype,String language, int lastId,int pageCounts);

        void getReadReporterDetail( String uid, String format, int thisNumber,int startNumber);
    }

    interface LearningReportDetailModel extends BaseModel {

        Disposable  getHearingDetail(int NumPerPage, int Pageth, int TestMode, String uid, String sign, CallBackLearningReportDetail callBackLearningReportDetail);

        Disposable getSpokenDetail(String userId, String newstype, String language, int lastId, int pageCounts, CallBackSpokenDetail callBackSpokenDetail);

        Disposable getReadReporterDetail( String uid, String format, int thisNumber,int startNumber, CallBackReadReporterDetail callBackReadReporterDetail );

    }




    interface CallBackLearningReportDetail{

        void successLearningReportDetail(HearingDetailBean hearingDetailBean);

        void errorLearningReportDetail(Exception e);

    }

    interface CallBackSpokenDetail{

        void successSpokenDetail(SpokenDetailBean spokenDetailBean);

        void errorSpokenDetail(Exception e);

    }

    interface CallBackReadReporterDetail {

        void successReadReporterDetail (ReadReporterDetailBean readReporterDetailBean);

        void errorReadReporter(Exception e);

    }



}
