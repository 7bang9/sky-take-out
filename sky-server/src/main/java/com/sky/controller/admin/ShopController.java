package com.sky.controller.admin;

import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {
    @Autowired
    private RedisTemplate redisTemplate;
    private static final String SHOP_STATUS_KEY = "shopStatus";

    /**
     * 修改店铺状态
     *
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    public Result updateStatus(@PathVariable Integer status) {
        log.info("修改店铺状态：{}", status);
        redisTemplate.opsForValue().set(SHOP_STATUS_KEY, status);
        return Result.success();
    }

    @GetMapping("/status")
    public Result getStatus() {
        Object status = redisTemplate.opsForValue().get(SHOP_STATUS_KEY);
        log.info("获取店铺状态：{}", status);
        return Result.success(status);
    }
}
