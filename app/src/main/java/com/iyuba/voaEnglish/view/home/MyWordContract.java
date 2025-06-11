package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.MyWordBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface MyWordContract {

    interface MyWordView extends LoadingView{
        void getMyWord(MyWordBean myWordBean);
    }

    interface MyWordPresenter extends IBasePresenter<MyWordView>{
        void getMyWord(int u, int pageNumber, int pageCounts, String format);
    }

    interface MyWordModel extends BaseModel{
        Disposable getMyWord(int u, int pageNumber, int pageCounts, String format,MyWordCallBack myWordCallBack);
    }

    interface MyWordCallBack{

        void myWordSuccess(MyWordBean myWordBean);

        void myWordError(Exception e);
    }
}
