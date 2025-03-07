package org.example.coralreef_backend.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.example.coralreef_backend.entity.User;
import org.example.coralreef_backend.mapper.UserMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 用户身份验证管理类
 */
@Component
public class DBUserDetailsManager implements UserDetailsService {

    @Resource
    private UserMapper userMapper;



    /**
     * 从数据库中获取用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        User user = userMapper.selectOne(userQueryWrapper);

        if(Objects.isNull(user)){
            throw new UsernameNotFoundException("用户 " + username + " 未找到");
        }else{

            // 获取用户角色和权限
            List<GrantedAuthority> authorities = getUserAuthorities(user);
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .disabled("1".equals(user.getEnabled()))
                    .credentialsExpired(false)
                    .accountLocked(false)
                    .authorities(authorities)
                    .build();
        }
    }

    /**
     * 获取用户权限
     * @param user 用户实体
     * @return 用户权限集合
     */
    private List<GrantedAuthority> getUserAuthorities(User user) {
        // 创建一个空的权限列表
        List<GrantedAuthority> authorities = new ArrayList<>();

        // 添加用户的权限（暂不从数据库获取）
        authorities.add(new SimpleGrantedAuthority("ADMIN"));

        return authorities;
    }

}
