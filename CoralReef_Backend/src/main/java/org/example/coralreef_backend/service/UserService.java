package org.example.coralreef_backend.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.servlet.http.HttpSession;
import org.example.coralreef_backend.dto.UserRequestDto;
import org.example.coralreef_backend.entity.Email;
import org.example.coralreef_backend.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author xzw
* @description 针对表【user】的数据库操作Service
* @createDate 2025-03-07 20:58:21
*/
public interface UserService extends IService<User> {
    /**
     * 创建单条数据
     */
    int createOne(Email email, HttpSession session);

    /**
     * 创建单条数据
     */
    int updateOne(User user);

    /**
     * 删除单条数据
     */
    int deleteOne(String username);

    /**
     * 批量删除
     */
    int deleteBatch(List<Long> idList);

    /**
     * 查询多条数据
     */
    IPage<User> findList(UserRequestDto userRequestDto);

    /**
     * 单条查询
     */
    User findOne(String username);
}
