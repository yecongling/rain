package cn.soft.modules.base.mapper;

import cn.soft.common.api.dto.LogDTO;
import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 基础操作的mapper
 */
@Repository
public interface BaseCommonMapper {

    /**
     * 保存日志
     * @param dto 日志对象
     */
    @InterceptorIgnore(illegalSql = "true", tenantLine = "true")
    void saveLog(LogDTO dto);
}
