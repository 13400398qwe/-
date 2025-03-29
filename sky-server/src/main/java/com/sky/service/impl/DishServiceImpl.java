package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImpl implements DishService {

    @Autowired
    DishMapper dishmapper;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    /**
     * 修改菜品
     */
    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO,dish);
        dishmapper.update(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&& !flavors.isEmpty()){
            //先删除原来的口味
            dishFlavorMapper.deleteByDishId(dish.getId());
            //再添加新的口味
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }
    /**
     * 新增菜品
     */
    @Override
    @Transactional(rollbackFor = Exception.class)//添加事务注解,保证数据库事务一致性
    public void save(DishDTO dishDTO) {
        Dish dish = Dish.builder()
                .name(dishDTO.getName())
                .categoryId(dishDTO.getCategoryId())
                .price(dishDTO.getPrice())
                .image(dishDTO.getImage())
                .description(dishDTO.getDescription())
                .status(dishDTO.getStatus())
                .build();
        dishmapper.insert(dish);
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&& !flavors.isEmpty()){
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dish.getId());
            });
            dishFlavorMapper.insertBatch(flavors);
        }
    }
    /**
     * 分页查询菜品
     *
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page page=dishmapper.pageQuery(dishPageQueryDTO);
        PageResult pageResult=new PageResult();
        pageResult.setRecords(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }
    /**
     * 根据分类id查询菜品
     * @param categoryId
     */
    @Override
    public List list(Long categoryId) {
        List<Dish> list = dishmapper.list(categoryId);
        return list;
    }
    /**
     * 菜品起售停售
     */
    @Override
    public void startOrStop(Integer status, Long id) {
        Dish dish = Dish.builder()
                .id(id)
                .status(status)
                .build();
        dishmapper.update(dish);
    }
    /**
     * 根据id查询菜品
     * @param id
     */
    @Override
    public Dish getById(Long id) {
        Dish dish = dishmapper.getById(id);
        return dish;
    }

    /**
     * 批量删除菜品
     * @param ids
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteByIds(List<Long> ids) {
        ids.forEach(id->{
            dishFlavorMapper.deleteByDishId(id);
        });//删除口味
        ids.forEach(id->{
            dishmapper.deleteById(id);
        });//删除菜品
    }
}
