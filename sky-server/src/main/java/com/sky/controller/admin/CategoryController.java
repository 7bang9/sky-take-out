package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 新增分类
     * @param categoryDTO
     * @return
     */
    @PostMapping
    public Result save(@RequestBody CategoryDTO categoryDTO){
        log.info("新增分类: {}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result page(CategoryPageQueryDTO categoryPageQueryDTO){
        log.info("分类分页查询: {}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 根据类型查询分类列表
     * @param type
     * @return
     */
    @GetMapping("/list")
    public Result<List<Category>> list(Integer type){
        log.info("分类列表查询: {}", type);
        List<Category> list = categoryService.list(type);
        return Result.success(list);
    }

    /**
     * 启用禁用分类
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, Long id){
        log.info("分类启禁用：{}，{}",status,id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }

    /**
     * 修改分类
     * @param categoryDTO
     * @return
     */
    @PutMapping()
    public Result update(@RequestBody CategoryDTO categoryDTO){
        log.info("分类修改: {}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id){
        log.info("分类查询: {}", id);
        Category category = categoryService.getById(id);
        return Result.success(category);
    }

    /**
     * 删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public Result deleteById(Long id){
        log.info("分类删除: {}", id);
        categoryService.deleteById(id);
        return Result.success();
    }
}
