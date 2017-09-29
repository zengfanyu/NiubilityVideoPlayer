package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.AppManager;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Result;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.ResultAlbum;

import java.io.IOException;

import okhttp3.Response;

import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_SOHU.API_CHANNEL_ALBUM_FORMAT;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_SOHU.SOHU_CHANNELID_COMIC;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_SOHU.SOHU_CHANNELID_DOCUMENTRY;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_SOHU.SOHU_CHANNELID_MOVIE;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_SOHU.SOHU_CHANNELID_MUSIC;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_SOHU.SOHU_CHANNELID_SERIES;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_SOHU.SOHU_CHANNELID_VARIETY;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class SohuApi extends BaseSiteAPI {

    private static final String TAG = "SohuApi";

    @Override
    protected String getAlbumUrl(ChannelMode channelMode, int pageNo, int pageSize) {
        return String.format(API_CHANNEL_ALBUM_FORMAT, toConvertChannelId(channelMode), pageNo, pageSize);
    }

    @Override
    protected void parseAndMappingDataFromResponse(Response response, onGetChannelAlbumListener listener) {
        //取到数据映射ResultAlbum
        try {
            Result result = null;
            result = AppManager.getGson().fromJson(response.body().string(), Result.class);

            //转换ResultAlbum成Album ,Album存到AlbumList中
            AlbumList albumList = toConcertAlbumList(result);

            if (albumList != null && albumList.size() > 0 && listener != null) {
                listener.onGetChannelAlbumSuccess(albumList);
            } else {
                if (listener != null) {
                    ErrorInfo errorInfo = new ErrorInfo.Builder()
                            .setFunctionName("parseAndMappingDataFromResponse")
                            .setType(ErrorInfo.ERROR_TYPE_JSON_PARSE)
                            .setSiteId(SiteMode.SOHU)
                            .setClassName(TAG)
                            .build();
                    listener.onGetChannelAlbumFailed(errorInfo);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将从服务器获取的数据模型Result转换成需要的AlbumList
     *
     * @param result
     * @return
     */
    private AlbumList toConcertAlbumList(Result result) {
        if (result.getData().getResultAlbumList().size() > 0) {
            AlbumList albumList = new AlbumList();
            for (ResultAlbum resultAlbum : result.getData().getResultAlbumList()) {
                Album album = new Album(SiteMode.SOHU);
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

    /**
     * 自定义频道id与真实频道id的转换
     *
     * @param channelMode
     * @return
     */
    private int toConvertChannelId(ChannelMode channelMode) {
        int channelId = -1;//-1 无效值
        switch (channelMode.getChannelId()) {
            case ChannelMode.SERIES:
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
