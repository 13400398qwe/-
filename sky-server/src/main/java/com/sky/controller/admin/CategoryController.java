package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/category")
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    /**
     * 新增分类
     */
    @PostMapping(value = "")
    public Result save(@RequestBody CategoryDTO categoryDTO) {
        log.info("新增分类：{}", categoryDTO);
        categoryService.save(categoryDTO);
        return Result.success();
    }
    /**
     * 分类分页查询
     */
    @GetMapping(value = "/page")
    public Result<Object>page(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("分类分页查询：{}", categoryPageQueryDTO);
        PageResult pageresult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageresult);
    }
    /**
     * 启用禁用分类
     */
    @PostMapping(value = "/status/{status}")
    public Result<String> startOrStop(@PathVariable Integer status, Long id) {
        log.info("启用禁用分类：{}", status, id);
        categoryService.startOrStop(status, id);
        return Result.success();
    }
    /**
     * 修改分类
     */
    @PutMapping(value = "")
    public Result<String> update(@RequestBody CategoryDTO categoryDTO)
    {
        log.info("修改分类：{}", categoryDTO);
        categoryService.update(categoryDTO);
        return Result.success();
    }
    /**
     * 根据类型查询分类
     */
    @GetMapping(value = "/list")
    public Result<Object> list(Integer type) {
        log.info("根据类型查询分类：{}", type);
        List<Category>list=categoryService.list(type);
        return Result.success(list);
    }
    /**
     * 根据id删除分类
     */
    @DeleteMapping(value = "")
    public Result<String> deleteById(Long id) {
        log.info("根据id删除分类：{}", id);
        categoryService.deleteById(id);
        return Result.success();
    }
}
