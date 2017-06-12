package com.signalway.highway.dao.ImageMatchDao;

import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.entity.MapQueryPojo;
import org.apache.ibatis.annotations.Param;

import java.sql.Blob;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGang on 2017/5/15.
 */
public interface ImageMatchDao {

    List<MapPojo> ImageMatchBlobList( MapQueryPojo mapQueryPojo);
    //查询所有点信息
    List<MapQueryPojo> ImageMergeInfoList(MapQueryPojo listQueryPojo);

    int SaveImageMergeInfo(MapPojo mapPojo);

    MapPojo SelectTest();


}
