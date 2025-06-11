package com.iyuba.voaEnglish.presenter.ad;




import com.iyuba.voaEnglish.MyApplication;
import com.iyuba.voaEnglish.dao.AdLogDao;
import com.iyuba.voaEnglish.db.AdLog;
import com.iyuba.voaEnglish.db.AdLogDatabase;
import com.iyuba.voaEnglish.model.ad.AdListModel;
import com.iyuba.voaEnglish.model.bean.ad.AdSubmitBean;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.ad.AdContract;

import java.util.List;

import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class AdListPresenter extends BasePresenter<AdContract.AdView, AdContract.AdModel>
        implements AdContract.AdPresenter {
    @Override
    protected AdContract.AdModel initModel() {
        return new AdListModel();
    }


    @Override
    public void addAdInfo(String url, String date_time, String appid, String device,
                          String deviceid, String uid, String packageStr, String os, String ads,
                          List<AdLog> adLogList) {

        Disposable disposable = model.addAdInfo(url, date_time, appid, device, deviceid, uid, packageStr, os, ads
                , new AdContract.Callback() {
                    @Override
                    public void success(AdSubmitBean adSubmitBean) {

                        if (adSubmitBean.getResult() == 200) {

                            AdLogDatabase adLogDatabase = AdLogDatabase.getInstence(MyApplication.getContext());
                            AdLogDao adLogDao = adLogDatabase.getAdLogDao();

                            for (AdLog adLog : adLogList) {

                                adLog.setUpload(1);
                                adLogDao.update(adLog);
                            }
                        }
                        Timber.d("success" + adSubmitBean.getMessage());
                    }

                    @Override
                    public void error(Exception e) {

                        Timber.d("addAdInfo" + e.getMessage());
                    }
                });
    }
}
