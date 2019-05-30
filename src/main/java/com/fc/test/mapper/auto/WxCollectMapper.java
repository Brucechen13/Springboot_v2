package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxCollect;

public interface WxCollectMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxCollect record);

    int insertSelective(WxCollect record);

    WxCollect selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxCollect record);

    int updateByPrimaryKey(WxCollect record);
}