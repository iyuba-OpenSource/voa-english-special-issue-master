package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.JoinWordBookBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface JoinWordBookContract {


    interface JoinWordBookView extends LoadingView {
        void joinWordBook(JoinWordBookBean joinWordBookBean);
    }

    interface JoinWordBookPresenter extends IBasePresenter<JoinWordBookView> {
        void joinWordBook(String groupName, int userId, String mod, String word, String format);

    }

    interface JoinWordBookModel extends BaseModel {
        Disposable joinWordBook(String groupName, int userId, String mod, String word, String format, JoinWordCallBack joinWordCallBack);
    }

    interface JoinWordCallBack{
        void joinWordSuccess(JoinWordBookBean joinWordBookBean);

        void joinWordError(Exception e);
    }
}
