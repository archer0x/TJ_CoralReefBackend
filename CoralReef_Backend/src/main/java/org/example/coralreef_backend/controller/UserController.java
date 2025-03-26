package org.example.coralreef_backend.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.example.coralreef_backend.common.Result;
import org.example.coralreef_backend.dto.UserRequestDto;
import org.example.coralreef_backend.entity.Email;
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
     * @param email 用户实体类参数
     * @return 统一接口返回结果类
     */
    @PostMapping("/createOne")
    public Result<?> createOne(@RequestBody Email email, HttpSession session){
        try {
            int result = userService.createOne(email,session);
            return Result.success(result);
        } catch (RuntimeException e) {
            // 判断错误类型，返回不同的错误代码
            if (e.getMessage().contains("插入用户失败")) {
                return Result.failure(400, "用户插入失败，请检查数据");
            }
            else if(e.getMessage().contains("验证码错误")){
                return Result.failure(400, "验证码错误");
            }
            return Result.failure(500, e.getMessage());
        }
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
     * @param username 主键id
     * @return 统一接口返回结果类
     */
    @DeleteMapping("/deleteOne/{username}")
    public Result<?> deleteOne(@PathVariable String username){
        return Result.success(userService.deleteOne(username));
    }

    /**
     * 批量删除
     * @param idList id集合
     * @return 统一接口返回结果类
     */
    @DeleteMapping("/deleteBatch")
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
     * @param username 主键id
     * @return 统一接口返回结果类
     */
    @GetMapping("/findOne/{username}")
    public Result<?> findOne(@PathVariable String username){
        return Result.success(userService.findOne(username));
    }

}
