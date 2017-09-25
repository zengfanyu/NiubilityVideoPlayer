package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.AppManager;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Result;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.ResultAlbum;
import com.project.fanyuzeng.niubilityvideoplayer.utils.okHttpUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class SohuApi extends BaseSiteAPI {
    private static final String TAG = SohuApi.class.getSimpleName();
    private static final int SOHU_CHANNELID_MOVIE = 1; //搜狐电影频道ID
    private static final int SOHU_CHANNELID_SERIES = 2; //搜狐电视剧频道ID
    private static final int SOHU_CHANNELID_VARIETY = 7; //搜狐综艺频道ID
    private static final int SOHU_CHANNELID_DOCUMENTRY = 8; //搜狐纪录片频道ID
    private static final int SOHU_CHANNELID_COMIC = 16; //搜狐动漫频道ID
    private static final int SOHU_CHANNELID_MUSIC = 24; //搜狐音乐频道ID

    //某一专辑详情
    //http://api.tv.sohu.com/v4/album/info/9112373.json?plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
    private final static String API_KEY = "plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47";
    private final static String API_ALBUM_INFO = "http://api.tv.sohu.com/v4/album/info/";
    //http://api.tv.sohu.com/v4/search/channel.json?cid=2&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47&page=1&page_size=1
    private final static String API_CHANNEL_ALBUM_FORMAT = "http://api.tv.sohu.com/v4/search/channel.json" +
            "?cid=%s&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&" +
            "sver=6.2.0&sysver=4.4.2&partner=47&page=%s&page_size=%s";
    //http://api.tv.sohu.com/v4/album/videos/9112373.json?page=1&page_size=50&order=0&site=1&with_trailer=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
    private final static String API_ALBUM_VIDOES_FORMAT = "http://api.tv.sohu.com/v4/album/videos/%s.json?page=%s&page_size=%s&order=0&site=1&with_trailer=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47";
    // 播放url
    //http://api.tv.sohu.com/v4/video/info/3669315.json?site=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=4.5.1&sysver=4.4.2&partner=47&aid=9112373
    private final static String API_VIDEO_PLAY_URL_FORMAT = "http://api.tv.sohu.com/v4/video/info/%s.json?site=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=4.5.1&sysver=4.4.2&partner=47&aid=%s";
    //真实url格式 m3u8
    //http://hot.vrs.sohu.com/ipad3669271_4603585256668_6870592.m3u8?plat=6uid=f5dbc7b40dad477c8516885f6c681c01&pt=5&prod=app&pg=1

    @Override
    public void onGetChannelAlbums(ChannelMode channelMode, int pageNo, int pageSize, onGetChannelAlbumListener channelAlbumListener) {
        String url = getChannelAlbumUrl(channelMode, pageNo, pageSize);
        doGetChannelAlbumsByUrl(url, channelAlbumListener);
    }

    private void doGetChannelAlbumsByUrl(final String url, final onGetChannelAlbumListener channelAlbumListener) {
        okHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (channelAlbumListener != null) {
                    ErrorInfo errorInfo = buildErrorInfo(url, "doGetChannelAlbumsByUrl", e, ErrorInfo.ERROR_TYPE_URL);
                    channelAlbumListener.onGetChannelAlbumFailed(errorInfo);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo errorInfo = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_HTTP);
                    channelAlbumListener.onGetChannelAlbumFailed(errorInfo);
                    return;
                }

                //取到数据映射ResultAlbum
                Result result = AppManager.getGson().fromJson(response.body().string(), Result.class);
                //转换ResultAlbum成Album ,Album存到AlbumList中
                AlbumList albumList = toConcertAlbumList(result);
                //
                if (albumList != null && albumList.size() > 0 && channelAlbumListener != null) {
                    channelAlbumListener.onGetChannelAlbumSuccess(albumList);
                }else {
                    ErrorInfo errorInfo = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_DATA_CONVERT);
                    if (channelAlbumListener!=null) {
                        channelAlbumListener.onGetChannelAlbumFailed(errorInfo);
                    }
                }
            }
        });
    }

    private AlbumList toConcertAlbumList(Result result) {

        if (result.getData().getResultAlbumList().size() > 0) {
            AlbumList albumList = new AlbumList();
            for (ResultAlbum resultAlbum : result.getData().getResultAlbumList()) {
                Album album = new Album(SiteMode.SOHU, AppManager.getContext());
                album.setAlbumDesc(resultAlbum.getTvDesc());
                album.setAlbumId(resultAlbum.getAlbumId());
                album.setHorImgUrl(resultAlbum.getHorHighPic());
                album.setMainActor(resultAlbum.getMainActor());
                album.setTip(resultAlbum.getTip());
                album.setTitle(resultAlbum.getAlbumName());
                album.setVerImgUrl(resultAlbum.getVerHighPic());
                album.setDirector(resultAlbum.getDirector());
                albumList.add(album);

            }
            return albumList;
        }
        return null;
    }

    private ErrorInfo buildErrorInfo(String url, String functionName, Exception e, int type) {
        ErrorInfo info = new ErrorInfo(SiteMode.SOHU, type);
        info.setExceptionString(e.getMessage());
        info.setFunctionName(functionName);
        info.setUrl(url);
        info.setTag(TAG);
        info.setClassName(TAG);
        return info;
    }

    private String getChannelAlbumUrl(ChannelMode channelMode, int pageNo, int pageSize) {
        //格式化url
        return String.format(API_CHANNEL_ALBUM_FORMAT, toConvertChannelId(channelMode), pageNo, pageSize);
    }

    //自定义平道id与真实pindaoid的转换
    private int toConvertChannelId(ChannelMode channelMode) {
        int channelId = -1;//-1 无效值
        switch (channelMode.getChannelId()) {
            case ChannelMode.SHOW:
                channelId = SOHU_CHANNELID_SERIES;
                break;
            case ChannelMode.MOVIE:
                channelId = SOHU_CHANNELID_MOVIE;
                break;
            case ChannelMode.COMIC:
                channelId = SOHU_CHANNELID_COMIC;
                break;
            case ChannelMode.MUSIC:
                channelId = SOHU_CHANNELID_MUSIC;
                break;
            case ChannelMode.DOCUMENTRY:
                channelId = SOHU_CHANNELID_DOCUMENTRY;
                break;
            case ChannelMode.VARIETY:
                channelId = SOHU_CHANNELID_VARIETY;
                break;
        }
        return channelId;
    }
}
