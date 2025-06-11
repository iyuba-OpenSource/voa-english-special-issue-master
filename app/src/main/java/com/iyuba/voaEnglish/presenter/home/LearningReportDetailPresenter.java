package com.iyuba.voaEnglish.presenter.home;





import com.iyuba.voaEnglish.model.bean.HearingDetailBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterDetailBean;
import com.iyuba.voaEnglish.model.bean.SpokenDetailBean;
import com.iyuba.voaEnglish.model.home.LearningReportDetailModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.LearningReportDetailContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class LearningReportDetailPresenter extends BasePresenter<LearningReportDetailContract.LearningReportDetailView,LearningReportDetailContract.LearningReportDetailModel> implements LearningReportDetailContract.LearningReportDetailPresenter{
    @Override
    protected LearningReportDetailContract.LearningReportDetailModel initModel() {
        return new LearningReportDetailModel();
    }

    @Override
    public void getHearingDetail(int NumPerPage, int Pageth, int TestMode, String uid, String sign) {

        Disposable disposable= model.getHearingDetail(NumPerPage, Pageth, TestMode, uid, sign, new LearningReportDetailContract.CallBackLearningReportDetail() {
            @Override
            public void successLearningReportDetail(HearingDetailBean hearingDetailBean) {
                view.getHearingDetail(hearingDetailBean);
            }

            @Override
            public void errorLearningReportDetail(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getSpokenDetail(String userId, String newstype, String language, int lastId, int pageCounts) {
        Disposable disposable= model.getSpokenDetail(userId, newstype, language, lastId, pageCounts, new LearningReportDetailContract.CallBackSpokenDetail() {
            @Override
            public void successSpokenDetail(SpokenDetailBean spokenDetailBean) {
                view.getSpokenDetail(spokenDetailBean);
            }

            @Override
            public void errorSpokenDetail(Exception e) {

                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getReadReporterDetail(String uid, String format, int thisNumber, int startNumber) {
        Disposable disposable= model.getReadReporterDetail(uid, format, thisNumber, startNumber, new LearningReportDetailContract.CallBackReadReporterDetail() {
            @Override
            public void successReadReporterDetail(ReadReporterDetailBean readReporterDetailBean) {
                view.getReadReporterDetail(readReporterDetailBean);
            }

            @Override
            public void errorReadReporter(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }

}
