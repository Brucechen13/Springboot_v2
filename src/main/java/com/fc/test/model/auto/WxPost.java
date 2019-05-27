package com.fc.test.model.auto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class WxPost implements Serializable {
    private String id;

    private String userid;

    private String title;

    private String content;

    private String status;

    private String flagstr;

    private List<String> flags;

    private List<WxComment> comments;

    private String classes;

    private String begintime;

    private String endtime;

    private WxUser user;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
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

    public String getFlagstr() {
        return flagstr;
    }

    public void setFlagstr(String flagstr) {
        this.flagstr = flagstr;
    }

    public List<String> getFlags() {
        return flags;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public List<WxComment> getComments() {
        return comments;
    }

    public void setComments(List<WxComment> comments) {
        this.comments = comments;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public WxUser getUser() {
        return user;
    }

    public void setUser(WxUser user) {
        this.user = user;
    }
}