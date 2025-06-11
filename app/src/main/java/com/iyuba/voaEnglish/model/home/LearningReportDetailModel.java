package com.iyuba.voaEnglish.model.home;




import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.HearingDetailBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterDetailBean;
import com.iyuba.voaEnglish.model.bean.SpokenDetailBean;
import com.iyuba.voaEnglish.view.home.LearningReportDetailContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class LearningReportDetailModel implements LearningReportDetailContract.LearningReportDetailModel {
    @Override
    public Disposable getHearingDetail(int NumPerPage, int Pageth, int TestMode, String uid, String sign, LearningReportDetailContract.CallBackLearningReportDetail callBackLearningReportDetail) {
        return Network
                .getRequest()
                .getHearingDetail("http://daxue.iyuba.cn/ecollege/getStudyRecordByTestMode.jsp?", NumPerPage,   Pageth,   TestMode,  uid,   sign)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HearingDetailBean>() {
                    @Override
                    public void accept(HearingDetailBean hearingDetailBean) throws Exception {
                        callBackLearningReportDetail.successLearningReportDetail(hearingDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackLearningReportDetail.errorLearningReportDetail((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getSpokenDetail(String userId, String newstype, String language, int lastId, int pageCounts, LearningReportDetailContract.CallBackSpokenDetail callBackSpokenDetail) {
        return Network
                .getRequest()
                .getSpokenDetail("http://ai.iyuba.cn/management/getDetailInfo.jsp?",userId,  newstype,  language,  lastId,  pageCounts)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SpokenDetailBean>() {
                    @Override
                    public void accept(SpokenDetailBean spokenDetailBean) throws Exception {
                        callBackSpokenDetail.successSpokenDetail(spokenDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackSpokenDetail.errorSpokenDetail((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getReadReporterDetail(String uid, String format, int thisNumber, int startNumber, LearningReportDetailContract.CallBackReadReporterDetail callBackReadReporterDetail) {
        return Network
                .getRequest()
                .getReadReporterDetail("http://cms.iyuba.cn/newsApi/getRecentRV.jsp?",uid,format,thisNumber,startNumber)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ReadReporterDetailBean>() {
                    @Override
                    public void accept(ReadReporterDetailBean readReporterDetailBean) throws Exception {
                        callBackReadReporterDetail.successReadReporterDetail(readReporterDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackReadReporterDetail.errorReadReporter((Exception) throwable);
                    }
                });
    }
}
