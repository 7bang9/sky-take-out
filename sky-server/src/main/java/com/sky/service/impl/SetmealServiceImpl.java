package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Override
    public void save(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        //插入套餐基本信息
        setmealMapper.save(setmeal);
        //插入套餐和菜品的关联信息
        if(setmealDTO.getSetmealDishes() != null && setmealDTO.getSetmealDishes().size() > 0){
            setmealDishMapper.save(setmealDTO.getSetmealDishes(), setmeal.getId());
        }

    }

    @Override
    public PageResult page(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());
        Page<SetmealVO> page = setmealMapper.page(setmealPageQueryDTO);

        return new PageResult(page.getTotal(), page.getResult());

    }

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    @Override
    public List<Setmeal> list(Setmeal setmeal) {
        List<Setmeal> list = setmealMapper.list(setmeal);
        return list;
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @Override
    public SetmealVO getById(Long id) {
        //根据id查询套餐基本信息
        Setmeal setmeal = setmealMapper.getById(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        //根据套餐id查询菜品选项
        List<SetmealDish> setmealDishList = setmealMapper.getSetmealDishList(id);
        setmealVO.setSetmealDishes(setmealDishList);
        return setmealVO;
    }

    /**
     * 修改套餐
     * @param setmealDTO
     */
    @Override
    public void update(SetmealDTO setmealDTO) {
        //修改基本信息
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.update(setmeal);

        //删除套餐和菜品的关联信息
        setmealDishMapper.deleteBySetmealId(setmealDTO.getId());

        //添加套餐和菜品的关联信息
        setmealDishMapper.save(setmealDTO.getSetmealDishes(), setmealDTO.getId());
    }

    @Override
    public void updateStatus(Integer status, Long id) {
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        setmeal.setStatus(status);
        setmealMapper.update(setmeal);
    }

    /**
     * 批量删除套餐
     * @param ids
     */
    @Override
    public void delete(List<Long> ids) {
        //判断套餐是否起售
        for(Long id : ids){
            Setmeal setmeal = setmealMapper.getById(id);
            if(setmeal.getStatus() == StatusConstant.ENABLE){
                //起售中的套餐不能删除
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
        }
        //删除套餐表中的数据
        setmealMapper.delete(ids);
        //删除套餐菜品关系表中的数据
        ids.forEach(setmealDishMapper::deleteBySetmealId);
    }
}
