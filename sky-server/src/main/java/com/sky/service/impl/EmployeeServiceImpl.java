package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeService employeeService;
    private PageHelper pageHelper;
    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());//密码加密
        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //密码比对
        // TODO 后期需要进行md5加密，然后再进行比对
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }
    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    public void save(EmployeeDTO employeeDTO) {
        //设置初始密码123456，需要进行md5加密处理
        Employee employee = new Employee();
        //对象的属性拷贝
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));//密码加密
        employee.setStatus(StatusConstant.ENABLE);//启用状态
        employee.setCreateTime(LocalDateTime.now());//设置创建时间和修改时间
        employee.setUpdateTime(LocalDateTime.now());//设置创建时间和修改时间
        employee.setCreateUser(BaseContext.getCurrentId());
        employee.setUpdateUser(BaseContext.getCurrentId());
        /*清除Localthread 中的数据*/
        BaseContext.removeCurrentId();
        employeeMapper.save(employee);

    }
    /**
     * 员工分页查询
     * @param employeePageQueryDTO
     * @return
     */
    public PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO) {
        /**
         *借助PageHelper对result分页
         */
        pageHelper.startPage(employeePageQueryDTO.getPage(),employeePageQueryDTO.getPageSize());//设置分页参数
        Page<Employee>page=employeeMapper.pageQuery(employeePageQueryDTO);//分页查询
        /*
          将page转换为pageresult
         */
        PageResult pageResult=new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRecords(page.getResult());//设置分页数据
        return pageResult;
    }
    /**
     * 启用禁用员工账号
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .updateTime(LocalDateTime.now())
                .updateUser(BaseContext.getCurrentId())
                .build();
        employeeMapper.update(employee);
        /**清除Localthread 中的数据*/
        BaseContext.removeCurrentId();
    }
    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    public void update(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser(BaseContext.getCurrentId());
        employeeMapper.update(employee);
        /**清除Localthread 中的数据*/
        BaseContext.removeCurrentId();
    }
    public Employee getById(Long id) {
        Employee employee = employeeMapper.getById(id);
        employee.setPassword("****");
        return employee;
    }
}
