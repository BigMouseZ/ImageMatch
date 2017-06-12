package com.signalway.highway.service.impl;

import com.signalway.highway.dao.ImageMatchDao.ImageMatchDao;
import com.signalway.highway.entity.MapPojo;
import com.signalway.highway.entity.MapQueryPojo;
import com.signalway.highway.service.ImageMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by ZhangGang on 2017/5/15.
 */
@Service
public class ImageMatchServiceImpl implements ImageMatchService {

  @Autowired
  ImageMatchDao imageMatchDao;


  @Override
  public List<MapPojo> ImageMatchBlobList( MapQueryPojo mapQueryPojo)  {

    List<MapPojo> mapPojoList = imageMatchDao.ImageMatchBlobList(mapQueryPojo);

    return mapPojoList;
  }

  @Override
  public List<MapQueryPojo> ImageMergeInfoList(MapQueryPojo listQueryPojo)  {

    List<MapQueryPojo> mapQueryPojoList = imageMatchDao.ImageMergeInfoList(listQueryPojo);

    return mapQueryPojoList;
  }


  @Override
  public int SaveImageMergeInfo(MapPojo mapPojo)  {

   int rel =  imageMatchDao.SaveImageMergeInfo(mapPojo);

     return rel;

  }

  @Override
  public MapPojo SelectTest()  {

    MapPojo mapQueryPojoList = imageMatchDao.SelectTest();

    return mapQueryPojoList;
  }

}


