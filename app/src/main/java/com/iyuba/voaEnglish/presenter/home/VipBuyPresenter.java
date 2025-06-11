package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.VipBean;
import com.iyuba.voaEnglish.model.bean.VipParseBean;
import com.iyuba.voaEnglish.model.home.VipBuyModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.VipBuyContract;

import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class VipBuyPresenter extends BasePresenter<VipBuyContract.VipBuyView, VipBuyContract.VipBuyModel> implements VipBuyContract.VipBuyPresenter {

    @Override
    public void getVip(int app_id, int userId, String code, String WIDtotal_fee, int amount, int product_id, String WIDbody, String WIDsubject) {

        Disposable disposable = model.getVip(app_id,userId,code,WIDtotal_fee,amount,product_id,WIDbody,WIDsubject, new VipBuyContract.VipCallBack(){
            @Override
            public void vipSuccess(VipParseBean vipParseBean) {
                view.getVip(vipParseBean);
            }

            @Override
            public void vipError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void callbackVip(String data) {

        Disposable disposable = model.callbackVip(data, new VipBuyContract.VipNotifyCallBack() {
            @Override
            public void vipNotifySuccess(VipBean vipBean) {
                view.callbackVip(vipBean);
            }

            @Override
            public void vipNotifyError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });

    }


    @Override
    protected VipBuyContract.VipBuyModel initModel() {
        return new VipBuyModel();
    }

}
