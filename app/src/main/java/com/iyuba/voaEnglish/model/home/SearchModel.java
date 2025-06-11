package com.iyuba.voaEnglish.model.home;

import com.iyuba.voaEnglish.model.Network;
import com.iyuba.voaEnglish.model.bean.HotWordBean;
import com.iyuba.voaEnglish.model.bean.HotWordSearchBean;
import com.iyuba.voaEnglish.model.bean.WordSearchBean;
import com.iyuba.voaEnglish.view.home.SearchContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchModel implements SearchContract.SearchModel {
    @Override
    public Disposable getHotWord(String newstype, SearchContract.HotWordCallBack hotWordCallBack) {
        return Network
                .getRequest()
                .getHotWord("http://apps.iyuba.cn/voa/recommend.jsp",newstype)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotWordBean>() {
                    @Override
                    public void accept(HotWordBean hotWordBean) throws Exception {
                        hotWordCallBack.hotWordSuccess(hotWordBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hotWordCallBack.hotWordError((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getHotSearch(String newstype, String keyword, int userid, int appid, SearchContract.HotSearchCallBack hotSearchCallBack) {
        return Network
                .getRequest()
                .getHotSearch("http://apps.iyuba.cn/voa/recommendByKeyword.jsp",newstype,keyword,userid,appid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotWordSearchBean>() {
                    @Override
                    public void accept(HotWordSearchBean hotWordSearchBean) throws Exception {
                        hotSearchCallBack.hotSearchSuccess(hotWordSearchBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        hotSearchCallBack.hotSearchError((Exception) throwable);
                    }
                });
    }

    @Override
    public Disposable getWordSearch(String format, String key, int pages, int pageNum, int parentID, String type, int flg, int userid, int appid, SearchContract.WordSearchCallBack wordSearchCallBack) {
        return Network
                .getRequest()
                .getWordSearch("http://apps.iyuba.cn/iyuba/searchApiNew_20200612.jsp",format,key,pages,pageNum,parentID,type,flg,userid,appid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WordSearchBean>() {
                    @Override
                    public void accept(WordSearchBean wordSearchBean) throws Exception {
                        wordSearchCallBack.wordSearchSuccess(wordSearchBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        wordSearchCallBack.wordSearchError((Exception) throwable);
                    }
                });
    }
}
