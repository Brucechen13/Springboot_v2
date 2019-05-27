package com.fc.test.model.auto;

import java.io.Serializable;
import java.util.Date;

public class WxComment implements Serializable {
    private String id;

    private String userid;

    private String postid;

    private String content;

    private String status;

    private String posttime;

    private WxUser user;

    private WxPost post;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid == null ? null : postid.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPosttime() {
        return posttime;
    }

    public void setPosttime(String posttime) {
        this.posttime = posttime;
    }

    public WxUser getUser() {
        return user;
    }

    public void setUser(WxUser user) {
        this.user = user;
    }

    public WxPost getPost() {
        return post;
    }

    public void setPost(WxPost post) {
        this.post = post;
    }
}