package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysPosition;
import cn.soft.modules.system.mapper.SysPositionMapper;
import cn.soft.modules.system.service.ISysPositionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysPositionServiceImpl
 * @Description TODO
 * @Author 叶丛林
 * @Date 2022/3/7 9:01 下午
 * @Version 1.0
 **/
@Service
public class SysPositionServiceImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements ISysPositionService {
    /**
     * 根据code查询职务
     *
     * @param code 职务编码
     * @return 职务对象
     */
    @Override
    public SysPosition getByCode(String code) {
        LambdaQueryWrapper<SysPosition> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysPosition::getCode, code);
        return getOne(queryWrapper);
    }
}
