package com.iyuba.voaEnglish.view.home;

import com.iyuba.voaEnglish.model.BaseModel;
import com.iyuba.voaEnglish.model.bean.HotWordBean;
import com.iyuba.voaEnglish.model.bean.HotWordSearchBean;
import com.iyuba.voaEnglish.model.bean.WordSearchBean;
import com.iyuba.voaEnglish.presenter.IBasePresenter;
import com.iyuba.voaEnglish.view.LoadingView;

import java.io.UnsupportedEncodingException;

import io.reactivex.disposables.Disposable;

public interface SearchContract {

    interface SearchView extends LoadingView {
        void getHotWord(HotWordBean hotWordBean);

        void getHotSearch(HotWordSearchBean hotWordSearchBean);

        void getWordSearch(WordSearchBean wordSearchBean) throws UnsupportedEncodingException;
    }


    interface SearchPresenter extends IBasePresenter<SearchView> {

        void getHotWord(String newstype);

        void getHotSearch(String newstype, String keyword, int userid, int appid);

        void getWordSearch(String format, String key, int pages, int pageNum, int parentID, String type, int flg, int userid, int appid);

    }

    interface SearchModel extends BaseModel {

        Disposable getHotWord(String newstype, HotWordCallBack hotWordCallBack);

        Disposable getHotSearch(String newstype, String keyword, int userid, int appid, HotSearchCallBack hotSearchCallBack);

        Disposable getWordSearch(String format, String key, int pages, int pageNum, int parentID, String type, int flg, int userid, int appid, WordSearchCallBack wordSearchCallBack);
    }


    interface HotWordCallBack{
        void hotWordSuccess(HotWordBean hotWordBean);

        void hotWordError(Exception e);
    }

    interface HotSearchCallBack{

        void hotSearchSuccess(HotWordSearchBean hotWordSearchBean);

        void hotSearchError(Exception e);
    }

    interface WordSearchCallBack{

        void wordSearchSuccess(WordSearchBean wordSearchBean) throws UnsupportedEncodingException;

        void wordSearchError(Exception e);
    }
}
