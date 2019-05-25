package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxUser;

public interface WxUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);
}