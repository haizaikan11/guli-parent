package com.wulx.guli.service.edu.controller;

import com.wulx.guli.service.base.result.R;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("user")
public class LoginController {

    /**
     * 登录
     * @return
     */
    @PostMapping("login")
    public R login() {

        return R.ok().data("token","admin");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("info")
    public R info() {

        return R.ok()
                .data("roles","[admin]")   //角色
                .data("name","admin")      //名字
                .data("avatar","https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");   //头像
    }

    /**
     * 退出
     * @return
     */
    @PostMapping("logout")
    public R logout(){
        return R.ok();
    }

}
