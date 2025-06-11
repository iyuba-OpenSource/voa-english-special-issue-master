package com.iyuba.voaEnglish.view.home;




import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.MyMoneyBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface MyMoneyContract {

    interface MyMoneyView extends LoadingView {
        void getMyMoney(MyMoneyBean myMoneyBean);
    }

    interface MyMoneyPresenter extends IBasePresenter<MyMoneyView> {
        void getMyMoney(String uid, int pages,  int pageCount,  String sign);

    }
    interface MyMoneyModel extends BaseModel {

        Disposable getMyMoney(String uid, int pages, int pageCount, String sign, CallBackMyMoney callBackMyMoney);
    }

    interface CallBackMyMoney{

        void successMyMoney(MyMoneyBean myMoneyBean);

        void errorMyMoney(Exception e);

    }
}
