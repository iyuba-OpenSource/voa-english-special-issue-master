package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.PdfBean;
import com.iyuba.voaEnglish.view.home.PdfContact;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PdfModel implements PdfContact.PdfModel {
    @Override
    public Disposable getPdf(String idtype, int id, int isenglish, PdfContact.PdfCallBack pdfCallBack) {
        return Network
                .getRequest()
                .getPdf(idtype,id,isenglish)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PdfBean>() {
                    @Override
                    public void accept(PdfBean pdfBean) throws Exception {
                        pdfCallBack.PdfSuccess(pdfBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        pdfCallBack.PdfError((Exception) throwable);
                    }
                });
    }
}
