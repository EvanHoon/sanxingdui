package com.evan.controller.admin;

import com.evan.constant.LogActions;
import com.evan.constant.WebConst;
import com.evan.controller.BaseController;
import com.evan.exception.BusinessException;
import com.evan.model.UserDomain;
import com.evan.service.log.LogService;
import com.evan.service.user.UserService;
import com.evan.utils.APIResponse;
import com.evan.utils.TaleUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Api("登录相关接口")
@Controller
@RequestMapping("/admin")
public class AuthController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;


    @ApiOperation("跳转登录页")
    @GetMapping(value = "/login")
    public String login() {
        return "admin/login";
    }

    @ApiOperation("跳转注册页")
    @GetMapping(value = "/register")
    public String register() {
        return "admin/register";
    }


    @ApiOperation("登录")
    @PostMapping(value = "/login")
    @ResponseBody
    public APIResponse toLogin(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(name = "username", value = "用户名", required = true)
            @RequestParam(name = "username", required = true)
            String username,
            @ApiParam(name = "password", value = "用户名", required = true)
            @RequestParam(name = "password", required = true)
            String password,
            @ApiParam(name = "remember_me", value = "记住我", required = false)
            @RequestParam(name = "remember_me", required = false)
            String remember_me
    ) {
        Integer error_count = cache.get("login_error_count");
        try {
            // 调用Service登录方法
            UserDomain userInfo = userService.login(username, password);
            // 设置用户信息session
            request.getSession().setAttribute(WebConst.LOGIN_SESSION_KEY, userInfo);
            // 判断是否勾选记住我
            if (StringUtils.isNotBlank(remember_me)) {
                TaleUtils.setCookie(response, userInfo.getUid());
            }
            // 写入日志
            logService.addLog(LogActions.LOGIN.getAction(), userInfo.getUsername()+"用户", request.getRemoteAddr(), userInfo.getUid());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            error_count = null == error_count ? 1 : error_count + 1;
            if (error_count > 3) {
                return APIResponse.fail("您输入密码已经错误超过3次，请10分钟后尝试");
            }
            System.out.println(error_count);
            // 设置缓存为10分钟
            cache.set("login_error_count", error_count, 10 * 60);
            String msg = "登录失败";
            if (e instanceof BusinessException) {
                msg = e.getMessage();
            } else {
                LOGGER.error(msg,e);
            }
            return APIResponse.fail(msg);
        }
        // 返回登录成功信息
        return APIResponse.success();
    }

    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    @ApiOperation("注册")
    @PostMapping(value = "/register")
    @ResponseBody
    public APIResponse toRegister(
            HttpServletRequest request,
            HttpServletResponse response,
            @ApiParam(name = "username", value = "用户名", required = true)
            @RequestParam(name = "username", required = true)
                    String username,
            @ApiParam(name = "password1", value = "密码1", required = true)
            @RequestParam(name = "password1", required = true)
                    String password1,
            @ApiParam(name = "password2", value = "密码2", required = true)
            @RequestParam(name = "password2", required = true)
                    String password2,
            @ApiParam(name = "email", value = "邮箱", required = true)
            @RequestParam(name = "email", required = true)
                    String email,
            @ApiParam(name = "screenName", value = "姓名", required = true)
            @RequestParam(name = "screenName", required = true)
                    String screenName
    ) {
        try {
            if (!checkEmail(email)){
                return APIResponse.fail("邮箱格式错误，请检查后重试");
            }
            if (!StringUtils.equals(password1,password2)){
                return APIResponse.fail("两次输入密码不一致，请检查密码");
            }

            // 调用Service注册方法
            Integer resultInt = userService.register(username, password1,email,screenName);
            if (resultInt == 3){
                return APIResponse.fail("任一输入项不能为空");
            }else if (resultInt == 4){
                return APIResponse.fail("该账号已注册，请换一个重试");
            }else if (resultInt == null || resultInt == 0){
                return APIResponse.fail("注册失败，该用户已存在");
            }
            if (password1.length() < 6 || password1.length() > 14) {
                return APIResponse.fail("请输入6-14位密码");
            }
            if (username.length() < 4) {
                return APIResponse.fail("请输入4位以上账号");
            }
            if (username.length() > 16){
                return APIResponse.fail("请输入16位以下账号");
            }

        }catch (Exception e){
            if (e.getMessage().contains("key \'mail\'")){
                return APIResponse.fail("该邮箱已注册，请换一个重试");
            }
            if (e.getMessage().contains("key \'username\'")){
                return APIResponse.fail("该账号已注册，请换一个重试");
            }
            return APIResponse.fail(e.getMessage());
        }

        // 返回登录成功信息
        return APIResponse.success();
    }

    @RequestMapping(value = "/logout")
    public void logout(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        // 移除session
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        // 设置cookie值和时间为空
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
        cookie.setValue(null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            // 跳转到登录页面
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("注销失败",e);
        }
    }

    @RequestMapping(value = "/update1")
    public String update1(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                       @ApiParam(name = "username", value = "用户名", required = true)
            @RequestParam(name = "username", required = true)
                    String username,
            @ApiParam(name = "password1", value = "密码1", required = true)
            @RequestParam(name = "password1", required = true)
                    String password1,
            @ApiParam(name = "password2", value = "密码2", required = true)
            @RequestParam(name = "password2", required = true)
                    String password2
                       ) {

        if(!password1.equals(password2)){
            return "/admin/setting";
        }
        UserDomain user = new UserDomain();
        user.setUsername(username);
        user.setPassword(password1);
        int resultInt = userService.updateUserInfo(user);

        // 移除session
        session.removeAttribute(WebConst.LOGIN_SESSION_KEY);
        // 设置cookie值和时间为空
        Cookie cookie = new Cookie(WebConst.USER_IN_COOKIE, "");
        cookie.setValue(null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
        try {
            // 跳转到登录页面
            response.sendRedirect("/admin/login");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("注销失败",e);
        }
        return "/admin/login";
    }

    @RequestMapping(value = "/update2")
    public Map update2(HttpSession session, HttpServletRequest request, HttpServletResponse response,
                       @ApiParam(name = "username", value = "用户名", required = true)
                       @RequestParam(name = "username", required = true)
                               String username,
                       @ApiParam(name = "email", value = "邮箱", required = true)
                       @RequestParam(name = "email", required = true)
                               String email,
                       @ApiParam(name = "screenName", value = "姓名", required = true)
                       @RequestParam(name = "screenName", required = true)
                               String screenName
    ) {

        UserDomain user = new UserDomain();
        user.setUsername(username);
        user.setEmail(email);
        user.setScreenName(screenName);
        int resultInt = userService.updateUserInfo(user);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("result","success");
        return map;
    }



}
