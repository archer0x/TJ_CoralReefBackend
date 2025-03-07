package org.example.coralreef_backend.config;

import com.baomidou.mybatisplus.core.incrementer.IdentifierGenerator;
import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import org.example.coralreef_backend.common.handler.MyTenantLineHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mybatis-plus配置类
 */
@Configuration
public class MyBatisPlusConfig {

    private final long workerId = 1; // 终端ID
    private final long datacenterId = 1; // 数据中心ID

    /**
     * 雪花算法生成器
     */
    @Bean
    public IdentifierGenerator idGenerator() {
        return new IdentifierGenerator() {
            Sequence sequence = new Sequence(workerId, datacenterId);

            @Override
            public Number nextId(Object entity) {
                return sequence.nextId();
            }

            @Override
            public String nextUUID(Object entity) {
                return Long.toString(sequence.nextId());
            }
        };
    }

    /**
     * mybatisPlus拦截器配置
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(MyTenantLineHandler tenantLineHandler) {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加多租户拦截器，用于处理全局查询条件
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantLineHandler));
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}
