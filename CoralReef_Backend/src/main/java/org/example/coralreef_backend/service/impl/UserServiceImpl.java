package org.example.coralreef_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.example.coralreef_backend.dto.UserRequestDto;
import org.example.coralreef_backend.entity.User;
import org.example.coralreef_backend.service.UserService;
import org.example.coralreef_backend.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
* @author xzw
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-03-07 20:58:21
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService{

    @Resource
    public UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 创建单条数据
     * @param user 用户实体类参数
     * @return int
     */
    @Override
    public int createOne(User user) {
        Assert.notNull(user, "用户实体类参数不能为空");
        Assert.hasText(user.getPassword(), "密码不能为空");

        user.setEnabled(1);
        //密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());

        try {
            int result = userMapper.insert(user);
            if (result == 0) {
                throw new RuntimeException("插入用户失败");
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("插入用户数据时发生错误: " + e.getMessage(), e);
        }
    }


    /**
     * 修改单条数据
     * @param user 用户实体类参数
     * @return int
     */
    @Override
    public int updateOne(User user) {

        Assert.notNull(user, "用户实体类参数不能为空");
        Assert.notNull(user.getId(), "用户主键ID不能为空");
        if(user.getPassword() != null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userMapper.updateById(user);
    }

    /**
     * 删除单条数据
     * @param username 主键
     * @return int
     */
    @Override
    public int deleteOne(String username) {

        Assert.notNull(username, "主键username不能为空");

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        //根据id指定单条数据
        updateWrapper.lambda().eq(User::getUsername,username);
        //将 删除标记 置为1
        updateWrapper.lambda().set(User::getDelFlag,1);

        return userMapper.update(updateWrapper);

    }

    /**
     * 批量删除
     * @param idList 主键list
     * @return int
     */
    public int deleteBatch(List<Long> idList){

        Assert.notEmpty(idList, "主键id的list集合不能为空");

        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        //根据idList指定若干条数据
        updateWrapper.lambda().in(User::getId,idList);
        //将 删除标记 置为1
        updateWrapper.lambda().set(User::getDelFlag,1);

        return userMapper.update(updateWrapper);

    }

    /**
     * 查询多条数据
     * @param userRequestDto 用户请求实体类参数
     * @return IPage<User>
     */
    @Override
    public IPage<User> findList(UserRequestDto userRequestDto) {

        Assert.notNull(userRequestDto, "用户请求实体类参数不能为空");

        //分页参数
        Page<User> page = new Page<>(
                userRequestDto.getPageNum()==null?1:userRequestDto.getPageNum(),
                userRequestDto.getPageSize()==null?10:userRequestDto.getPageSize()
        );

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

        return userMapper.selectPage(page,queryWrapper);

    }

    /**
     * 单条查询
     * @param username 主键
     * @return User
     */
    @Override
    public User findOne(String username) {

        Assert.notNull(username, "主键id不能为空");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername,username);
        return userMapper.selectOne(queryWrapper);

    }

}





