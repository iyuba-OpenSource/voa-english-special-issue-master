package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.ComposeBean;
import com.iyuba.voaEnglish.model.bean.EvaBean;
import com.iyuba.voaEnglish.model.bean.UploadBean;
import com.iyuba.voaEnglish.model.home.EvaModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.EvaContract;

import java.io.IOException;
import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;

public class EvaPresenter extends BasePresenter<EvaContract.EvaView, EvaContract.EvaModel> implements EvaContract.EvaPresenter {


    @Override
    public void getEvaData(RequestBody requestBody) {
        Disposable disposable = model.getEvaData(requestBody, new EvaContract.CallBackEva() {
            @Override
            public void successEva(EvaBean evaBean) throws IOException {
                view.getEvaData(evaBean);
            }

            @Override
            public void errorEva(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }

        });
        addSubscribe(disposable);
    }

    @Override
    public void uploadList(String platform, String format, int protocol, String topic, int uid, String username, int voaid, String idIndex, String paraId, int score, int shuoshuotype, String content,int rewardVersion,int appid) {
        Disposable disposable = model.uploadList(platform, format, protocol, topic, uid, username, voaid, idIndex, paraId, score, shuoshuotype, content, rewardVersion, appid, new EvaContract.UploadCallBack() {
            @Override
            public void uploadSuccess(UploadBean uploadBean) {
                view.uploadList(uploadBean);
            }

            @Override
            public void uploadError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void composeUp(String audios, String type) {
        Disposable disposable = model.composeUp(audios, type, new EvaContract.ComposeCallback() {
            @Override
            public void successCompose(ComposeBean composeBean) {
                view.composeUp(composeBean);
            }

            @Override
            public void errorCompose(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    protected EvaContract.EvaModel initModel() {
        return new EvaModel();
    }
}
