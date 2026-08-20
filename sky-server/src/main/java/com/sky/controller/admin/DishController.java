package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping
    @Caching(evict = {
            @CacheEvict(cacheNames = "dishCache", key = "#dishDTO.categoryId"),
            @CacheEvict(cacheNames = "setmealDishCache", allEntries = true)
    })
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("保存菜品{}", dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }
    /**
     * 菜品分页查询
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("菜品分页查询{}", dishPageQueryDTO);
        PageResult pageResult = dishService.page(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping()
    @CacheEvict(cacheNames = {"dishCache", "setmealDishCache"}, allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        log.info("删除菜品{}", ids);
        dishService.deleteBatch(ids);
        return Result.success();
    }
    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result getById(@PathVariable Long id) {
        log.info("根据id查询菜品{}", id);
        DishVO dishVO = dishService.getById(id);
        return Result.success(dishVO);
    }
    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @CacheEvict(cacheNames = {"dishCache", "setmealDishCache"}, allEntries = true)
    public Result update(@RequestBody DishDTO dishDTO) {
        log.info("修改菜品{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    /**
     * 菜品起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = {"dishCache", "setmealDishCache"}, allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        log.info("菜品起售停售：{},{}", status, id);
        dishService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 根据分类id查询菜品（此接口由 Claude 编写）
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    public Result<List<Dish>> list(Long categoryId, String name) {
        log.info("根据分类id查询菜品{}或{}", categoryId, name);
        List<Dish> list = dishService.list(categoryId, name);
        return Result.success(list);
    }

    

}
