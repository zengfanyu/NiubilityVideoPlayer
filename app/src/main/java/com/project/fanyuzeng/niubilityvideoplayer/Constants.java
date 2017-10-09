package com.project.fanyuzeng.niubilityvideoplayer;

/**
 * Created by fanyuzeng on 2017/9/29.
 * Function:
 */

public class Constants {

    public static class API_LETV {
        public static final String TAG = "LetvAPI";
        public static final int LETV_CHANNELID_MOVIE = 1; //乐视电影频道ID
        public static final int LETV_CHANNELID_SERIES = 2; //乐视电视剧频道ID
        public static final int LETV_CHANNELID_VARIETY = 11; //乐视综艺频道ID
        public static final int LETV_CHANNELID_DOCUMENTRY = 16; //乐视纪录片频道ID
        public static final int LETV_CHANNELID_COMIC = 5; //乐视动漫频道ID
        public static final int LETV_CHANNELID_MUSIC = 9; //乐视音乐频道ID
        public static final int BITSTREAM_SUPER = 100;
        public static final int BITSTREAM_NORMAL = 101;
        public static final int BITSTREAM_HIGH = 102;
        //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/listalbum/act/index/src/1/cg/2/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/1/ps/30/pcode/010110263/version/5.6.2.mindex.html
        public final static String ALBUM_LIST_URL_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
                "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

        public final static String ALBUM_LIST_URL_DOCUMENTARY_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
                "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/3/ph/420003,420004/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

        public final static String ALBUM_LIST_URL_SHOW_FORMAT = "http://static.meizi.app.m.letv.com/android/" +
                "mod/mob/ctl/listalbum/act/index/src/1/cg/%s/or/20/vt/180001/ph/420003,420004/pt/-141003/pn/%s/ps/%s/pcode/010110263/version/5.6.2.mindex.html";

        //http://static.meizi.app.m.letv.com/android/mod/mob/ctl/album/act/detail/id/10026309/pcode/010410000/version/2.1.mindex.html
        public final static String ALBUM_DESC_URL_FORMAT = "http://static.meizi.app.m.letv.com/" +
                "android/mod/mob/ctl/album/act/detail/id/%s/pcode/010410000/version/2.1.mindex.html";
        //key : bh65OzqYYYmHRQ     server time
        public final static String SEVER_TIME_URL = "http://dynamic.meizi.app.m.letv.com/android/dynamic.php?mod=mob&ctl=timestamp&act=timestamp&pcode=010410000&version=5.4";

        //http://static.app.m.letv.com/android/mod/mob/ctl/videolist/act/detail/id/10026309/vid/0/b/1/s/30/o/-1/m/1/pcode/010410000/version/2.1.mindex.html
        public final static String ALBUM_VIDEOS_URL_FORMAT = "http://static.app.m.letv.com/" +
                "android/mod/mob/ctl/videolist/act/detail/id/%s/vid/0/b/%s/s/%s/o/%s/m/%s/pcode/010410000/version/2.1.mindex.html";

        //arg: mmsid currentServerTime key vid
        public final static String VIDEO_FILE_URL_FORMAT = "http://dynamic.meizi.app.m.letv.com/android/dynamic.php?mmsid=" +
                "%s&playid=0&tss=ios&pcode=010410000&version=2.1&tm=%s&key=%s&vid=" +
                "%s&ctl=videofile&mod=minfo&act=index";

        public final static String VIDEO_REAL_LINK_APPENDIX = "&format=1&expect=1&termid=2&pay=0&ostype=android&hwtype=iphone";

        //http://play.g3proxy.lecloud.com/vod/v2/MjYwLzkvNTIvbGV0di11dHMvMTQvdmVyXzAwXzIyLTEwOT
        // czMjQ5NzUtYXZjLTE5OTY1OS1hYWMtNDgwMDAtMjU4NjI0MC04Mzk3NjQ4OC04MmQxMGVlM2I3ZTdkMGU5ZjE4
        // YzM1NDViMWI4MzI4Yi0xNDkyNDA2MDE2MTg4Lm1wNA==?b=259&mmsid=64244666&tm=1492847915&key=22
        // f2f114ed643e0d08596659e5834cd6&platid=3&splatid=347&playid=0&tss=ios&vtype=21&cvid=711
        // 590995389&payff=0&pip=83611a86979ddb3df8ef0fb41034f39c&format=1&sign=mb&dname=mobile&e
        // xpect=3&p1=0&p2=00&p3=003&tag=mobile&pid=10031263&format=1&expect=1&termid=2&pay=0&ostype=android&hwtype=iphone
    }

   public static class API_SOHU{
        public static final int SOHU_CHANNELID_MOVIE = 1; //搜狐电影频道ID
        public static final int SOHU_CHANNELID_SERIES = 2; //搜狐电视剧频道ID
        public static final int SOHU_CHANNELID_VARIETY = 7; //搜狐综艺频道ID
        public static final int SOHU_CHANNELID_DOCUMENTRY = 8; //搜狐纪录片频道ID
        public static final int SOHU_CHANNELID_COMIC = 16; //搜狐动漫频道ID
        public static final int SOHU_CHANNELID_MUSIC = 24; //搜狐音乐频道ID

        //某一专辑详情
        //http://api.tv.sohu.com/v4/album/info/9395605.json?plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
        public final static String API_KEY = "plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47";
        public final static String API_ALBUM_INFO = "http://api.tv.sohu.com/v4/album/info/";
        //http://api.tv.sohu.com/v4/search/channel.json?cid=2&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47&page=1&page_size=1
        public final static String API_CHANNEL_ALBUM_FORMAT = "http://api.tv.sohu.com/v4/search/channel.json" +
                "?cid=%s&o=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&" +
                "sver=6.2.0&sysver=4.4.2&partner=47&page=%s&page_size=%s";
        //http://api.tv.sohu.com/v4/album/videos/9395605.json?page=1&page_size=50&order=0&site=1&with_trailer=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47
        public final static String API_ALBUM_VIDOES_FORMAT = "http://api.tv.sohu.com/v4/album/videos/%s.json?page=%s&page_size=%s&order=0&site=1&with_trailer=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=6.2.0&sysver=4.4.2&partner=47";
        // 播放url
        //http://api.tv.sohu.com/v4/video/info/3669315.json?site=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=4.5.1&sysver=4.4.2&partner=47&aid=9112373
        public final static String API_VIDEO_PLAY_URL_FORMAT = "http://api.tv.sohu.com/v4/video/info/%s.json?site=1&plat=6&poid=1&api_key=9854b2afa779e1a6bff1962447a09dbd&sver=4.5.1&sysver=4.4.2&partner=47&aid=%s";
        //真实url格式 m3u8
        //http://hot.vrs.sohu.com/ipad3669271_4603585256668_6870592.m3u8?plat=6uid=f5dbc7b40dad477c8516885f6c681c01&pt=5&prod=app&pg=1
    }
}
