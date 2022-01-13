package cn.soft.config.mybatis;

import cn.soft.common.util.ConvertUtils;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 单数据源配置（rainPro.datasource.open = false时生效）
 *
 */
@Configuration
@MapperScan(value = {"cn.soft.modules.**.mapper*"})
public class MybatisPlusSaasConfig {
    /**
     * tenant_id 字段名
     */
    private static final String TENANT_FIELD_NAME = "tenant_id";
    /**
     * 哪些表需要做多租户 表需要添加一个字段 tenant_id
     */
    private static final List<String> tenantTable = new ArrayList<>();

    static {
        tenantTable.add("demo");

    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 先 add TenantLineInnerInterceptor 再 add PaginationInnerInterceptor
        interceptor.addInnerInterceptor(new TenantLineInnerInterceptor(new TenantLineHandler() {
            @Override
            public Expression getTenantId() {
                String tenant_id = ConvertUtils.getString(TenantContext.getTenant(), "0");
                return new LongValue(tenant_id);
            }

            @Override
            public String getTenantIdColumn() {
                return TENANT_FIELD_NAME;
            }

            // 返回 true 表示不走租户逻辑
            @Override
            public boolean ignoreTable(String tableName) {
                for (String temp : tenantTable) {
                    if (temp.equalsIgnoreCase(tableName)) {
                        return false;
                    }
                }
                return true;
            }
        }));
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

//    /**
//     * 下个版本会删除，现在为了避免缓存出现问题不得不配置
//     * @return
//     */
//    @Bean
//    public ConfigurationCustomizer configurationCustomizer() {
//        return configuration -> configuration.setUseDeprecatedExecutor(false);
//    }
//    /**
//     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
//     */
//    @Bean
//    public PerformanceInterceptor performanceInterceptor() {
//        return new PerformanceInterceptor();
//    }

}
