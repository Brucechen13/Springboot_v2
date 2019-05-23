package com.fc.test.controller;

import com.fc.test.common.base.BaseController;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
            item.addProperty("title", "cc");
            item.addProperty("content", "cc");
            item.addProperty("status", "cc");
            item.addProperty("time", "2019-5-23");
            JsonArray flags = new JsonArray();
            for(int ii = 0; ii < 3; ii ++){
                flags.add("flag" + ii);
            }
            item.add("flag", flags);
            array.add(item);
        }
        result.add("data", array);
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

        String sendGet=wxServiceService.loginByWeixin(code); //根据code去调用接口获取用户openid和session_key

        JsonObject json = new JsonParser().parse(sendGet).getAsJsonObject();
        System.out.println("返回过来的json数据:"+json.toString());
        String sessionkey=json.get("session_key").toString(); //会话秘钥
        String openid=json.get("openid").toString(); //用户唯一标识
        try{
            int a;
//            //拿到用户session_key和用户敏感数据进行解密，拿到用户信息。
//            String decrypts=AesCbcUtil.decrypt(encryptedData,sessionkey,iv,"utf-8");//解密
//            JsonObject jsons = JsonObject.fromObject(decrypts);
//            String nickName=jsons.get("nickName").toString(); //用户昵称
//            String jsonsds=jsons.get("avatarUrl").toString(); //用户头像
//            jsons.get("avatarUrl").toString(); //头像
//            jsons.get("gender").toString();//性别
//            jsons.get("unionid").toString(); //unionid
//            jsons.get("city").toString(); //城市
//            jsons.get("province").toString();//省份
//            jsons.get("country").toString(); //国家
        }catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
