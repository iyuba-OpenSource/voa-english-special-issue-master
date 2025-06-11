package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.view.home.HomeContract;
import com.iyuba.voaEnglish.view.home.PageContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PageModel implements PageContract.PageModel {


    @Override
    public Disposable getVoaLike(String type, String format, int appId, int maxid, int pages, int parentId, PageContract.TitleCallbackLike titleCallbacklLike) {
        return Network
                .getRequest()
                .getVoaLike(type,format,appId,maxid,pages,parentId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VoaBean>() {
                    @Override
                    public void accept(VoaBean voaBean) throws Exception {
                        titleCallbacklLike.successLike(voaBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        titleCallbacklLike.errorLike((Exception) throwable);
                    }
                });

    }

    @Override
    public Disposable getDetailPage(String type, String format, int voaid, PageContract.DetailPageCallBack detailPageCallBack) {
        return Network
                .getRequest()
                .getDetailPic(type, format, voaid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DetailPageBean>() {
                    @Override
                    public void accept(DetailPageBean detailPageBean) throws Exception {

                        detailPageCallBack.successPage(detailPageBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        detailPageCallBack.errorPage((Exception) throwable);
                    }
                });
    }
}
