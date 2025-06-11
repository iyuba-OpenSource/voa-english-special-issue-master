package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.JoinWordBookBean;
import com.iyuba.voaEnglish.view.home.JoinWordBookContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class JoinWordBookModel implements JoinWordBookContract.JoinWordBookModel {
    @Override
    public Disposable joinWordBook(String groupName, int userId, String mod, String word, String format, JoinWordBookContract.JoinWordCallBack joinWordCallBack) {
        return Network
                .getRequest()
                .joinWordBook(groupName,userId,mod,word,format)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<JoinWordBookBean>() {
                    @Override
                    public void accept(JoinWordBookBean joinWordBookBean) throws Exception {
                        joinWordCallBack.joinWordSuccess(joinWordBookBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        joinWordCallBack.joinWordError((Exception) throwable);
                    }
                });
    }
}
