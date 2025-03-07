package org.example.coralreef_backend.common.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.LongValue;
import org.springframework.stereotype.Component;

/**
 * 自定义多租户处理器
 */
@Component
public class MyTenantLineHandler implements TenantLineHandler {

    @Override
    public LongValue getTenantId() {
        // 返回一个表达式，表示全局过滤条件，这里使用 delFlag=0
        return new LongValue(0);
    }

    @Override
    public String getTenantIdColumn() {
        // 返回需要过滤的字段名称，这里是 delFlag
        return "del_flag";
    }

    @Override
    public boolean ignoreTable(String tableName) {
        // 如果有些表不需要过滤，可以在这里指定
        return false;
    }

}
