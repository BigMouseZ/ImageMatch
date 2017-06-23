package com.signalway.highway.service;

import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.entity.MapQueryPojo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGang on 2017/5/15.
 */
public interface ImageMatchService {

    List<MapQueryPojo> ImageMergeInfoList(MapQueryPojo listQueryPojo);

    void SaveImageMergeInfo();

}
