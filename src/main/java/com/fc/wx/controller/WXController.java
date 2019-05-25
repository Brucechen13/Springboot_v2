package com.fc.wx.controller;

import com.fc.test.model.auto.WxUser;
import com.fc.wx.common.AesCbcUtil;
import com.fc.wx.common.AppUtil;
import com.fc.wx.common.HttpRequest;
import com.fc.test.common.base.BaseController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
@RequestMapping("wx")
@Api(value = "微信接口")
public class WXController extends BaseController {
    @ApiOperation(value="测试",notes="测试")
    @ResponseBody
    @RequestMapping("/test")
    public String index() {
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

        WxUser user = new WxUser();
        user.setOpenid("0001");
        user.setSessionkey("0001");
        user.setNickname("0001");
        user.setAvatarurl("0001");
        user.setSign("0001");
        user.setLasttime(new Date());
        wxServiceService.insert(user);

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String writeByBody(@RequestBody JsonObject jsonParam) {
        // 直接将json信息打印出来
        System.out.println(jsonParam.toString());

        // 将获取的json数据封装一层，然后在给返回
        JsonObject result = new JsonObject();
        result.addProperty("msg", "ok");
        result.addProperty("method", "@ResponseBody");

        return result.toString();
    }

    @RequestMapping("/login")
    @ResponseBody
    public String loginByWeixin(String code, String encryptedData, String iv)
    {

         WxUser user = wxServiceService.loginByWeixin(code); //根据code去调用接口获取用户openid和session_key
        JsonObject obj = new JsonObject();
        if(user == null){
            String url = AppUtil.wxLoginUrl;
            String param = "appid=" + AppUtil.appId + "&secret=" + AppUtil.secret + "&js_code=" + code + "&grant_type=authorization_code";
            try {
                String ret = HttpRequest.sendGet(url, param);
                System.out.println(ret);
                JsonObject res = new JsonParser().parse(ret).getAsJsonObject();
                String openid = res.get("openid").toString();
                String sessionkey = res.get("session_key").toString();
                String decrypts= AesCbcUtil.decrypt(encryptedData,sessionkey,iv,"utf-8");//解密
                JsonObject jsons = new JsonParser().parse(decrypts).getAsJsonObject();
                String nickName=jsons.get("nickName").toString(); //用户昵称
                String jsonsds=jsons.get("avatarUrl").toString(); //用户头像
                obj.addProperty("status", 0);
                obj.add("data", jsons);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
                obj.addProperty("status", 1);
            }
        }else{
            obj.addProperty("status", 0);
        }
        return obj.toString();
    }
}
