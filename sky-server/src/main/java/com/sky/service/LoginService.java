package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.vo.UserLoginVO;

public interface LoginService {
    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);
}
