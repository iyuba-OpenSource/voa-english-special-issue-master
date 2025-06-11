package com.iyuba.voaEnglish.presenter.home;




import com.iyuba.voaEnglish.model.bean.MyMoneyBean;
import com.iyuba.voaEnglish.model.home.MyMoneyModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.MyMoneyContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class MyMoneyPresenter extends BasePresenter<MyMoneyContract.MyMoneyView, MyMoneyContract.MyMoneyModel> implements MyMoneyContract.MyMoneyPresenter {
    @Override
    protected MyMoneyContract.MyMoneyModel initModel() {
        return new MyMoneyModel();
    }

    @Override
    public void getMyMoney(String uid, int pages, int pageCount, String sign) {
        Disposable disposable = model.getMyMoney(uid, pages, pageCount, sign, new MyMoneyContract.CallBackMyMoney() {
            @Override
            public void successMyMoney(MyMoneyBean myMoneyBean) {
                view.getMyMoney(myMoneyBean);
            }

            @Override
            public void errorMyMoney(Exception e) {
                if (e instanceof UnknownHostException) {

                    view.toast("请求超时");
                }
            }
        });
        addSubscribe(disposable);
    }
}
