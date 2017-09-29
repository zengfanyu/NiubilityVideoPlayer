package com.project.fanyuzeng.niubilityvideoplayer.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.project.fanyuzeng.niubilityvideoplayer.AppManager;

/**
 * Created by fanyuzeng on 2017/9/25.
 * Function:
 */

public class Album implements Parcelable {
    private String albumId; //专辑ID
    private int videoTotle;//集数
    private String title;//标题
    private String subTitle;//子标题
    private String director;//导演
    private String mainActor;//主演
    private String verImgUrl;//竖图url
    private String horImgUrl;//横图url
    private String albumDesc;//专辑描述
    private SiteMode site;//网站
    private String tip;//提示
    private boolean isCompleted;//是否更新完
    private String letvStyle;//letv独有
    public static final Parcelable.Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel source) {
            return new Album(source);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    public Album(int siteId) {
        site = new SiteMode(siteId);
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public int getVideoTotle() {
        return videoTotle;
    }

    public void setVideoTotle(int videoTotle) {
        this.videoTotle = videoTotle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getVerImgUrl() {
        return verImgUrl;
    }

    public void setVerImgUrl(String verImgUrl) {
        this.verImgUrl = verImgUrl;
    }

    public String getHorImgUrl() {
        return horImgUrl;
    }

    public void setHorImgUrl(String horImgUrl) {
        this.horImgUrl = horImgUrl;
    }

    public String getAlbumDesc() {
        return albumDesc;
    }

    public void setAlbumDesc(String albumDesc) {
        this.albumDesc = albumDesc;
    }

    public SiteMode getSite() {
        return site;
    }

    public void setSite(SiteMode site) {
        this.site = site;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public String getLetvStyle() {
        return letvStyle;
    }

    public void setLetvStyle(String letvStyle) {
        this.letvStyle = letvStyle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Album(Parcel in) {
        this.albumId = in.readString();
        this.videoTotle = in.readInt();
        this.title = in.readString();
        this.subTitle = in.readString();
        this.director = in.readString();
        this.mainActor = in.readString();
        this.verImgUrl = in.readString();
        this.horImgUrl = in.readString();
        this.albumDesc = in.readString();
        this.tip = in.readString();
        this.site = new SiteMode(in.readInt());
        this.isCompleted = in.readByte() != 0;
        this.letvStyle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(albumId);
        dest.writeInt(videoTotle);
        dest.writeString(title);
        dest.writeString(subTitle);
        dest.writeString(director);
        dest.writeString(mainActor);
        dest.writeString(verImgUrl);
        dest.writeString(horImgUrl);
        dest.writeString(albumDesc);
        dest.writeString(tip);
        dest.writeInt(site.getSiteId());
        dest.writeByte((byte) (isCompleted() ? 1 : 0));
        dest.writeString(letvStyle);
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId='" + albumId + '\'' +
                ", videoTotle=" + videoTotle +
                ", title='" + title + '\'' +
                ", subTitle='" + subTitle + '\'' +
                ", director='" + director + '\'' +
                ", mainActor='" + mainActor + '\'' +
                ", verImgUrl='" + verImgUrl + '\'' +
                ", horImgUrl='" + horImgUrl + '\'' +
                ", albumDesc='" + albumDesc + '\'' +
                ", site=" + site +
                ", tip='" + tip + '\'' +
                ", isCompleted=" + isCompleted +
                ", letvStyle='" + letvStyle  +
                '}';
    }

    public String toJson() {
        String result = AppManager.getGson().toJson(this);
        return result;
    }

    public Album fromJson(String json) {
        Album album = AppManager.getGson().fromJson(json, Album.class);
        return album;
    }
}
