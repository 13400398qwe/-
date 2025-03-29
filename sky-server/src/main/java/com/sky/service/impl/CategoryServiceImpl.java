package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    /**
     * 新增分类服务实现
     * @param categoryDTO
     * @return
     */
    @Override
    public void save(CategoryDTO categoryDTO)
    {
        Category category=Category.builder()
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .createUser(BaseContext.getCurrentId())
                .updateUser(BaseContext.getCurrentId())
                .build();//构建Category对象
        categoryMapper.insert(category);//执行插入操作
        BaseContext.removeCurrentId();//清除Localthread 中的数据
    }
    /**
     * 分页查询服务实现
     * @param categoryPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO)
    {
        //调用mapper层进行分页查询
        PageHelper pageHelper=new PageHelper();
        pageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        PageResult pageResult=new PageResult();
        pageResult.setRecords(page.getResult());
        pageResult.setTotal(page.getTotal());
        return pageResult;
    }
    /**
     * 启用禁用分类服务实现
     * @param status
     * @param id
     * @return
     */
    @Override
    public void startOrStop(Integer status, Long id)
    {
        Category category=Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category);
        BaseContext.removeCurrentId();
    }
    @Override
    public void update(CategoryDTO categoryDTO)
    {
        Category category=Category.builder()
                .id(categoryDTO.getId())
                .type(categoryDTO.getType())
                .name(categoryDTO.getName())
                .sort(categoryDTO.getSort())
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        categoryMapper.update(category);
        BaseContext.removeCurrentId();
    }
    /**
     * 根据类型查询分类
     * @param type
     * @return
     */
    @Override
    public List list(Integer type)
    {
        Page<Category> page = categoryMapper.list(type);
        List<Category> list = page.getResult();
        return list;
    }
    /**
     * 根据id删除分类实现
     * @param id
     */
    @Override
    public void deleteById(Long id)
    {
        categoryMapper.deleteById(id);
    }
}
