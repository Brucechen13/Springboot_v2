package com.fc.test.controller;

import com.fc.test.model.auto.WxCollect;
import com.fc.test.model.auto.WxComment;
import com.fc.test.model.auto.WxPost;
import com.fc.test.model.auto.WxUser;
import com.fc.test.model.custom.TableSplitResult;
import com.fc.test.model.custom.Tablepar;
import com.fc.wx.bean.ResponseBean;
import com.fc.wx.bean.WxPostFrontBean;
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
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("wx")
@Api(value = "微信接口")
public class WXController extends BaseController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    private void printHeader(HttpServletRequest request){

        Map<String,String[]> requestMsg = request.getParameterMap();
        Enumeration<String> requestHeader = request.getHeaderNames();

        System.out.println("------- header -------");
        while(requestHeader.hasMoreElements()){
            String headerKey=requestHeader.nextElement().toString();
            //打印所有Header值

            System.out.println("headerKey="+headerKey+";value="+request.getHeader(headerKey));
        }
    }

    @ApiOperation(value="测试header",notes="测试header")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public Object test(HttpSession session, HttpServletRequest request){
        printHeader(request);
        session.setAttribute("userid", "123");
        return  ResponseBean.MakeSuccessRes("test header", null);
    }

    @ApiOperation(value="查看所有动态",notes="查看所有动态")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(Tablepar tablepar){
        PageInfo<WxPost> page=wxServiceService.listPosts(tablepar) ;
        TableSplitResult<WxPost> result=new TableSplitResult<>(page.getPageNum(), (long)page.getPages(), page.getList());
        return  ResponseBean.MakeSuccessRes("Post List", result);
    }

    @ApiOperation(value="查看发布的动态",notes="查看发布的动态")
    @RequestMapping(value = "/listowner", method = RequestMethod.GET)
    @ResponseBody
    public Object listOwner(@SessionAttribute("userid") String userId, Tablepar tablepar){
        PageInfo<WxPost> page=wxServiceService.listOwnList(userId, tablepar) ;
        TableSplitResult<WxPost> result=new TableSplitResult<>(page.getPageNum(), (long)page.getPages(), page.getList());
        return  ResponseBean.MakeSuccessRes("listowner", result);
    }

    @ApiOperation(value="查看收藏的动态",notes="查看收藏的动态")
    @RequestMapping(value = "/listcollect", method = RequestMethod.GET)
    @ResponseBody
    public Object listCollector(@SessionAttribute("userid") String userId, Tablepar tablepar){
        PageInfo<WxPost> page=wxServiceService.listCollectList(userId, tablepar) ;
        TableSplitResult<WxPost> result=new TableSplitResult<>(page.getPageNum(), (long)page.getPages(), page.getList());
        return  ResponseBean.MakeSuccessRes("listcollect", result);
    }

    @ApiOperation(value="查看评论的动态",notes="查看评论的动态")
    @RequestMapping(value = "/listcomment", method = RequestMethod.GET)
    @ResponseBody
    public Object listComment(@SessionAttribute("userid") String userId, Tablepar tablepar){
        PageInfo<WxPost> page=wxServiceService.listCommentList(userId, tablepar) ;
        TableSplitResult<WxPost> result=new TableSplitResult<>(page.getPageNum(), (long)page.getPages(), page.getList());
        return  ResponseBean.MakeSuccessRes("listcomment", result);
    }

    @ApiOperation(value="查看动态",notes="查看动态")
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    public Object getDetail(String postid){
        WxPost page=wxServiceService.getPost(postid);
        return  ResponseBean.MakeSuccessRes("Post Detail", page);
    }

    @ApiOperation(value="添加新动态",notes="{'titleIntro':xx, 'taskDiscribe':xx, 'startime':xx, 'endtime':xx, 'selectedTag':[], 'class':xx}")
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBean addPost(@SessionAttribute("userid") String userId, @RequestBody WxPostFrontBean bean) {
        // 直接将json信息打印出来
        System.out.println("add post: " + bean.getTitleIntro() + " " + userId);
//        JsonObject jsonParam = new JsonParser().parse(json).getAsJsonObject();
        WxPost post = new WxPost();
        post.setUserid(userId);
        post.setTitle(bean.getTitleIntro());
        post.setContent(bean.getTaskDiscribe());
        post.setClasses(bean.getClasses());
        if(bean.getStartime().equals("")){
            post.setBegintime(sdf.format(new Date()));
        }else {
            post.setBegintime(bean.getStartime());
        }
        if(bean.getEndtime().equals("")){
            post.setEndtime(sdf.format(new Date()));
        }else {
            post.setEndtime(bean.getEndtime());
        }

        if(bean.getSelectedTag() == null || bean.getSelectedTag().size() == 0){
            post.setFlagstr("");
        }else {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bean.getSelectedTag().size(); i ++) {
                sb.append(bean.getSelectedTag().get(i).toString() + AppUtil.SEP);
            }
            post.setFlagstr(sb.toString());
        }

        post.setStatus(AppUtil.ACTIVATE);
        wxServiceService.insertPost(post);

        return ResponseBean.MakeSuccessRes("添加动态成功", null);
    }

    @ApiOperation(value="添加动态评论",notes="添加动态评论")
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBean addComment(HttpServletRequest request, @SessionAttribute("userid") String userId, String postid, String content) {

        printHeader(request);
        System.out.println("comment, sessId: " + userId);
        WxComment comment = new WxComment();
        comment.setContent(content);
        comment.setPostid(postid);
        comment.setUserid(userId);
        comment.setStatus("");
        comment.setPosttime(sdf.format(new Date()));
        wxServiceService.insertComment(comment);

        return ResponseBean.MakeSuccessRes("添加评论成功", content);
    }

    @ApiOperation(value="查看动态是否被收藏",notes="查看动态是否被收藏")
    @RequestMapping(value = "/iscollect", method = RequestMethod.GET)
    @ResponseBody
    public Object isCollect(@SessionAttribute("userid") String userid, String postid){
        System.out.println("isCollect, sessId: " + userid + " " + postid);
        boolean page = wxServiceService.isCollect(userid, postid);
        return  ResponseBean.MakeSuccessRes("iscollect", page);
    }

    @ApiOperation(value="添加动态收藏",notes="添加动态收藏")
    @ResponseBody
    @RequestMapping(value = "/collect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBean addCollect(@SessionAttribute("userid") String userId, String postid) {
        System.out.println("collect, sessId: " + userId + " " + postid);
        WxCollect collect = new WxCollect();
        collect.setPostid(postid);
        collect.setUserid(userId);
        collect.setCollecttime(sdf.format(new Date()));
        wxServiceService.insertCollect(collect);

        return ResponseBean.MakeSuccessRes("收藏动态成功", null);
    }

    @ApiOperation(value="取消动态收藏",notes="取消动态收藏")
    @ResponseBody
    @RequestMapping(value = "/uncollect", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseBean unCollect(@SessionAttribute("userid") String userId, String postid) {
        System.out.println("comment, sessId: " + userId);
        wxServiceService.deleteCollect(userId, postid);

        return ResponseBean.MakeSuccessRes("取消收藏成功", null);
    }

    @ApiOperation(value="用户登录",notes="用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean loginByWeixin(HttpSession session, String code, String nickName, String avatarUrl) {
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
        session.setAttribute("userid", user.getId());
        return ResponseBean.MakeSuccessRes("登录成功", msg);
    }
}
