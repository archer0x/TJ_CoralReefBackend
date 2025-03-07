package org.example.coralreef_backend.controller;

import jakarta.annotation.Resource;
import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.dto.UserRequestDto;
import org.example.coralreef_backend.entity.User;
import org.example.coralreef_backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Resource
    public UserService userService;

    /**
     * 创建一条数据
     * @param user 用户实体类参数
     * @return 统一接口返回结果类
     */
    @PostMapping("/createOne")
    public Result<?> createOne(@RequestBody User user){
        return Result.success(userService.createOne(user));
    }

    /**
     * 修改一条数据
     * @param user 用户实体类参数
     * @return 统一接口返回结果类
     */
    @PostMapping("/updateOne")
    public Result<?> updateOne(@RequestBody User user){
        return Result.success(userService.updateOne(user));
    }

    /**
     * 删除一条数据
     * @param id 主键id
     * @return 统一接口返回结果类
     */
    @PostMapping("/deleteOne/{id}")
    public Result<?> deleteOne(@PathVariable Long id){
        return Result.success(userService.deleteOne(id));
    }

    /**
     * 批量删除
     * @param idList 主键id集合
     * @return 统一接口返回结果类
     */
    @PostMapping("/deleteBatch")
    public Result<?> deleteBatch(@RequestBody List<Long> idList){
        return Result.success(userService.deleteBatch(idList));
    }

    /**
     * 分页查询多条数据
     * @param userRequestDto 用户请求实体类参数
     * @return 统一接口返回结果类
     */
    @PostMapping("/findList")
    public Result<?> findList(@RequestBody UserRequestDto userRequestDto){
        return Result.success(userService.findList(userRequestDto));
    }

    /**
     * 查询一条数据
     * @param id 主键id
     * @return 统一接口返回结果类
     */
    @GetMapping("/findOne/{id}")
    public Result<?> findOne(@PathVariable Long id){
        return Result.success(userService.findOne(id));
    }

}
