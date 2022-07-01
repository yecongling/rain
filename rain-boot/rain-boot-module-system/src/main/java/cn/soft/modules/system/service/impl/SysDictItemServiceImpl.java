package cn.soft.modules.system.service.impl;

import cn.soft.modules.system.entity.SysDictItem;
import cn.soft.modules.system.mapper.SysDictItemMapper;
import cn.soft.modules.system.service.ISysDictItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName SysDictItemServiceImpl
 * @Description TODO
 * @Author 叶丛林
 * @Date 2022/2/22 9:52 下午
 * @Version 1.0
 **/
@Service
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements ISysDictItemService {

    private SysDictItemMapper sysDictItemMapper;
    @Autowired
    public void setSysDictItemMapper(SysDictItemMapper sysDictItemMapper) {
        this.sysDictItemMapper = sysDictItemMapper;
    }

    /**
     * 获取字典条目详细数据
     *
     * @param mainId id
     * @return /
     */
    @Override
    public List<SysDictItem> selectItemsByMainId(String mainId) {
        return sysDictItemMapper.selectItemsByMainId(mainId);
    }
}
