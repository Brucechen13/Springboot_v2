package com.fc.wx.controller;

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

    String SEP = "&%$";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @ApiOperation(value="测试",notes="测试")
    @ResponseBody
    @RequestMapping("/test")
    public String index(HttpServletResponse response) {
        // 将获取的json数据封装一层，然后在给返回
        JsonObject result = new JsonObject();
        result.addProperty("status", "0");
        JsonArray array = new JsonArray();
        for(int i = 0; i < 20; i ++){
            JsonObject item = new JsonObject();
            item.addProperty("userpic", "");
            item.addProperty("userName", "cc");
            item.addProperty("userSign", "签名");
            item.addProperty("title", "cc");
            item.addProperty("content", "cc");
            item.addProperty("status", "cc");
            item.addProperty("beginTime", "2019-5-23");
            item.addProperty("endTime", "2019-5-23");
            JsonArray flags = new JsonArray();
            for(int ii = 0; ii < 3; ii ++){
                flags.add("flag" + ii);
            }
            item.add("flag", flags);
            array.add(item);
        }
        result.add("data", array);


        Cookie cookie=new Cookie("id", "123");
        response.addCookie(cookie);

        return result.toString();
    }

    @GetMapping("/article")
    @ResponseBody
    public ResponseBean article() {
        List<WxUser> users = new ArrayList<>();
        for(int i = 0; i < 10; i ++){
            WxUser user = new WxUser();
            user.setNickname("asd");
            users.add(user);
        }
        return ResponseBean.MakeSuccessRes("You are already logged in", users);
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(Tablepar tablepar){
        PageInfo<WxUser> page=wxServiceService.list(tablepar) ;
        TableSplitResult<WxUser> result=new TableSplitResult<WxUser>(page.getPageNum(), (long)page.getPages(), page.getList());
        return  ResponseBean.MakeSuccessRes("Post List", result);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseBean writeByBody(@CookieValue("sessionId") String userId, @RequestBody JsonObject jsonParam) {
        // 直接将json信息打印出来
        System.out.println(jsonParam.toString());
        WxPost post = new WxPost();
        post.setUserid(userId);
        post.setTitle(jsonParam.get("title").toString());
        post.setContent(jsonParam.get("content").toString());
        try {
            post.setBegintime(sdf.parse(jsonParam.get("beginTime").toString()));
            post.setBegintime(sdf.parse(jsonParam.get("beginTime").toString()));
        }catch (ParseException e){
            return ResponseBean.MakeFailRes("时间格式出错： " + jsonParam.toString());
        }
        if(jsonParam.getAsJsonArray("flags").size() == 0){
            post.setFlags(null);
        }else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < jsonParam.getAsJsonArray("flags").size(); i ++) {
                sb.append(jsonParam.getAsJsonArray("flags").get(i).toString() + SEP);
            }
            post.setFlags(sb.toString());
        }
        post.setStatus("进行中");
        wxServiceService.insertPost(post);

        return ResponseBean.MakeSuccessRes("添加动态成功", null);
    }

    @RequestMapping("/login")
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

            user = wxServiceService.loginByWeixin(code); //根据code去调用接口获取用户openid和session_key
            if (user == null) {
                user = new WxUser();
                user.setOpenid(openid);
                user.setSessionkey(sessionkey);
                user.setNickname(nickName);
                user.setAvatarurl(avatarUrl);
                user.setSign("该用户尚未设置签名");
                user.setLasttime(new Date());
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
