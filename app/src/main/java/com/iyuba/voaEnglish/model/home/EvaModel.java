package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.ComposeBean;
import com.iyuba.voaEnglish.model.bean.EvaBean;
import com.iyuba.voaEnglish.model.bean.UploadBean;
import com.iyuba.voaEnglish.model.networkCertificates.Constant;
import com.iyuba.voaEnglish.view.home.EvaContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

public class EvaModel implements EvaContract.EvaModel {
    @Override
    public Disposable getEvaData(RequestBody requestBody, EvaContract.CallBackEva callBackEva) {
        return Network
                .getRequest()
                .getEvaData("http://iuserspeech.iyuba.cn:9001/test/eval/",requestBody)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<EvaBean>() {
                    @Override
                    public void accept(EvaBean evaBean) throws Exception {

                        callBackEva.successEva(evaBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        callBackEva.errorEva((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable uploadList(String platform, String format, int protocol, String topic, int uid, String username, int voaid, String idIndex, String  paraId, int score, int shuoshuotype, String content,int rewardVersion,int appid, EvaContract.UploadCallBack uploadCallBack) {
        return Network
                .getRequest()
                .uploadList("https://apps.iyuba.cn/voa/UnicomApi",platform, format, protocol, topic, uid, username, voaid, idIndex, paraId, score, shuoshuotype, content, rewardVersion, appid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UploadBean>() {
                    @Override
                    public void accept(UploadBean uploadBean) throws Exception {
                        uploadCallBack.uploadSuccess(uploadBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        uploadCallBack.uploadError((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable composeUp(String audios, String type, EvaContract.ComposeCallback composeCallback) {
        return Network
                .getRequest()
                .composeUp("https://ai.iyuba.cn/test/merge",audios, type)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ComposeBean>() {
                    @Override
                    public void accept(ComposeBean composeBean) throws Exception {
                        composeCallback.successCompose(composeBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        composeCallback.errorCompose((Exception) throwable);
                    }
                });
    }
}
