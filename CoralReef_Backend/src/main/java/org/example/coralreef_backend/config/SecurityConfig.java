package org.example.coralreef_backend.config;

import jakarta.annotation.Resource;
import org.example.coralreef_backend.common.DBUserDetailsManager;
import org.example.coralreef_backend.common.JwtAuthenticationFilter;
import org.example.coralreef_backend.security.handler.MyAuthenticationEntryPoint;
import org.example.coralreef_backend.security.handler.MyAuthenticationFailureHandler;
import org.example.coralreef_backend.security.handler.MyAuthenticationSuccessHandler;
import org.example.coralreef_backend.security.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@EnableMethodSecurity //开启方法授权
@Configuration
public class SecurityConfig {

    @Resource
    private DBUserDetailsManager userDetailsManager;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {

        //默认编码器为BCrypt
        String defaultEncode = "bcrypt";
        Map<String, PasswordEncoder> encoderMap = new HashMap<>();

        // 添加BCrypt编码器，并自定义工作因子
        encoderMap.put(defaultEncode, new BCryptPasswordEncoder(4));
        // 添加NoOp编码器
        encoderMap.put("noop", NoOpPasswordEncoder.getInstance());

        return new DelegatingPasswordEncoder(defaultEncode, encoderMap);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.
                        getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userDetailsManager)
                .passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .formLogin(form -> form
//                        .loginProcessingUrl("/myLogin")
//                        .successHandler(new MyAuthenticationSuccessHandler()) //登录成功后处理
//                        .failureHandler(new MyAuthenticationFailureHandler()) //登录失败后处理
//                        .permitAll()
//                )
//                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // 启用 CORS 配置
                .csrf(AbstractHttpConfigurer::disable) // 关闭 CSRF 保护
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/myLogin", "/logout","/users/createOne","/UploadPhoto/**","/upload").permitAll() // 允许未认证的访问
                        .anyRequest().authenticated() // 其他请求需要认证
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(new MyAuthenticationEntryPoint()) // 处理未认证的请求
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout") // 注销处理 URL
                        .logoutSuccessHandler(new MyLogoutSuccessHandler()) // 注销成功后的处理
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 设置无状态 Session
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        ;
//        http.sessionManagement(session -> session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 设置无状态 Session
//        );
//        // 在 UsernamePasswordAuthenticationFilter 之前添加 JWT 过滤器
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }

    /**
     * 跨域资源访问配置
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 1. 显式指定允许的源（避免使用 *）
        configuration.addAllowedOrigin("http://localhost:5173");

        // 2. 允许的 HTTP 方法（覆盖默认值）
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 3. 允许的请求头（生产环境应明确列出）
        configuration.addAllowedHeader("*");

        // 4. 启用凭证支持（需与 allowedOrigin 配合）
        configuration.setAllowCredentials(true);

        // 5. 暴露自定义响应头（如 Authorization）
        configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

