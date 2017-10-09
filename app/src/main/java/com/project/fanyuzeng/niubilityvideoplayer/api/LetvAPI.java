package com.project.fanyuzeng.niubilityvideoplayer.api;

import android.text.TextUtils;
import android.util.Log;

import com.project.fanyuzeng.niubilityvideoplayer.Constants;
import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.Video;
import com.project.fanyuzeng.niubilityvideoplayer.model.sohu.VideoList;
import com.project.fanyuzeng.niubilityvideoplayer.utils.MD5;
import com.project.fanyuzeng.niubilityvideoplayer.utils.okHttpUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.ALBUM_LIST_URL_DOCUMENTARY_FORMAT;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.ALBUM_LIST_URL_FORMAT;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.ALBUM_LIST_URL_SHOW_FORMAT;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.BITSTREAM_HIGH;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.BITSTREAM_NORMAL;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.BITSTREAM_SUPER;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_COMIC;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_DOCUMENTRY;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_MOVIE;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_MUSIC;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_SERIES;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.LETV_CHANNELID_VARIETY;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.SEVER_TIME_URL;
import static com.project.fanyuzeng.niubilityvideoplayer.Constants.API_LETV.VIDEO_REAL_LINK_APPENDIX;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class LetvAPI extends BaseSiteAPI {
    private static final String TAG = "LetvAPI";
    private Long mTimeOffSet = Long.MAX_VALUE;

    public LetvAPI() {
        fetchServerTime();
    }

    private void fetchServerTime() {
        if (mTimeOffSet != Long.MAX_VALUE) {
            return;
        }
        okHttpUtils.execute(SEVER_TIME_URL, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, ">> onFailure !!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.d(TAG, ">> onResponse failed!!");
                    return;
                }
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String time = jsonObject.optString("time");
                    updateServerTime(Long.parseLong(time));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void updateServerTime(Long time) {
        if (mTimeOffSet == Long.MAX_VALUE) {
            mTimeOffSet = System.currentTimeMillis()/1000 - time;
        }
    }

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
        return String.format(Constants.API_LETV.ALBUM_DESC_URL_FORMAT, album.getAlbumId());
    }

    @Override
    protected void parseAndMappingAlbumDetailDataFromResponse(Album album, Response response, onGetAlbumDetailListener listener) {
        try {
            String result = response.body().string();
            JSONObject albumJson = new JSONObject(result);
            if (albumJson.opt("body") != null) {
                JSONObject body = albumJson.getJSONObject("body");
                if (body.optJSONObject("picCollections") != null) {
                    JSONObject imgJson = body.optJSONObject("picCollections");
                    if (!TextUtils.isEmpty(imgJson.optString("150*200"))) {
                        album.setHorImgUrl(StringEscapeUtils.unescapeJava(imgJson.optString("150*200")));
                    }
                }
                if (!TextUtils.isEmpty(albumJson.optString("description"))) {
                    album.setAlbumDesc(albumJson.optString("description"));
                }
                if (!TextUtils.isEmpty(albumJson.optString("directory"))) {
                    album.setDirector(albumJson.optString("directory"));
                }
                if (!TextUtils.isEmpty(albumJson.optString("starring"))) {
                    album.setMainActor(albumJson.optString("starring"));
                }
                if (listener != null) {
                    listener.onGetAlbumDetailsSuccess(album);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getAlbumVideoUrl(Album album, int pageNo, int pageSize) {
        return String.format(Constants.API_LETV.ALBUM_VIDEOS_URL_FORMAT, album.getAlbumId(), pageNo, pageSize, "-1", "1");
//        return null;
    }

    @Override
    protected void parseAndMappingAlbumVideoDataFromResponse(Album album, Response response, onGetAlbumVideoListener listener) {
        try {
            String result = response.body().string();

            JSONObject resultJson = new JSONObject(result);
            if (resultJson.optJSONObject("body") != null) {
                JSONObject bodyJson = resultJson.optJSONObject("body");
                JSONArray jsonArray = bodyJson.optJSONArray("videoInfo");
                if (jsonArray != null) {
                    if (jsonArray.length() > 0) {
                        VideoList videoList = new VideoList();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Video video = new Video();
                            //videoInfo表示每个视频info
                            JSONObject videoInfo = jsonArray.getJSONObject(i);
                            video.setAid(Integer.parseInt(album.getAlbumId()));
                            video.setSite(album.getSite().getSiteId());
                            //nameCn: "择天记03"
                            if (!TextUtils.isEmpty(videoInfo.optString("nameCn"))) {
                                video.setVideo_name(videoInfo.optString("nameCn"));
                            }
                            //mid: "64271196" 表示解释乐视视频源需要的
                            if (!TextUtils.isEmpty(videoInfo.optString("mid"))) {
                                video.setMid(Integer.parseInt(videoInfo.optString("mid")));
                            }
                            if (!TextUtils.isEmpty(videoInfo.optString("id"))) {
                                video.setVid(Integer.parseInt(videoInfo.optString("id")));
                            }
                            videoList.add(video);
                        }
                        if (videoList.size() > 0 && listener != null) {
                            listener.onGetAlbumVideoSuccess(videoList);
                        } else {
//                            ErrorInfo info  = buildErrorInfo(url, "onGetVideo", null, ErrorInfo.ERROR_TYPE_DATA_CONVERT);
//                            listener.OnGetVideoFailed(info);
                            ErrorInfo info = new ErrorInfo.Builder()
                                    .setFunctionName("parseAndMappingAlbumVideoDataFromResponse")
                                    .setType(ErrorInfo.ERROR_TYPE_DATA_CONVERT)
                                    .build();
                            if (listener != null) {
                                listener.onGetAlbumVideoFail(info);
                            }
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String getVideoPlayUrl(int siteId, Video video) {
        //arg: mmsid currentServerTime key vid
        return String.format(Constants.API_LETV.VIDEO_FILE_URL_FORMAT, video.getMid(), getCurrentServerTime(), getKey(video, getCurrentServerTime()), video.getVid());
//        return null;
    }

    @Override
    protected void parseAndMappingVideoPlayUrlDataFromResponse(Response response, Video video, onGetVideoPlayUrlListener listener) {
        try {
            String result = response.body().string();
            JSONObject resultJson = new JSONObject(result);
            JSONObject infosJson = resultJson.getJSONObject("body").getJSONObject("videofile").getJSONObject("infos");
            if (infosJson != null) {
                JSONObject normalInfoObject = infosJson.getJSONObject("mp4_350");
                if (normalInfoObject != null) {
                    String normalUrl = "";
                    if (!TextUtils.isEmpty(normalInfoObject.optString("mainUrl"))) {
                        normalUrl = normalInfoObject.optString("mainUrl");
                        normalUrl += VIDEO_REAL_LINK_APPENDIX;
                    } else if (!TextUtils.isEmpty(normalInfoObject.optString("backUrl1"))) {
                        normalUrl = normalInfoObject.optString("backUrl1");
                        normalUrl += VIDEO_REAL_LINK_APPENDIX;
                    } else if (!TextUtils.isEmpty(normalInfoObject.optString("backUrl2"))) {
                        normalUrl = normalInfoObject.optString("backUrl2");
                        normalUrl += VIDEO_REAL_LINK_APPENDIX;
                    }
                    getRealUrl(video, normalUrl, BITSTREAM_NORMAL, listener);
                }
                JSONObject highInfoObject = infosJson.getJSONObject("mp4_1000");
                if (highInfoObject != null) {
                    String highUrl = "";
                    if (!TextUtils.isEmpty(highInfoObject.optString("mainUrl"))) {
                        highUrl = highInfoObject.optString("mainUrl");
                        highUrl += VIDEO_REAL_LINK_APPENDIX;
                    } else if (!TextUtils.isEmpty(highInfoObject.optString("backUrl1"))) {
                        highUrl = highInfoObject.optString("backUrl1");
                        highUrl += VIDEO_REAL_LINK_APPENDIX;
                    } else if (!TextUtils.isEmpty(highInfoObject.optString("backUrl2"))) {
                        highUrl = highInfoObject.optString("backUrl2");
                        highUrl += VIDEO_REAL_LINK_APPENDIX;
                    }
                    getRealUrl(video, highUrl, BITSTREAM_HIGH, listener);
                }
                JSONObject superfoObject = infosJson.getJSONObject("mp4_1300");
                if (superfoObject != null) {
                    String superUrl = "";
                    if (!TextUtils.isEmpty(superfoObject.optString("mainUrl"))) {
                        superUrl = superfoObject.optString("mainUrl");
                        superUrl += VIDEO_REAL_LINK_APPENDIX;
                    } else if (!TextUtils.isEmpty(superfoObject.optString("backUrl1"))) {
                        superUrl = superfoObject.optString("backUrl1");
                        superUrl += VIDEO_REAL_LINK_APPENDIX;
                    } else if (!TextUtils.isEmpty(highInfoObject.optString("backUrl2"))) {
                        superUrl = superfoObject.optString("backUrl2");
                        superUrl += VIDEO_REAL_LINK_APPENDIX;
                    }
                    getRealUrl(video, superUrl, BITSTREAM_SUPER, listener);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //parse 'location' field in whatStreamVideoUrl
    private void getRealUrl(final Video video, String normalUrl, final int type, final onGetVideoPlayUrlListener listener) {
        okHttpUtils.execute(normalUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Nothing
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    return;
                }
                String result = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String realUrl = jsonObject.optString("location");
                    if (type == BITSTREAM_NORMAL) {
                        video.setUrl_nor(realUrl);
                        if (listener != null) {
                            listener.onGetNormalUrl(video, realUrl);
                        }
                    }
                    if (type == BITSTREAM_HIGH) {
                        video.setUrl_high(realUrl);
                        if (listener != null) {
                            listener.onGetHightUrl(video, realUrl);
                        }
                    }
                    if (type == BITSTREAM_SUPER) {
                        video.setUrl_high(realUrl);
                        if (listener != null) {
                            listener.onGetSuperUrl(video, realUrl);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
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

    private String getKey(Video video, String serverTime) {
        StringBuilder sb = new StringBuilder();
        sb.append(video.getMid());
        sb.append(",");
        sb.append(serverTime);
        sb.append(",");
        sb.append("bh65OzqYYYmHRQ");
        return MD5.toMd5(sb.toString());
    }

    private String getCurrentServerTime() {
        if (mTimeOffSet != Long.MAX_VALUE) {
            return String.valueOf(System.currentTimeMillis()/1000 - mTimeOffSet);
        } else {
            return null;
        }
    }
}
