package cn.soft.modules.base.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;

/**
 * 基础操作的mapper
 */
@Mapper
public interface BaseCommonMapper {

    /**
     * 保存日志
     */
    @InterceptorIgnore(illegalSql = "true", tenantLine = "true")
    void saveLog();
}
