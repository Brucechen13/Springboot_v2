package com.fc.test.mapper.auto;

import com.fc.test.model.auto.WxUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface WxUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(WxUser record);

    int insertSelective(WxUser record);

    WxUser selectByPrimaryKey(String id);

    WxUser selectByOpenId(String id);

    int updateByPrimaryKeySelective(WxUser record);

    int updateByPrimaryKey(WxUser record);

    @Select("select * from wx_users")
    public List<WxUser> selectList();
}