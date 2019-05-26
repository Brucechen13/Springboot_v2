package com.fc.wx.service;

import com.fc.test.common.base.BaseService;
import com.fc.test.mapper.auto.WxPostMapper;
import com.fc.test.mapper.auto.WxUserMapper;
import com.fc.test.model.auto.WxPost;
import com.fc.test.model.auto.WxUser;
import com.fc.test.model.custom.Tablepar;
import com.fc.test.util.SnowflakeIdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WxServiceService {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    private WxPostMapper wxPostMapper;

    public WxUser loginByWeixin(String code){
        return wxUserMapper.selectByOpenId(code);
    }


    public int insertUser(WxUser record) {
        //添加雪花主键id
        record.setId(SnowflakeIdWorker.getUUID());
        return wxUserMapper.insert(record);
    }

    public int insertPost(WxPost record) {
        //添加雪花主键id
        record.setId(SnowflakeIdWorker.getUUID());
        return wxPostMapper.insert(record);
    }

    public PageInfo<WxPost> listPosts(Tablepar tablepar){
        PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
        List<WxPost> list = wxPostMapper.selectList();
        PageInfo<WxPost> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
