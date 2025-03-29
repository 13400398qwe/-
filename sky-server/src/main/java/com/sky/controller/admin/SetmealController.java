package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/setmeal")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新增套餐
     * @param setmealDTO
     */
    @PostMapping("")
    public Result<Object> save(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐：{}",setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     *
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    public Result<PageResult>pageQuery(SetmealPageQueryDTO setmealPageQueryDTO)
    {
        log.info("分页查询套餐：{}",setmealPageQueryDTO);
        PageResult pageResult=setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     *
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public Result<String>setStatus(long id,@PathVariable Integer status)
    {
        log.info("修改套餐状态：{}",status);
        setmealService.startOrStop(status,id);
        return Result.success();
    }
    /**
     * 根据id查询套餐
     */
    @GetMapping("/{id}")
    public Result<SetmealVO> getById(@PathVariable Long id)
    {
        log.info("根据id查询套餐：{}",id);
        SetmealVO setmealVO=setmealService.getById(id);
        return Result.success(setmealVO);
    }
    /**
     * 修改套餐
     * @param setmealDTO
     */
    @PutMapping("")
    public Result<String> update(@RequestBody SetmealDTO setmealDTO)
    {
        log.info("修改套餐：{}",setmealDTO);
        setmealService.update(setmealDTO);
        return Result.success();
    }
    /**
     * 批量删除套餐
     */
    @DeleteMapping("")
    public Result<String> deleteById(@RequestParam List<Long>ids)
    {
        log.info("删除套餐：{}",ids);
        setmealService.deleteByIds(ids);
        return Result.success();
    }
}
