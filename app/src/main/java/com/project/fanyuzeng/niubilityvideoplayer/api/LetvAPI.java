package com.project.fanyuzeng.niubilityvideoplayer.api;

import com.project.fanyuzeng.niubilityvideoplayer.model.Album;
import com.project.fanyuzeng.niubilityvideoplayer.model.AlbumList;
import com.project.fanyuzeng.niubilityvideoplayer.model.ChannelMode;
import com.project.fanyuzeng.niubilityvideoplayer.model.SiteMode;
import com.project.fanyuzeng.niubilityvideoplayer.utils.okHttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class LetvAPI extends BaseSiteAPI {
    private static final String TAG = "LetvAPI";
    private static final int LETV_CHANNELID_MOVIE = 1; //乐视电影频道ID
    private static final int LETV_CHANNELID_SERIES = 2; //乐视电视剧频道ID
    private static final int LETV_CHANNELID_VARIETY = 11; //乐视综艺频道ID
    private static final int LETV_CHANNELID_DOCUMENTRY = 16; //乐视纪录片频道ID
    private static final int LETV_CHANNELID_COMIC = 5; //乐视动漫频道ID
    private static final int LETV_CHANNELID_MUSIC = 9; //乐视音乐频道ID
    private static final int BITSTREAM_SUPER = 100;
    private static final int BITSTREAM_NORMAL = 101;
    private static final int BITSTREAM_HIGH = 102;
    //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/listalbum/act/index/src/1/cg/2/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/1/ps/30/pcode/010110263/version/5.6.2.mindex.html
    private final static String ALBUM_LIST_URL_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_DOCUMENTARY_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/3/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    private final static String ALBUM_LIST_URL_SHOW_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
            "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

    //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/album/act/detail/id/10026309/pcode/010410000/version/2.1.mindex.html
    private final static String ALBUM_DESC_URL_FORMAT = "http://static.meizi.app.m.letv.com/" +
            "android/mod/mob/ctl/album/act/detail/id/%s/pcode/010410000/version/2.1.mindex.html";
    //key : bh65OzqYYYmHRQ
    private final static String SEVER_TIME_URL = "http://dynamic.meizi.app.m.letv.com/android/dynamic.php?mod=mob&ctl=timestamp&act=timestamp&pcode=010410000&version=5.4";

    //http://static.app.m.letv.com/android/mod/mob/ctl/videolist/act/detail/id/10026309/vid/0/b/1/s/30/o/-1/m/1/pcode/010410000/version/2.1.mindex.html
    private final static String ALBUM_VIDEOS_URL_FORMAT = "http://static.app.m.letv.com/" +
            "android/mod/mob/ctl/videolist/act/detail/id/%s/vid/0/b/%s/s/%s/o/%s/m/%s/pcode/010410000/version/2.1.mindex.html";

    //arg: mmsid currentServerTime key vid
    private final static String VIDEO_FILE_URL_FORMAT = "http://dynamic.meizi.app.m.letv.com/android/dynamic.php?mmsid=" +
            "%s&playid=0&tss=ios&pcode=010410000&version=2.1&tm=%s&key=%s&vid=" +
            "%s&ctl=videofile&mod=minfo&act=index";

    private final static String VIDEO_REAL_LINK_APPENDIX = "&format=1&expect=1&termid=2&pay=0&ostype=android&hwtype=iphone";

    //http://play.g3proxy.lecloud.com/vod/v2/MjYwLzkvNTIvbGV0di11dHMvMTQvdmVyXzAwXzIyLTEwOTczMjQ5NzUtYXZjLTE5OTY1OS1hYWMtNDgwMDAtMjU4NjI0MC04Mzk3NjQ4OC04MmQxMGVlM2I3ZTdkMGU5ZjE4YzM1NDViMWI4MzI4Yi0xNDkyNDA2MDE2MTg4Lm1wNA==?b=259&mmsid=64244666&tm=1492847915&key=22f2f114ed643e0d08596659e5834cd6&platid=3&splatid=347&playid=0&tss=ios&vtype=21&cvid=711590995389&payff=0&pip=83611a86979ddb3df8ef0fb41034f39c&format=1&sign=mb&dname=mobile&expect=3&p1=0&p2=00&p3=003&tag=mobile&pid=10031263&format=1&expect=1&termid=2&pay=0&ostype=android&hwtype=iphone

    @Override
    public void onGetChannelAlbums(ChannelMode channelMode, int pageNo, int pageSize, onGetChannelAlbumListener channelAlbumListener) {
        String url = getAlbumUrl(channelMode, pageNo, pageSize);
        doGetChannelAlbumsByUrl(url, channelAlbumListener);
    }

    private void doGetChannelAlbumsByUrl(final String url, final onGetChannelAlbumListener listener) {
        okHttpUtils.execute(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    ErrorInfo errorInfo = buildErrorInfo(url, "doGetChannelAlbumsByUrl", e, ErrorInfo.ERROR_TYPE_URL);
                    listener.onGetChannelAlbumFailed(errorInfo);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    ErrorInfo errorInfo = buildErrorInfo(url, "doGetChannelAlbumsByUrl", null, ErrorInfo.ERROR_TYPE_HTTP);
                    listener.onGetChannelAlbumFailed(errorInfo);
                    return;
                }
                try {
                    String json = response.body().string();
                    JSONObject resultJson=new JSONObject(json);
                    JSONObject bodyJson = resultJson.optJSONObject("body");
                    AlbumList albumList = new AlbumList();
                    if (bodyJson.optInt("album_count")>0){
                        JSONArray albumListJson = bodyJson.optJSONArray("album_list");
                        for (int i=0;i<albumListJson.length();i++){
                            Album album=new Album(SiteMode.LETV);
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
                        if (albumList.size()>0){
                            listener.onGetChannelAlbumSuccess(albumList);
                        }

                } catch (JSONException e) {
                    e.printStackTrace();
                    ErrorInfo errorInfo=new ErrorInfo(SiteMode.LETV,ErrorInfo.ERROR_TYPE_JSON_PARSE);
                    listener.onGetChannelAlbumFailed(errorInfo);
                }
            }
        });
    }

    private String getAlbumUrl(ChannelMode channelMode, int pageNo, int pageSize) {
        if (channelMode.getChannelId() == ChannelMode.DOCUMENTRY) {
            return String.format(ALBUM_LIST_URL_DOCUMENTARY_FORMAT, convertChannelId(channelMode), pageNo, pageSize);
        } else if (channelMode.getChannelId() == ChannelMode.SERIES) {
            return String.format(ALBUM_LIST_URL_SHOW_FORMAT, convertChannelId(channelMode), pageNo, pageSize);
        }
        return String.format(ALBUM_LIST_URL_FORMAT, convertChannelId(channelMode), pageNo, pageSize);
    }

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

    private ErrorInfo buildErrorInfo(String url, String functionName, Exception e, int type) {
        ErrorInfo info = new ErrorInfo(SiteMode.LETV, type);
        info.setExceptionString(e.getMessage());
        info.setFunctionName(functionName);
        info.setUrl(url);
        info.setTag(TAG);
        info.setClassName(TAG);
        return info;

    }
}
