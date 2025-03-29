package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishFlavorMapper {
    /**
     * 根据菜品id查询口味
     */
    public void deleteByDishId(Long dishId);//

    public void insertBatch(List<DishFlavor> dishFlavorList);//

    public List<DishFlavor> getByDishId(Long dishId);//
}
