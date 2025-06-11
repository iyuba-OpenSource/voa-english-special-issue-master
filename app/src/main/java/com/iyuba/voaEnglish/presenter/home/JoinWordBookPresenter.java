package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.JoinWordBookBean;
import com.iyuba.voaEnglish.model.home.JoinWordBookModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.JoinWordBookContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class JoinWordBookPresenter extends BasePresenter<JoinWordBookContract.JoinWordBookView, JoinWordBookContract.JoinWordBookModel> implements JoinWordBookContract.JoinWordBookPresenter {

    @Override
    public void joinWordBook(String groupName, int userId, String mod, String word, String format) {
        Disposable disposable = model.joinWordBook(groupName, userId, mod, word, format, new JoinWordBookContract.JoinWordCallBack() {
            @Override
            public void joinWordSuccess(JoinWordBookBean joinWordBookBean) {
                view.joinWordBook(joinWordBookBean);
            }

            @Override
            public void joinWordError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }


    @Override
    protected JoinWordBookContract.JoinWordBookModel initModel() {
        return new JoinWordBookModel();
    }

}
