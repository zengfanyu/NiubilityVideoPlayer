package com.project.fanyuzeng.niubilityvideoplayer.model.sohu;

import java.util.List;

/**
 * Created by fanyuzeng on 2017/9/30.
 * Function:
 */

public class VideoData {

    

    private int count;
    private int page;
    private List<Video> videos;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    @Override
    public String toString() {
        return "VideoData{" +
                "count=" + count +
                ", page=" + page +
                ", videos=" + videos +
                '}';
    }



}
