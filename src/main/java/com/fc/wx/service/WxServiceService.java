package com.fc.wx.service;

import com.fc.test.common.base.BaseService;
import com.fc.test.mapper.auto.WxCommentMapper;
import com.fc.test.mapper.auto.WxPostMapper;
import com.fc.test.mapper.auto.WxUserMapper;
import com.fc.test.model.auto.WxComment;
import com.fc.test.model.auto.WxPost;
import com.fc.test.model.auto.WxUser;
import com.fc.test.model.custom.Tablepar;
import com.fc.test.util.SnowflakeIdWorker;
import com.fc.wx.common.AppUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class WxServiceService {

    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    private WxPostMapper wxPostMapper;

    @Autowired
    private WxCommentMapper wxCommentMapper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

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

    public int insertComment(WxComment record) {
        //添加雪花主键id
        record.setId(SnowflakeIdWorker.getUUID());
        return wxCommentMapper.insert(record);
    }

    private void deal(WxPost post){
        List<String> flags = new ArrayList<>();
        for(String flag : post.getFlagstr().split(AppUtil.SEP)){
            flags.add(flag);
        }
        post.setFlags(flags);
        if(post.getEndtime().compareTo(sdf.format(new Date())) < 0){
            post.setStatus(AppUtil.ENDED);
        }
        post.setComments(wxCommentMapper.selectByPostId(post.getId()));
    }


    public PageInfo<WxPost> listPosts(Tablepar tablepar){
        PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
        List<WxPost> list = wxPostMapper.selectList();
        for(WxPost post : list){
            deal(post);
        }
        PageInfo<WxPost> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo<WxPost> listOwnList(String userId, Tablepar tablepar){
        PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
        List<WxPost> list = wxPostMapper.selectOwnList(userId);
        for(WxPost post : list){
            deal(post);
        }
        PageInfo<WxPost> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public PageInfo<WxPost> listCommentList(String userId, Tablepar tablepar){
        PageHelper.startPage(tablepar.getPageNum(), tablepar.getPageSize());
        List<WxPost> list = wxPostMapper.selectCommentList(userId);
        for(WxPost post : list){
            deal(post);
        }
        PageInfo<WxPost> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public WxPost getPost(String id){
        WxPost post = wxPostMapper.selectByPrimaryKey(id);
        if(post != null){
            post.setComments(wxCommentMapper.selectByPostId(post.getId()));
        }
        return post;
    }
}
