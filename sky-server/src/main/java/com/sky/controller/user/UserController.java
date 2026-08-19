package com.sky.controller.user;

import com.sky.dto.UserLoginDTO;
import com.sky.result.Result;
import com.sky.service.LoginService;
import com.sky.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/user")

public class UserController {
    @Autowired
    private LoginService loginService;

    /**
     * 用户登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginDTO userLoginDTO){
        log.info("用户登录：{}", userLoginDTO);
        UserLoginVO userLoginVO = loginService.login(userLoginDTO);
        return Result.success(userLoginVO);
    }
}
