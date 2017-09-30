package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Response;

import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.ALBUM_LIST_URL_DOCUMENTARY_FORMAT;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.ALBUM_LIST_URL_FORMAT;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.ALBUM_LIST_URL_SHOW_FORMAT;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_COMIC;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_DOCUMENTRY;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_MOVIE;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_MUSIC;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_SERIES;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_VARIETY;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class LetvAPI extends BaseSiteAPI {
    private static final String TAG = "LetvAPI";

    @Override
    protected String getAlbumUrl(ChannelMode channelMode, int pageNo, int pageSize) {
        if (channelMode.getChannelId() == ChannelMode.DOCUMENTRY) {
            return String.format(ALBUM_LIST_URL_DOCUMENTARY_FORMAT, convertChannelId(channelMode), pageNo, pageSize);
        } else if (channelMode.getChannelId() == ChannelMode.SERIES) {
            return String.format(ALBUM_LIST_URL_SHOW_FORMAT, convertChannelId(channelMode), pageNo, pageSize);
        }
        return String.format(ALBUM_LIST_URL_FORMAT, convertChannelId(channelMode), pageNo, pageSize);
    }

    @Override
    protected void parseAndMappingDataFromResponse(Response response, onGetChannelAlbumListener listener) {
        try {
            String json = response.body().string();
            JSONObject resultJson = new JSONObject(json);
            JSONObject bodyJson = resultJson.optJSONObject("body");
            AlbumList albumList = new AlbumList();
            if (bodyJson.optInt("album_count") > 0) {
                JSONArray albumListJson = bodyJson.optJSONArray("album_list");
                for (int i = 0; i < albumListJson.length(); i++) {
                    Album album = new Album(SiteMode.LETV);
                    JSONObject albumJson = albumListJson.getJSONObject(i);
                    album.setAlbumId(albumJson.getString("aid"));
                    album.setAlbumDesc(albumJson.getString("subname"));
                    album.setTitle(albumJson.getString("name"));
                    album.setTip(albumJson.getString("subname"));
                    JSONObject imagesJson = albumJson.getJSONObject("images");
//                            String imgUrl = StringEscapeUtils.unescapeJava(imagesJson.getString("400*300"));
//                            Log.d(TAG,"onResponse " + "before:"+imagesJson.getString("400*300")+" ,after: "+imgUrl);
//                            album.setCompleted(albumJson);
                    album.setHorImgUrl(imagesJson.getString("400*300"));
                    albumList.add(album);
                }

            }
            if (albumList.size() > 0) {
                listener.onGetChannelAlbumSuccess(albumList);
            }

        } catch (Exception e) {
            e.printStackTrace();
            ErrorInfo errorInfo = new ErrorInfo.Builder()
                    .setFunctionName("parseAndMappingDataFromResponse")
                    .setType(ErrorInfo.ERROR_TYPE_JSON_PARSE)
                    .setSiteId(SiteMode.LETV)
                    .setClassName(TAG)
                    .build();
            listener.onGetChannelAlbumFailed(errorInfo);
        }
    }

    @Override
    protected String getAlbumDetailUrl(Album album) {
        // TODO: 2017/9/30
        return null;
    }

    @Override
    protected void parseAndMappingAlbumDetailDataFromResponse(Album album, Response response, onGetAlbumDetailListener listener) {
        // TODO: 2017/9/30
    }

    @Override
    protected String getAlbumVideoUrl(Album album, int pageNo, int pageSize) {
        // TODO: 2017/9/30
        return null;
    }

    @Override
    protected void parseAndMappingAlbumVideoDataFromResponse(Album album, Response response, onGetAlbumVideoListener listener) {
        // TODO: 2017/9/30
    }

    /**
     * 自定义频道id与真实频道id的转换
     *
     * @param channelMode
     * @return
     */
    private int convertChannelId(ChannelMode channelMode) {
        int channelId = -1;//-1 无效值
        switch (channelMode.getChannelId()) {
            case ChannelMode.SERIES:
                channelId = LETV_CHANNELID_SERIES;
                break;
            case ChannelMode.MOVIE:
                channelId = LETV_CHANNELID_MOVIE;
                break;
            case ChannelMode.COMIC:
                channelId = LETV_CHANNELID_COMIC;
                break;
            case ChannelMode.MUSIC:
                channelId = LETV_CHANNELID_MUSIC;
                break;
            case ChannelMode.DOCUMENTRY:
                channelId = LETV_CHANNELID_DOCUMENTRY;
                break;
            case ChannelMode.VARIETY:
                channelId = LETV_CHANNELID_VARIETY;
                break;
        }
        return channelId;
    }


}
