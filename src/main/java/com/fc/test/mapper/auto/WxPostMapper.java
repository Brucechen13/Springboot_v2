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

    @Select("select * from wx_posts, wx_users where wx_posts.userid = wx_users.id")
    public List<WxPost> selectList();
}