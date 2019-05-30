package com.fc.wx.bean;

import java.util.List;

public class WxPostFrontBean {
    private String titleIntro;
    private String taskDiscribe;
    private String classes;
    private String startime;
    private String endtime;
    private List<String> selectedTag;

    public String getTitleIntro() {
        return titleIntro;
    }

    public void setTitleIntro(String titleIntro) {
        this.titleIntro = titleIntro;
    }

    public String getTaskDiscribe() {
        return taskDiscribe;
    }

    public void setTaskDiscribe(String taskDiscribe) {
        this.taskDiscribe = taskDiscribe;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getStartime() {
        return startime;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public List<String> getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(List<String> selectedTag) {
        this.selectedTag = selectedTag;
    }
}
