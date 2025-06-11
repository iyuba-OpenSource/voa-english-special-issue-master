package com.iyuba.voaEnglish.model;

import com.iyuba.voaEnglish.model.bean.AppClockBean;
import com.iyuba.voaEnglish.model.bean.ClockMoneyBean;
import com.iyuba.voaEnglish.model.bean.ClockRecordBean;
import com.iyuba.voaEnglish.model.bean.CollectBean;
import com.iyuba.voaEnglish.model.bean.ComposeBean;
import com.iyuba.voaEnglish.model.bean.DetailPageBean;
import com.iyuba.voaEnglish.model.bean.EvaBean;
import com.iyuba.voaEnglish.model.bean.EvaHistoryBean;
import com.iyuba.voaEnglish.model.bean.GetWordsBean;
import com.iyuba.voaEnglish.model.bean.HearingBean;
import com.iyuba.voaEnglish.model.bean.HearingDetailBean;
import com.iyuba.voaEnglish.model.bean.HotWordBean;
import com.iyuba.voaEnglish.model.bean.HotWordSearchBean;
import com.iyuba.voaEnglish.model.bean.IncentiveVipBean;
import com.iyuba.voaEnglish.model.bean.IsRegisterBean;
import com.iyuba.voaEnglish.model.bean.JoinWordBookBean;
import com.iyuba.voaEnglish.model.bean.LearningTimeBean;
import com.iyuba.voaEnglish.model.bean.LogLoginBean;
import com.iyuba.voaEnglish.model.bean.MyCollectBean;
import com.iyuba.voaEnglish.model.bean.MyMoneyBean;
import com.iyuba.voaEnglish.model.bean.MyWordBean;
import com.iyuba.voaEnglish.model.bean.PdfBean;
import com.iyuba.voaEnglish.model.bean.RankingBean;
import com.iyuba.voaEnglish.model.bean.ReadBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterBean;
import com.iyuba.voaEnglish.model.bean.ReadReporterDetailBean;
import com.iyuba.voaEnglish.model.bean.RegisterBean;
import com.iyuba.voaEnglish.model.bean.SpokeBean;
import com.iyuba.voaEnglish.model.bean.SpokenDetailBean;
import com.iyuba.voaEnglish.model.bean.StudyRecordByTestModeBean;
import com.iyuba.voaEnglish.model.bean.UidBean;
import com.iyuba.voaEnglish.model.bean.UploadBean;
import com.iyuba.voaEnglish.model.bean.UserPwdLoginBean;
import com.iyuba.voaEnglish.model.bean.UserRankingDetailBean;
import com.iyuba.voaEnglish.model.bean.VipBean;
import com.iyuba.voaEnglish.model.bean.VipParseBean;
import com.iyuba.voaEnglish.model.bean.VoaBean;
import com.iyuba.voaEnglish.model.bean.VoaDetailBean;
import com.iyuba.voaEnglish.model.bean.WordSearchBean;
import com.iyuba.voaEnglish.model.bean.ad.AdSubmitBean;

import io.reactivex.Completable;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiServer {

    //请求voa主界面
    @GET("/iyuba/titleApi.jsp")
    Observable<VoaBean> getVoaTitle(@Query("type") String type, @Query("format") String format, @Query("appId") int appId, @Query("maxid") int maxid, @Query("pages") int pages, @Query("pageNum") int pageNum, @Query("parentID") int parentId);

    //获取voa详情界面
    @GET("/iyuba/textExamApi.jsp")
    Observable<VoaDetailBean> getVoaDetail(@Query("format") String format, @Query("voaid") int voaid);

    //获取voa文章
    @GET("/iyuba/titleOneApi.jsp")
    Observable<DetailPageBean> getDetailPic(@Query("type") String type, @Query("parentID") String format, @Query("voaId") int voaid);

    //获取猜你喜欢
    @GET("/iyuba/titleApi.jsp")
    Observable<VoaBean> getVoaLike(@Query("type") String type, @Query("format") String format, @Query("appId") int appId, @Query("maxid") int maxid, @Query("pages") int pages, @Query("parentID") int parentId);

    //账号密码登录
    @GET("/v2/api.iyuba")
    Observable<UserPwdLoginBean> getUserLogin(@Query("username") String username, @Query("password") String password,@Query("app") String app, @Query("token") String token,@Query("format") String format, @Query("appid") int appid, @Query("protocol") String protocol, @Query("sign") String sign);


    //账号密码登录2
    @GET("/v2/api.iyuba")
    Observable<UserPwdLoginBean> getUserLoginApp(@Query("username") String username, @Query("password") String password,@Query("app") String app, @Query("token") String token,@Query("format") String format, @Query("appid") int appid, @Query("protocol") String protocol, @Query("sign") String sign);

    //获取排行榜
    @GET("/ecollege/getTopicRanking.jsp")
    Observable<RankingBean> getRanking(@Query("uid") int uid, @Query("type") String type, @Query("total") int total, @Query("start") int start, @Query("topic") String topic, @Query("topicid") int topicid, @Query("sign") String sign);

    //评测接口
    @POST
    Observable<EvaBean> getEvaData(@Url String url, @Body RequestBody requestBody);

    //合成评测
    @GET()
    Observable<ComposeBean> composeUp(@Url String url,@Query("audios") String audios, @Query("type") String type);

    //上传排行榜
    @GET
    Observable<UploadBean> uploadList(@Url String url, @Query("platform") String platform, @Query("format") String format, @Query("protocol") int protocol, @Query("topic") String topic, @Query("userid") int uid, @Query("username") String username, @Query("voaid") int voaid, @Query("idIndex") String idIndex, @Query("paraid") String paraId, @Query("score") int score, @Query("shuoshuotype") int shuoshuotype, @Query("content") String content,@Query("rewardVersion") int rewardVersion,@Query("appid") int appid);


    //排行榜详情
    @GET("/voa/getWorksByUserId.jsp")
    Observable<UserRankingDetailBean> getUserRankingDetail(@Query("shuoshuoType") String shuoshuoType, @Query("topic") String topic, @Query("topicId") int topicId, @Query("uid") int uid, @Query("sign") String sign);


    //用户uid登录
    @GET("/v2/api.iyuba")
    Observable<UidBean> uidLogin(@Query("platform") String platform, @Query("format") String format, @Query("protocol") int protocol, @Query("id") int id, @Query("myid") int myid, @Query("appid") int appid, @Query("sign") String sign);

    //购买vip上传的参数
    @GET
    Observable<VipParseBean> getVip(@Url String url, @Query("app_id") int app_id, @Query("userId") int userId, @Query("code") String code, @Query("WIDtotal_fee") String WIDtotal_fee, @Query("amount") int amount, @Query("product_id") int product_id, @Query("WIDbody") String WIDbody, @Query("WIDsubject") String WIDsubject);

    //支付回调
    @GET
    Observable<VipBean> callbackVip(@Url String url, @Query("data") String data);


    //注销账户
    @POST
    Observable<LogLoginBean> logUser(@Url String url, @Query("protocol") int protocol, @Query("username") String userame, @Query("password") String password, @Query("format") String format,@Query("sign") String sign);

    //判断是否注册
    @GET("/v2/api.iyuba")
    Observable<IsRegisterBean> isRegister(@Query("format") String format, @Query("platform") String platform, @Query("protocol") int protocol,@Query("username") String username);

    //注册
    @GET("/v2/api.iyuba")
    Observable<RegisterBean> userRegister(@Query("protocol") int protocol, @Query("username") String username, @Query("password") String password, @Query("mobile") String mobile, @Query("app") String app, @Query("Platform") String Platform,@Query("format") String format, @Query("appid") int  appid, @Query("sign") String sign);


    //我的收藏
    @GET
    Observable<MyCollectBean>getMyCollect(@Url String url, @Query("userId") int userid, @Query("topic") String topic, @Query("appid") int appid, @Query("sentenceFlg") int sentenceFlg,@Query("format") String format, @Query("sign") String sign);

    //收藏，取消收藏
    @GET
    Observable<CollectBean>getCollect(@Url String url, @Query("groupName") String groupName, @Query("sentenceFlg") int sentenceFlg, @Query("appId") int appId, @Query("userId") int userid, @Query("type") String type, @Query("voaId") int voaId, @Query("sentenceId") int sentenceId, @Query("topic") String topic, @Query("format") String format);

    //PDF下载
    @GET("/getPdfFile_new.jsp")
    Observable<PdfBean> getPdf(@Query("idtype") String idtype, @Query("id") int id, @Query("isenglish") int isenglish);

    //评测历史记录
    @GET
    Observable<EvaHistoryBean> getEvaHistory(@Url String url,@Query("userId") int userId, @Query("newstype") String newstype, @Query("newsid") int newsid);

    //上传听力进度
    @GET("/ecollege/updateStudyRecordNew.jsp")
    Observable<StudyRecordByTestModeBean> UpStudyRecordContract(@Query("format") String format, @Query("uid") String uid, @Query("appId") int appId, @Query("BeginTime") String BeginTime
            , @Query("EndTime") String EndTime, @Query("Lesson") String Lesson, @Query("LessonId") String LessonId, @Query("EndFlg") String EndFlg, @Query("Device") String Device, @Query("platform") String platform
            , @Query("IP") String IP, @Query("sign") String sign, @Query("TestMode") String TestMode, @Query("TestNumber") String TestNumber, @Query("TestWords") String TestWords, @Query("rewardVersion") int rewardVersion);

    //获取单词
    @GET("/words/apiWordJson.jsp")
    Observable<GetWordsBean> getWordsMes(@Query("q") String q, @Query("format") String format);

    //加入生词本
    @GET("/words/updateWord.jsp")
    Observable<JoinWordBookBean> joinWordBook(@Query("groupName") String groupName,@Query("userId") int userId, @Query("mod") String mod, @Query("word") String word, @Query("format") String format);

    //我的生词列表
    @GET("/words/wordListService.jsp")
    Observable<MyWordBean> getMyWord(@Query("u") int u,@Query("pageNumber")  int pageNumber, @Query("pageCounts") int pageCounts,@Query("format") String format);


    //学习时间
    @GET("/ecollege/getMyTime.jsp")
    Observable<LearningTimeBean> getLearningTime(@Query("uid") int uid,@Query("day") long day, @Query("flg") int flg);


    //我的打卡,小程序用法，此程序未使用
    @GET("/dev/getClockInData.jsp")
    Observable<ClockRecordBean> getClockRecord(@Query("fid") int fid,@Query("uid") int uid, @Query("month") String month);

    //app打卡
    @GET
    Observable<AppClockBean> getAppClock(@Url String url,@Query("uid") int uid, @Query("appId") int appId, @Query("time") String time);


    //打卡成功回调
    @GET
    Observable<ClockMoneyBean> clockAddMoney(@Url String url, @Query("srid") int srid, @Query("mobile") int mobile, @Query("flag") String flag,@Query("uid") int uid, @Query("appid") int appid);


    //热词显示
    @GET
    Observable<HotWordBean> getHotWord(@Url String url ,@Query("newstype") String newstype);

    //热词搜索
    @GET
    Observable<HotWordSearchBean> getHotSearch(@Url String url,@Query("newstype") String newstype,@Query("keyword") String keyword,@Query("userid") int userid, @Query("appid") int appid);

    //单词搜索
    @GET
    Observable<WordSearchBean> getWordSearch(@Url String url,@Query("format") String format,@Query("key") String key,@Query("pages") int pages, @Query("pageNum") int pageNum, @Query("parentID") int parentID,@Query("type") String type,@Query("flg") int flg, @Query("userid") int userid, @Query("appid") int appid);


    //原文阅读 提交进度的接口  http://daxue.iyuba.cn/ecollege/updateNewsStudyRecord.jsp?format=json&uid=14044990&BeginTime=2023-08-15 09:34:53&EndTime=2023-08-15 09:36:53&appName=爱语吧TED演讲&Lesson=ted&LessonId=202499&appId=229&Device=android&DeviceId=02:00:00:00:00:00&EndFlg=1&wordcount=500&categoryid=0&platform=web&rewardVersion=1
    @GET
    Observable<ReadBean> getRead(@Url String url, @Query("format") String format, @Query("uid") String uid, @Query("BeginTime") String BeginTime, @Query("EndTime") String EndTime, @Query("appName") String appName, @Query("Lesson") String Lesson, @Query("LessonId") String LessonId, @Query("appId") int appId, @Query("Device") String Device
            , @Query("DeviceId") String DeviceId, @Query("EndFlg") int EndFlg, @Query("wordcount") int wordcount, @Query("categoryid") int categoryid, @Query("platform") String platform, @Query("rewardVersion") int rewardVersion);

    //学习报告 --阅读 http://cms.iyuba.cn/newsApi/getNewsRanking.jsp?uid=14550251&type=D&total=1&start=0&mode=reading&sign=e2a8232049843094f796ef8bcdc7e2c5
    @GET
    Observable<ReadReporterBean> getReadReporter(@Url String url, @Query("uid") String uid, @Query("type") String type, @Query("total") int total, @Query("start") int start, @Query("mode") String mode, @Query("sign") String sign);

    //学校报告 --阅读详情  http://cms.iyuba.cn/newsApi/getRecentRV.jsp?uid=9030248&format=json&thisNumber=20&startNumber=0
    @GET
    Observable<ReadReporterDetailBean> getReadReporterDetail(@Url String url, @Query("uid") String uid, @Query("format") String format, @Query("thisNumber") int thisNumber, @Query("startNumber") int startNumber);

    //听力报告接口http://daxue.iyuba.cn/ecollege/getStudyRanking.jsp?mode=listening&uid=14044990&type="D"&start=0&total=0&sign=e5d99657930e879053ed98ea98dc622a
    @GET
    Observable<HearingBean> getHearing(@Url String url, @Query("mode") String mode, @Query("uid") String uid, @Query("type") String type, @Query("start") int start, @Query("total") int total, @Query("sign") String sign);

    //口语报告http://ai.iyuba.cn/management/getHomePage.jsp?userId=6307010&newstype=concept&language=English&type=D&sign=e294a41f7a1db3e06a682264f8a54a0c
    @GET
    Observable<SpokeBean> getSpoke(@Url String url, @Query("userId") String userId, @Query("newstype") String newstype, @Query("language") String language, @Query("type") String type, @Query("sign") String sign);

    //听力报告详情 http://daxue.iyuba.cn/ecollege/getStudyRecordByTestMode.jsp?NumPerPage=10&Pageth=1&TestMode=1&uid=14044990&sign=c6f4b185ab5d148b2bfc83920fe70f50
    @GET
    Observable<HearingDetailBean> getHearingDetail(@Url String url, @Query("NumPerPage") int NumPerPage, @Query("Pageth") int Pageth, @Query("TestMode") int TestMode, @Query("uid") String uid, @Query("sign") String sign);

    //口语报告详情http://ai.iyuba.cn/management/getDetailInfo.jsp?userId=6307010&newstype=concept&language=English&lastId=0&pageCounts=20
    @GET
    Observable<SpokenDetailBean> getSpokenDetail(@Url String url, @Query("userId") String userId, @Query("newstype") String newstype, @Query("language") String language, @Query("lastId") int lastId, @Query("pageCounts")int pageCounts);


    //钱包记录 http://api.iyuba.cn/credits/getuseractionrecord.jsp?uid=6307010&pages=1&pageCount=20&sign=0fd32b5d167482f0cc3561b2abc70738
    @GET
    Observable<MyMoneyBean> getMyMoney(@Url String url, @Query("uid") String uid, @Query("pages") int pages, @Query("pageCount") int pageCount, @Query("sign") String sign);


    /**
     * 获取看视频的结果
     *
     * @param url
     * @param uid
     * @param appid
     * @param timeStr
     * @param sign
     * @return
     */
    @GET
    Observable<IncentiveVipBean> openIncentiveVip(@Url String url, @Query("uid") String uid, @Query("appid") String appid
            , @Query("timeStr") String timeStr, @Query("sign") String sign);

    /**
     * 广告数据提交
     *
     * @param url
     * @param date_time
     * @param appid
     * @param device
     * @param deviceid
     * @param uid
     * @param packageStr
     * @param os
     * @param ads
     * @return
     */
    @POST
    Observable<AdSubmitBean> addAdInfo(@Url String url,
                                       @Query("date_time") String date_time,
                                       @Query("appid") String appid,
                                       @Query("device") String device,
                                       @Query("deviceid") String deviceid,
                                       @Query("uid") String uid,
                                       @Query("package") String packageStr,
                                       @Query("os") String os,
                                       @Query("ads") String ads);

}
