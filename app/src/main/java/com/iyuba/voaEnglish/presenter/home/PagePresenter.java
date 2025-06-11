package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.home.PageModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.HomeContract;
import com.iyuba.voaEnglish.view.home.PageContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class PagePresenter extends BasePresenter<PageContract.PageView, PageContract.PageModel> implements PageContract.PagePresenter {


    @Override
    public void getVoaLike(String type, String format, int appId, int maxid, int pages, int parentId) {
        Disposable disposable = model.getVoaLike(type, format, appId, maxid, pages, parentId, new PageContract.TitleCallbackLike() {
            @Override
            public void successLike(VoaBean voaBean) {
                view.getVoaLike(voaBean);
            }

            @Override
            public void errorLike(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getDetailPage(String type, String format, int voaid) {
        Disposable disposable = model.getDetailPage(type, format, voaid, new PageContract.DetailPageCallBack() {
            @Override
            public void successPage(DetailPageBean detailPageBean) {
                view.getDetailPage(detailPageBean);
            }

            @Override
            public void errorPage(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });

        addSubscribe(disposable);
    }


    @Override
    protected PageContract.PageModel initModel() {
        return new PageModel();
    }
}
