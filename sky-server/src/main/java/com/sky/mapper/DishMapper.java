package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annomation.AutoFill;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DishMapper {
    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @Select("select * from dish where id = #{id}")
    Dish getById(Long id);
    /**
     * 新加菜品
     * @param dish
     */
    @AutoFill(value = OperationType.INSERT)
    void insert(Dish dish);

    /**
     * 修改菜品
     * @param dish
     */
    void update(Dish dish);

    Page pageQuery(DishPageQueryDTO dishPageQueryDTO);

    List<Dish> list(Long categoryId);

    void deleteById(Long id);
}
