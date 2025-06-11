package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.GetWordsBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface GetWordsContract {

    interface GetWordsView extends LoadingView{
        void getWordsMes(GetWordsBean getWordsBean);
    }

    interface GetWordsPresenter extends IBasePresenter<GetWordsView>{
        void getWordsMes(String q, String format);
    }

    interface GetWordsModel extends BaseModel{
        Disposable getWordsMes(String q, String format, GetWordsCallBack getWordsCallBack);
    }

    interface GetWordsCallBack{

        void getWordsSuccess(GetWordsBean getWordsBean);

        void getWordsError(Exception e);
    }
}
