package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.VipBean;
import com.iyuba.voaEnglish.model.bean.VipParseBean;
import com.iyuba.voaEnglish.view.home.VipBuyContract;

import java.security.PublicKey;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VipBuyModel implements VipBuyContract.VipBuyModel {
    @Override
    public Disposable getVip(int app_id, int userId, String code, String WIDtotal_fee, int amount, int product_id, String WIDbody, String WIDsubject, VipBuyContract.VipCallBack vipCallBack) {
        return Network
                .getRequest()
                .getVip("http://vip.iyuba.cn/alipay.jsp",app_id,userId,code,WIDtotal_fee,amount,product_id,WIDbody,WIDsubject)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VipParseBean>() {
                    @Override
                    public void accept(VipParseBean vipParseBean) throws Exception {
                        vipCallBack.vipSuccess(vipParseBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        vipCallBack.vipError((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable callbackVip(String data, VipBuyContract.VipNotifyCallBack vipNotifyCallBack) {
        return Network
                .getRequest()
                .callbackVip("http://vip.iyuba.cn/notifyAliNew.jsp",data)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VipBean>() {
                    @Override
                    public void accept(VipBean vipBean) throws Exception {
                        vipNotifyCallBack.vipNotifySuccess(vipBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        vipNotifyCallBack.vipNotifyError((Exception) throwable);
                    }
                });
    }


}
