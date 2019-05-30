package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxPost;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WxPostMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxPost record);

    int insertSelective(WxPost record);

    WxPost selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WxPost record);

    int updateByPrimaryKey(WxPost record);

    List<WxPost> selectList();

    List<WxPost> selectOwnList(String id);

    List<WxPost> selectCommentList(String id);

    List<WxPost> selectCollectList(String id);
}