package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.PdfBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface PdfContact {

    interface PdfView extends LoadingView{
        void getPdf(PdfBean pdfBean);
    }

    interface PdfPresenter extends IBasePresenter<PdfView>{
        void getPdf(String idtype, int id, int isenglish);
    }

    interface PdfModel extends BaseModel{
        Disposable getPdf(String idtype, int id, int isenglish,PdfCallBack pdfCallBack);
    }

    interface PdfCallBack{

        void PdfSuccess(PdfBean pdfBean);

        void PdfError(Exception e);
    }
}
