package com.sky.controller.user;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String SHOP_STATUS_KEY = "shopStatus";

    /**
     * 获取店铺状态
     *
     * @return
     */
    @GetMapping("/status")
    public Result getStatus() {
        Object status = redisTemplate.opsForValue().get(SHOP_STATUS_KEY);
        log.info("获取店铺状态：{}", status);
        return Result.success(status);
    }
}
