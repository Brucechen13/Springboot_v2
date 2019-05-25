package com.fc.wx.service;

import com.fc.test.common.base.BaseService;
import com.fc.test.mapper.auto.WxUserMapper;
import com.fc.test.model.auto.WxUser;
import com.fc.test.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WxServiceService {

    @Autowired
    private WxUserMapper wxUserMapper;

    public WxUser loginByWeixin(String code){
        return null;
    }


    public int insert(WxUser record) {
        //添加雪花主键id
        record.setId(SnowflakeIdWorker.getUUID());
        return wxUserMapper.insert(record);
    }
}
