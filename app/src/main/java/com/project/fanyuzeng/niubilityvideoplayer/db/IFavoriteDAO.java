package com.project.fanyuzeng.niubilityvideoplayer.db;

import com.project.fanyuzeng.niubilityvideoplayer.model.Album;

/**
 * Created by fanyuzeng on 2017/10/16.
 * Function:
 */

public interface IFavoriteDAO {
    public boolean add(Album album);

    public boolean delete(String albumId, int siteId);
}
