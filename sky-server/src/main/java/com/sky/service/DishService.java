package com.sky.service;


import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;

import java.util.List;

public interface DishService {

    /**
     * 修改菜品
     * @param dishDTO
     */
    void update(DishDTO dishDTO);

    void save(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    List list(Long categoryId);

    void startOrStop(Integer status, Long id);

    Dish getById(Long id);

    void deleteByIds(List<Long> ids);
}
