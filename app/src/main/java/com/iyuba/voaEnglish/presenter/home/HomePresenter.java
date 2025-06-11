package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.model.home.HomeModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.HomeContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class HomePresenter extends BasePresenter<HomeContract.HomeView, HomeContract.HomeModel> implements HomeContract.HomePresenter {


    //函数的返回接口,成功和失败的实现方法
    @Override
    public void getVoaTitle(String type, String format, int appId, int maxid, int pages, int pageNum, int parentId) {

        Disposable disposable = model.getVoaTitle(type, format, appId, maxid, pages, pageNum, parentId, new HomeContract.TitleCallbackVoa() {
            @Override
            public void successVoa(VoaBean voaBean) {

                view.getVoaTitle(voaBean, pages);

            }

            @Override
            public void errorVoa(Exception e) {

                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }

            }
        });
        addSubscribe(disposable);

    }

    public void getVoaDetail(String format, int voaid) {

        Disposable disposable = model.getVoaDetail(format, voaid, new HomeContract.TitleCallbackVoaDatail() {


            @Override
            public void successVoaDetail(VoaDetailBean voaDetailBean) {
                view.getVoaDetail(voaDetailBean);
            }


            @Override
            public void errorVoaDetail(Exception e) {

                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }

            }
        });
        //addSubscribe(disposable);

    }



    //可能有问题，后续看情况更改
    @Override
    protected HomeContract.HomeModel initModel() {
        return new HomeModel();
    }
}
