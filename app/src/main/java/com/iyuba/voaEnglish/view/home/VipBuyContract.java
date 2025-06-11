package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.VipBean;
import com.iyuba.voaEnglish.model.bean.VipParseBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import io.reactivex.disposables.Disposable;

public interface VipBuyContract {

    interface VipBuyView extends LoadingView {

        void getVip(VipParseBean vipParseBean);

        void callbackVip(VipBean vipBean);
    }




    interface VipBuyPresenter extends IBasePresenter<VipBuyView> {

        void getVip(int app_id, int userId, String code, String WIDtotal_fee, int amount, int product_id, String WIDbody, String WIDsubject);

        void callbackVip(String data);
    }


    interface VipBuyModel extends BaseModel {
        Disposable getVip(int app_id, int userId, String code, String WIDtotal_fee, int amount, int product_id, String WIDbody, String WIDsubject, VipCallBack vipCallBack);


        Disposable callbackVip(String data, VipNotifyCallBack vipNotifyCallBack);
    }



    interface VipCallBack {

        void vipSuccess(VipParseBean vipParseBean);

        void vipError(Exception e);
    }

    interface VipNotifyCallBack {

        void vipNotifySuccess(VipBean vipBean);

        void vipNotifyError(Exception e);
    }
}
