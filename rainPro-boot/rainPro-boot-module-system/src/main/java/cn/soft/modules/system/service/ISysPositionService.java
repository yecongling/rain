package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysPosition;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @ClassName ISysPositionService
 * @Description TODO
 * @Author 叶丛林
 * @Date 2022/3/7 8:57 下午
 * @Version 1.0
 **/
public interface ISysPositionService extends IService<SysPosition> {

    /**
     * 根据code查询职务
     *
     * @param code 职务编码
     * @return 职务对象
     */
    SysPosition getByCode(String code);
}
