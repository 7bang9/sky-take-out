package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据id查询套餐菜品
     * @param ids
     * @return
     */
    List<Long> getSetmealDishIds(List<Long> ids);

    /**
     * 批量保存套餐菜品
     * @param setmealDishes
     * @param id
     */
    void save(List<SetmealDish> setmealDishes, Long id);

    /**
     * 根据套餐id删除套餐菜品
     * @param id
     */
    void deleteBySetmealId(Long id);
}
