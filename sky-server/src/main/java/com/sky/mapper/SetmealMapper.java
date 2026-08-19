package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper {

    /**
     * 根据分类id查询套餐的数量
     * @param categoryId
     * @return
     */
    @Select("select count(id) from setmeal where category_id = #{categoryId}")
    Integer countByCategoryId(Long categoryId);

    /**
     * 新增套餐
     * @param setmeal
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into setmeal (name, category_id, price, description, image, create_time, update_time, create_user, update_user, status) " +
            "values (#{name}, #{categoryId}, #{price}, #{description}, #{image}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser}, #{status})")
    void save(Setmeal setmeal);

    /**
     * 分页查询套餐
     * @param setmealPageQueryDTO
     * @return
     */
    Page<SetmealVO> page(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * 动态条件查询套餐
     * @param setmeal
     * @return
     */
    List<Setmeal> list(Setmeal setmeal);

    /**
     * 根据套餐id查询菜品选项
     * @param setmealId
     * @return
     */
    @Select("select sd.name, sd.copies, d.image, d.description " +
            "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
            "where sd.setmeal_id = #{setmealId}")
    List<DishItemVO> getDishItemBySetmealId(Long setmealId);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Select("select id, category_id, name, price, status, description, image, create_time, update_time, create_user, update_user from setmeal where id = #{id}")
    Setmeal getById(Long id);

    /**
     * 根据套餐id查询菜品选项
     * @param id
     * @return
     */
    @Select("select id, setmeal_id, dish_id, name, price, copies from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getSetmealDishList(Long id);

    /**
     * 修改套餐
     * @param setmeal
     */
    void update(Setmeal setmeal);

    /**
     * 批量删除套餐
     * @param ids
     */
    void delete(List<Long> ids);
}
