package com.signalway.highway.service;

import com.signalway.highway.entity.MapQueryPojo;

import java.io.File;
import java.util.List;

/**
 * Created by ZhangGang on 2017/5/15.
 */
public interface ImageMatchService {

    List<MapQueryPojo> ImageMergeInfoList(MapQueryPojo QueryPojo);

    void SaveImageMergeInfo(File filepath) throws InterruptedException;

    void InsertImageMergeInfoList(List<MapQueryPojo> mapQueryPojos);

    int CreateTempTable(String tableName);

}
