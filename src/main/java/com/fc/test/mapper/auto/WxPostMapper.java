package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxPost;

public interface WxPostMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxPost record);

    int insertSelective(WxPost record);

    WxPost selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxPost record);

    int updateByPrimaryKey(WxPost record);
}