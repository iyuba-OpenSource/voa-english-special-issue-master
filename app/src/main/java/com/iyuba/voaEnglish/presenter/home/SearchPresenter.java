package com.iyuba.voaEnglish.presenter.home;

import com.iyuba.voaEnglish.model.bean.HotWordBean;
import com.iyuba.voaEnglish.model.bean.HotWordSearchBean;
import com.iyuba.voaEnglish.model.bean.WordSearchBean;
import com.iyuba.voaEnglish.model.home.SearchModel;
import com.iyuba.voaEnglish.presenter.BasePresenter;
import com.iyuba.voaEnglish.view.home.SearchContract;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import io.reactivex.disposables.Disposable;

public class SearchPresenter extends BasePresenter<SearchContract.SearchView, SearchContract.SearchModel> implements SearchContract.SearchPresenter {


    @Override
    public void getHotWord(String newstype) {
        Disposable disposable = model.getHotWord(newstype, new SearchContract.HotWordCallBack() {
            @Override
            public void hotWordSuccess(HotWordBean hotWordBean) {
                view.getHotWord(hotWordBean);
            }

            @Override
            public void hotWordError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });
        addSubscribe(disposable);
    }

    @Override
    public void getHotSearch(String newstype, String keyword, int userid, int appid) {
        Disposable disposable = model.getHotSearch(newstype, keyword, userid, appid, new SearchContract.HotSearchCallBack() {
            @Override
            public void hotSearchSuccess(HotWordSearchBean hotWordSearchBean) {
                view.getHotSearch(hotWordSearchBean);
            }

            @Override
            public void hotSearchError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });

        addSubscribe(disposable);
    }

    @Override
    public void getWordSearch(String format, String key, int pages, int pageNum, int parentID, String type, int flg, int userid, int appid) {
        Disposable disposable = model.getWordSearch(format, key, pages, pageNum, parentID, type, flg, userid, appid, new SearchContract.WordSearchCallBack() {
            @Override
            public void wordSearchSuccess(WordSearchBean wordSearchBean) throws UnsupportedEncodingException {
                view.getWordSearch(wordSearchBean);
            }

            @Override
            public void wordSearchError(Exception e) {
                if (e instanceof UnknownHostException) {
                    view.toast("请求时间");
                }
            }
        });

        addSubscribe(disposable);
    }

    @Override
    protected SearchContract.SearchModel initModel() {
        return new SearchModel();
    }
}
