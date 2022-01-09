package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysDepart;
import cn.soft.modules.system.mapper.SysDepartMapper;
import cn.soft.modules.system.service.ISysDepartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 部门表 服务实现类
 */
@Service
public class SysDepartServiceImpl extends ServiceImpl<SysDepartMapper, SysDepart> implements ISysDepartService {
}
