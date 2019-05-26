package com.fc.wx.bean;

public class ResponseBean {

    // http 状态码
    private int status;

    // 返回信息
    private String msg;

    // 返回的数据
    private Object data;

    public ResponseBean(int code, String msg, Object data) {
        this.status = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseBean MakeSuccessRes(String msg, Object data){
        return new ResponseBean(0, msg, data);
    }

    public static ResponseBean MakeFailRes(String msg){
        return new ResponseBean(1, msg, null);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
