package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.PdfBean;
import com.iyuba.voaEnglish.model.home.PdfModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.PdfContact;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class PdfPresenter extends BasePresenter<PdfContact.PdfView, PdfContact.PdfModel> implements PdfContact.PdfPresenter {


    @Override
    public void getPdf(String idtype, int id, int isenglish) {

        Disposable disposable = model.getPdf(idtype, id, isenglish, new PdfContact.PdfCallBack() {
            @Override
            public void PdfSuccess(PdfBean pdfBean) {
                view.getPdf(pdfBean);
            }

            @Override
            public void PdfError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
    }

    @Override
    protected PdfContact.PdfModel initModel() {
        return new PdfModel();
    }
}
