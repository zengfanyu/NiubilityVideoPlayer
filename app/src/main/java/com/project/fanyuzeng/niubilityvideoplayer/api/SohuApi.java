package com.project.fanyuzeng.niubilityvideoplayer.api;

import android.text.TextUtils;
import android.util.Log;

import com.project.fanyuzeng.niubilityvideoplayer.AppManager;
import com.project.fanyuzeng.niubilityvideoplayer.Constants;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.DetailResult;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Result;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.ResultAlbum;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.VideoList;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.VideoResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

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

    @Override
    protected String getAlbumDetailUrl(Album album) {
        return Constants.API_SOHU.API_ALBUM_INFO + album.getAlbumId() + ".json?" + Constants.API_SOHU.API_KEY;
    }

    @Override
    protected void parseAndMappingAlbumDetailDataFromResponse(Album album, Response response, onGetAlbumDetailListener listener) {
        try {
            DetailResult result = AppManager.getGson().fromJson(response.body().string(), DetailResult.class);
            if (result.getResultAlbum() != null) {
                if (result.getResultAlbum().getLastVideoCount() > 0) {
                    album.setVideoTotle(result.getResultAlbum().getLastVideoCount());
                } else {
                    album.setVideoTotle(result.getResultAlbum().getTotalVideoCount());
                }
            }
            if (listener != null)
                listener.onGetAlbumDetailsSuccess(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getAlbumVideoUrl(Album album, int pageNo, int pageSize) {

        return String.format(Constants.API_SOHU.API_ALBUM_VIDOES_FORMAT, album.getAlbumId(), pageNo, pageSize);
    }

    @Override
    protected void parseAndMappingAlbumVideoDataFromResponse(Album album, Response response, onGetAlbumVideoListener listener) {
        try {
            VideoResult result = AppManager.getGson().fromJson(response.body().string(), VideoResult.class);
            if (result != null) {
                Log.d(TAG, "parseAndMappingAlbumVideoDataFromResponse " + result.toString());
                VideoList videoList = new VideoList();
                for (Video video : result.getData().getVideos()) {
                    Video v = new Video();
                    v.setHor_high_pic(video.getHor_high_pic());
                    v.setVer_high_pic(video.getVer_high_pic());
                    v.setSite(album.getSite().getSiteId());
                    v.setVid(video.getVid());
                    v.setAid(video.getAid());
                    v.setVideo_name(video.getVideo_name());
                    videoList.add(v);
                }

                if (listener != null) {
                    listener.onGetAlbumVideoSuccess(videoList);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getVideoPlayUrl(int siteId, Video video) {
        return String.format(Constants.API_SOHU.API_VIDEO_PLAY_URL_FORMAT, video.getVid(), video.getAid());
    }

    @Override
    protected void parseAndMappingVideoPlayUrlDataFromResponse(Response response, Video video, onGetVideoPlayUrlListener listener) {
        try {
            JSONObject result = new JSONObject(response.body().string());
            JSONObject data = result.optJSONObject("data");

            //拿标清url
            String normalUrl = data.optString("url_nor");
            if (!TextUtils.isEmpty(normalUrl)) {
                normalUrl += "uid=" + getUUID() + "&pt=5&prod=app&pg=1";
                video.setUrl_nor(normalUrl);
                if (listener != null)
                    listener.onGetNormalUrl(video, normalUrl);
            }
            //拿高清Url
            String hightUrl = data.optString("url_high");
            if (!TextUtils.isEmpty(hightUrl)) {
                hightUrl += "uid=" + getUUID() + "&pt=5&prod=app&pg=1";
                video.setUrl_high(hightUrl);
                if (listener != null)
                    listener.onGetHightUrl(video, hightUrl);
            }
            //拿超清Url
            String superUrl = data.optString("url_super");
            if (!TextUtils.isEmpty(superUrl)) {
                superUrl += "uid=" + getUUID() + "&pt=5&prod=app&pg=1";
                video.setUrl_super(superUrl);
                if (listener != null)
                    listener.onGetSuperUrl(video, superUrl);
            }
            Log.d(TAG,"parseAndMappingVideoPlayUrlDataFromResponse " + "normalUrl"+normalUrl+",hightUrl:"+hightUrl+",superUrl:"+superUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getUUID() {
        UUID uuid = UUID.randomUUID();
        //SOHU API的规则
        return uuid.toString().replace("-", "");
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
