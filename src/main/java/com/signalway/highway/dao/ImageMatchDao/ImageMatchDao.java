package com.signalway.highway.dao.ImageMatchDao;

import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.entity.MapQueryPojo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

/**
 * Created by ZhangGang on 2017/5/15.
 */
public interface ImageMatchDao {

    List<MapPojo> ImageMatchBlobList(MapQueryPojo mapQueryPojo);
    //查询所有点信息
    List<MapQueryPojo> ImageMergeInfoList(MapQueryPojo listQueryPojo);

    int SaveImageMergeInfo(MapPojo mapPojo);

    int SaveImageMergeInfoList(List<MapPojo> mapPojoList);

    int InsertImageMergeInfoList(List<MapQueryPojo> mapQueryPojos);

    List<MapQueryPojo> findAll();

    /*动态建临时表*/
    int createNewTable(@Param("tableName") String tableName);
    /*删除表*/
    int dropTable(@Param("tableName") String tableName);
}
