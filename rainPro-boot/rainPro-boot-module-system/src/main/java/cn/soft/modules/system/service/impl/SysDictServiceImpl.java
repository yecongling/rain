package cn.soft.modules.system.service.impl;

import cn.soft.common.system.vo.DictModel;
import cn.soft.common.system.vo.DictModelMany;
import cn.soft.common.util.SqlInjectionUtil;
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

import java.util.ArrayList;
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
     * 通过编码查询字典
     *
     * @param code
     * @return
     */
    @Override
    public List<DictModel> queryDictItemsByCode(String code) {
        return dictMapper.queryDictItemsByCode(code);
    }

    /**
     * 查询所有字典条目
     *
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

    /**
     * 通过查询指定table的 text code key 获取字典值，可批量查询
     *
     * @param table
     * @param text
     * @param code
     * @param keys
     * @return
     */
    @Override
    public List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys) {
        return dictMapper.queryTableDictTextByKeys(table, text, code, keys);
    }

    /**
     * 可通过多个字典code查询翻译文本
     *
     * @param dictCodeList 多个字典code
     * @param keys         数据列表
     * @return
     */
    @Override
    public Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys) {
        List<DictModelMany> list = dictMapper.queryManyDictByKeys(dictCodeList, keys);
        Map<String, List<DictModel>> dictMap = new HashMap<>();
        for (DictModelMany dict : list) {
            List<DictModel> dictItemList = dictMap.computeIfAbsent(dict.getDictCode(), i -> new ArrayList<>());
            dictItemList.add(new DictModel(dict.getValue(), dict.getText()));
        }
        return dictMap;
    }

    /**
     * 获取字典数据
     *
     * @param dictCode 字典编码
     * @return /
     */
    @Override
    public List<DictModel> getDictItems(String dictCode) {
        List<DictModel> list;
        if (dictCode.contains(",")) {
            // 关联表字典
            String[] params = dictCode.split(",");
            if (params.length < 3) {
                // 字典code格式不正确
                return null;
            }
            // SQL注入校验（只限制非法串改数据库）
            final String[] sqlInjCheck = {params[0], params[1], params[2]};
            SqlInjectionUtil.filterContent(sqlInjCheck);
            if (params.length == 4) {
                list = this.queryTableDictItemsByCodeAndFilter(params[0], params[1], params[2], params[3]);
            } else if (params.length == 3) {
                list = this.queryTableDictItemsByCode(params[0], params[1], params[2]);
            } else {
                // 字典格式不正确
                return null;
            }
        } else {
            list = this.queryDictItemsByCode(dictCode);
        }
        return list;
    }

    /**
     * 通过查询指定table的 text code 获取字典
     * dictTableCache采用redis缓存有效期10分钟
     *
     * @param table
     * @param text
     * @param code
     * @return
     */
    @Override
    public List<DictModel> queryTableDictItemsByCode(String table, String text, String code) {
        return dictMapper.queryTableDictItemsByCode(table, text, code);
    }

    /**
     * 通过查询指定table的 text code 获取字典（指定条件）
     * dictTableCache采用redis缓存有效期10分钟
     *
     * @param table
     * @param text
     * @param code
     * @return
     */
    @Override
    public List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql) {
        return dictMapper.queryTableDictItemsByCodeAndFilter(table, text, code, filterSql);
    }

    /**
     *  查询被逻辑删除的字典数据
     *
     * @return
     */
    @Override
    public List<SysDict> queryDeleteList() {
        return dictMapper.queryDeleteList();
    }
}
