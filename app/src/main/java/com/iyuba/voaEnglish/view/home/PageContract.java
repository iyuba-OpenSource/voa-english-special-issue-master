package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface PageContract {

    interface PageView extends LoadingView {


        void getDetailPage(DetailPageBean detailPageBean);

        void getVoaLike(VoaBean voaBean);
    }

    interface PagePresenter extends IBasePresenter<PageView> {

        void getVoaLike(String type, String format, int appId, int maxid, int pages, int parentId);


        void getDetailPage(String type, String format, int voaid);
    }

    interface PageModel extends BaseModel {

        Disposable getVoaLike(String type, String format, int appId, int maxid, int pages, int parentId, TitleCallbackLike titleCallbackVoa);

        Disposable getDetailPage(String type, String format, int voaid, DetailPageCallBack detailPageCallBack);


    }

    interface DetailPageCallBack {
        void successPage(DetailPageBean detailPageBean);

        void errorPage(Exception e);
    }

    interface TitleCallbackLike{
        void successLike(VoaBean voaBean);

        void errorLike(Exception e);
    }


}
