package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxComment;

public interface WxCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxComment record);

    int insertSelective(WxComment record);

    WxComment selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxComment record);

    int updateByPrimaryKey(WxComment record);
}