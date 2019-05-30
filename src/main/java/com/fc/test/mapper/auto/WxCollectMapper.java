package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxCollect;

public interface WxCollectMapper {
    int deleteByPrimaryKey(String id);

    int deleteByUserId(String userid, String postid);

    int insert(WxCollect record);

    int insertSelective(WxCollect record);

    WxCollect selectByPrimaryKey(String id);
    WxCollect selectByUserId(String userid, String postid);

    int updateByPrimaryKeySelective(WxCollect record);

    int updateByPrimaryKey(WxCollect record);
}