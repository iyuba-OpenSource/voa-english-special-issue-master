package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.ComposeBean;
import com.iyuba.voaEnglish.model.bean.EvaBean;
import com.iyuba.voaEnglish.model.bean.UploadBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import java.io.IOException;

import io.reactivex.disposables.Disposable;
import okhttp3.RequestBody;
import retrofit2.http.Query;

public interface EvaContract {


    interface EvaView extends LoadingView {

        void getEvaData(EvaBean evaBean) throws IOException;

        void uploadList(UploadBean uploadBean);

        void composeUp(ComposeBean composeBean);
    }

    interface EvaPresenter extends IBasePresenter<EvaView> {

        void getEvaData(RequestBody requestBody);

        void uploadList(String platform, String format, int protocol, String topic, int uid, String username, int voaid, String idIndex, String paraId, int score, int shuoshuotype, String content,int rewardVersion,int appid);

        void composeUp(String audios, String type);
    }

    interface EvaModel extends BaseModel {
        Disposable getEvaData(RequestBody requestBody, CallBackEva callBackEva);

        Disposable uploadList(String platform, String format, int protocol, String topic, int uid, String username, int voaid, String idIndex, String paraId, int score, int shuoshuotype, String content,int rewardVersion,int appid,  UploadCallBack uploadCallBack);

        Disposable composeUp(String audios, String type, ComposeCallback composeCallback);
    }


    interface CallBackEva {

        void successEva(EvaBean evaBean) throws IOException;

        void errorEva(Exception e);
    }

    interface UploadCallBack {
        void uploadSuccess(UploadBean uploadBean);

        void uploadError(Exception e);
    }

    interface ComposeCallback {

        void successCompose(ComposeBean composeBean);

        void errorCompose(Exception e);
    }
}
