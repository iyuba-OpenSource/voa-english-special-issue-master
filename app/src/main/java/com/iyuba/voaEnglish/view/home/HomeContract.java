package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface HomeContract {

    interface HomeView extends LoadingView {


        //定义请求返回函数
        void getVoaTitle(VoaBean voaBean, int pages);

        void getVoaDetail(VoaDetailBean voaDetailBean);


    }


    interface HomePresenter extends IBasePresenter<HomeView> {

        //定义函数传递参数，便于更改变量值
        void getVoaTitle(String type, String format, int appId, int maxid, int pages, int pageNum, int parentId);

        void getVoaDetail(String format, int voaid);

    }



    interface HomeModel extends BaseModel {

        Disposable getVoaTitle(String type, String format, int appId, int maxid, int pages, int pageNum, int parentId, TitleCallbackVoa titleCallbackVoa);

        Disposable getVoaDetail(String format, int voaid, TitleCallbackVoaDatail titleCallbackVoaDatail);


    }


    //回调函数
    interface TitleCallbackVoa {


        void successVoa(VoaBean voaBean);

        void errorVoa(Exception e);

    }

    interface TitleCallbackVoaDatail {

        //void successVoa(TitleBean titleBean);

        void successVoaDetail(VoaDetailBean voaDetailBean);

        void errorVoaDetail(Exception e);

    }


}
