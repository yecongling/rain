package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysDict;
import cn.soft.modules.system.mapper.SysDictMapper;
import cn.soft.modules.system.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 字典表服务实现类
 */
@Service
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {
}
