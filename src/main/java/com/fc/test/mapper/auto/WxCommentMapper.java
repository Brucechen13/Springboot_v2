package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxComment;

import java.util.List;

public interface WxCommentMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxComment record);

    int insertSelective(WxComment record);

    WxComment selectByPrimaryKey(String id);

    List<WxComment> selectByPostId(String id);

    int updateByPrimaryKeySelective(WxComment record);

    int updateByPrimaryKey(WxComment record);
}