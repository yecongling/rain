package cn.soft.modules.system.service.impl;

import cn.soft.common.system.vo.DictModel;
import cn.soft.modules.system.entity.SysDict;
import cn.soft.modules.system.entity.SysDictItem;
import cn.soft.modules.system.mapper.SysDictItemMapper;
import cn.soft.modules.system.mapper.SysDictMapper;
import cn.soft.modules.system.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 字典表服务实现类
 */
@Service
@Slf4j
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {

    private SysDictMapper dictMapper;
    @Autowired
    public void setDictMapper(SysDictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    private SysDictItemMapper dictItemMapper;
    @Autowired
    public void setDictItemMapper(SysDictItemMapper dictItemMapper) {
        this.dictItemMapper = dictItemMapper;
    }

    /**
     * 查询所有字典条目
     * @return 返回所有字典条目
     */
    @Override
    public Map<String, List<DictModel>> queryAllDictItems() {
        Map<String, List<DictModel>> res = new HashMap<>();
        List<SysDict> ls = dictMapper.selectList(null);
        LambdaQueryWrapper<SysDictItem> queryWrapper = new LambdaQueryWrapper<>();
        // 查询启用了的
        queryWrapper.eq(SysDictItem::getStatus, 1);
        // 排序 升序排
        queryWrapper.orderByAsc(SysDictItem::getSortOrder);
        List<SysDictItem> sysDictItemList = dictItemMapper.selectList(queryWrapper);
        for (SysDict dict : ls) {
            List<DictModel> dictModelList = sysDictItemList.stream().filter(s -> dict.getId().equals(s.getDictId())).map(item -> {
                DictModel model = new DictModel();
                model.setText(item.getItemText());
                model.setValue(item.getItemValue());
                return model;
            }).collect(Collectors.toList());
            res.put(dict.getDictCode(), dictModelList);
        }
        log.debug("------------------登录加载系统字典-------------" + res.toString());
        return res;
    }
}
