package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.view.home.HomeContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomeModel implements HomeContract.HomeModel {
    @Override
    public Disposable getVoaTitle(String type, String format, int appId, int maxid, int pages, int pageNum, int parentId, HomeContract.TitleCallbackVoa titleCallbackVoa) {
        return Network
                .getRequest()
                .getVoaTitle(type, format, appId, maxid, pages, pageNum, parentId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VoaBean>() {
                    @Override
                    public void accept(VoaBean voaBean) throws Exception {

                        titleCallbackVoa.successVoa(voaBean);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                        titleCallbackVoa.errorVoa((Exception) throwable);

                    }
                });
    }

    @Override
    public Disposable getVoaDetail(String format, int voaid, HomeContract.TitleCallbackVoaDatail titleCallbackVoaDatail) {
        return Network
                .getRequest()
                .getVoaDetail(format, voaid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VoaDetailBean>() {
                    @Override
                    public void accept(VoaDetailBean voaDetailBean) throws Exception {
                        titleCallbackVoaDatail.successVoaDetail(voaDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        titleCallbackVoaDatail.errorVoaDetail((Exception) throwable);
                    }
                });
    }





}
