package com.fc.test.controller;

import com.fc.test.model.auto.WxComment;
import com.fc.test.model.auto.WxPost;
import com.fc.test.model.auto.WxUser;
import com.fc.test.model.custom.TableSplitResult;
import com.fc.test.model.custom.Tablepar;
import com.fc.wx.bean.ResponseBean;
import com.fc.wx.common.AesCbcUtil;
import com.fc.wx.common.AppUtil;
import com.fc.wx.common.HttpRequest;
import com.fc.test.common.base.BaseController;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("wx")
@Api(value = "微信接口")
public class WXController extends BaseController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    @ApiOperation(value="查看所有动态",notes="查看所有动态")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(Tablepar tablepar){
        PageInfo<WxPost> page=wxServiceService.listPosts(tablepar) ;
        TableSplitResult<WxPost> result=new TableSplitResult<>(page.getPageNum(), (long)page.getPages(), page.getList());
        return  ResponseBean.MakeSuccessRes("Post List", result);
    }

    @ApiOperation(value="添加新动态",notes="{'titleIntro':xx, 'taskDiscribe':xx, 'startime':xx, 'endtime':xx, 'selectedTag':[], 'class':xx}")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBean addPost(@CookieValue("sessionId") String userId, @RequestBody String json) {
        // 直接将json信息打印出来
        System.out.println(json + " " + userId);
        JsonObject jsonParam = new JsonParser().parse(json).getAsJsonObject();
        WxPost post = new WxPost();
        post.setUserid(userId);
        post.setTitle(jsonParam.get("titleIntro").toString());
        post.setContent(jsonParam.get("taskDiscribe").toString());
        post.setClasses(jsonParam.get("class").toString());
        if(jsonParam.get("startime") == null ||
                jsonParam.get("startime").toString().equals("")){
            post.setBegintime(jsonParam.get("startime").toString());
            post.setEndtime(jsonParam.get("endtime").toString().toLowerCase());
            //class
        }else{
            post.setBegintime(sdf.format(new Date()));
            post.setEndtime(sdf.format(new Date()));
        }
        JsonArray flags = jsonParam.getAsJsonArray("selectedTag");
        if(flags.size() == 0){
            post.setFlagstr("");
        }else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < flags.size(); i ++) {
                sb.append(flags.get(i).toString() + AppUtil.SEP);
            }
            post.setFlagstr(sb.toString());
        }
        post.setStatus("进行中");
        wxServiceService.insertPost(post);

        return ResponseBean.MakeSuccessRes("添加动态成功", null);
    }

    @ApiOperation(value="添加动态评论",notes="添加动态评论")
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public ResponseBean addComment(@CookieValue("sessionId") String userId, String postid, String content) {

        WxComment comment = new WxComment();
        comment.setContent(content);
        comment.setPostid(postid);
        comment.setUserid(userId);
        comment.setStatus("");
        comment.setPosttime(sdf.format(new Date()));
        wxServiceService.insertComment(comment);

        return ResponseBean.MakeSuccessRes("添加评论成功", content);
    }

    @ApiOperation(value="用户登录",notes="用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean loginByWeixin(HttpServletResponse response, String code, String nickName, String avatarUrl) {
        String url = AppUtil.wxLoginUrl;
        String param = "appid=" + AppUtil.appId + "&secret=" + AppUtil.secret + "&js_code=" + code + "&grant_type=authorization_code";
        WxUser user = null;
        String msg = "之前已登录";
        try {
            String ret = HttpRequest.sendGet(url, param);
            System.out.println(ret);
            JsonObject res = new JsonParser().parse(ret).getAsJsonObject();
            String openid = res.get("openid").toString();
            String sessionkey = res.get("session_key").toString();

            user = wxServiceService.loginByWeixin(openid); //根据code去调用接口获取用户openid和session_key
            if (user == null) {
                user = new WxUser();
                user.setOpenid(openid);
                user.setSessionkey(sessionkey);
                user.setNickname(nickName);
                user.setAvatarurl(avatarUrl);
                user.setSign("该用户尚未设置签名");
                user.setLasttime(sdf.format(new Date()));
                wxServiceService.insertUser(user);
                msg = "首次登录";
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return ResponseBean.MakeFailRes(e.getLocalizedMessage());
        }
        Cookie cookie = new Cookie("sessionId", user.getId());
        response.addCookie(cookie);
        return ResponseBean.MakeSuccessRes("登录成功", msg);
    }
}
