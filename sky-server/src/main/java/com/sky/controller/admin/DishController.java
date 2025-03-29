package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/dish")
@Slf4j
public class DishController {
    @Autowired
    DishService dishService;
    @Autowired
    DishFlavorMapper dishFlavorMapper;
    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping("")
    public Result<String> update(@RequestBody DishDTO dishDTO)
    {
        log.info("修改菜品：{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }
    /**
     * 新增菜品
     * @param dishDTO
     * @return
     */
    @PostMapping("")
    public Result<String> save(@RequestBody DishDTO dishDTO)
    {
        log.info("新增菜品：{}", dishDTO);
        dishService.save(dishDTO);
        return Result.success();
    }
    /**
     * 分页查询菜品
     */
    @GetMapping("/page")
    public Result<Object> pageQuery(DishPageQueryDTO dishPageQueryDTO)
    {
        log.info("分页查询菜品：{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }
    /**
     * 根据分类id查询菜品
     * @param categoryId
     */
    @GetMapping("/list")
    public Result<Object> list(Long categoryId)
    {
        log.info("根据分类id查询菜品：{}", categoryId);
        List list = dishService.list(categoryId);
        return Result.success(list);
    }
    /**
     * 菜品起售停售
     * @param status
     * @param id
     */
    @PostMapping("/status/{status}")
    public Result<String> startOrStop(@PathVariable Integer status, Long id)
    {
        log.info("菜品起售停售：{}", status);
        dishService.startOrStop(status, id);
        return Result.success();
    }
    /**
     * 根据id查询菜品
     * @param id
     */
    @GetMapping("/{id}")
    public Result<DishVO> getById(@PathVariable Long id)
    {
        log.info("根据id查询菜品：{}", id);
        Dish dish = dishService.getById(id);
        DishVO dishVO = new DishVO();
        BeanUtils.copyProperties(dish, dishVO);
        /**
         * 根据id查询菜品口味
         */
        dishVO.setFlavors(dishFlavorMapper.getByDishId(id));
        log.info("菜品信息：{}", dishVO);
        return Result.success(dishVO);
    }
    /**
     * 批量删除菜品
     * @param ids
     */
    @DeleteMapping("")
    public Result<String> deleteByIds(@RequestParam List<Long> ids)
    {
        log.info("批量删除菜品：{}", ids);
        dishService.deleteByIds(ids);
        return Result.success();
    }

}
