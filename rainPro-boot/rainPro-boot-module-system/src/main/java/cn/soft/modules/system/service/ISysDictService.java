package cn.soft.modules.system.service;

import cn.soft.common.system.vo.DictModel;
import cn.soft.modules.system.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 字典表服务类
 */
public interface ISysDictService extends IService<SysDict> {

    /**
     * 查询所有字典条目
     * @return 返回所有字典条目
     */
    public Map<String, List<DictModel>> queryAllDictItems();

    /**
     * 通过查询指定table的 text code key 获取字典值，可批量查询
     *
     * @param table
     * @param text
     * @param code
     * @param keys
     * @return
     */
    List<DictModel> queryTableDictTextByKeys(String table, String text, String code, List<String> keys);

    /**
     * 可通过多个字典code查询翻译文本
     * @param dictCodeList 多个字典code
     * @param keys 数据列表
     * @return
     */
    Map<String, List<DictModel>> queryManyDictByKeys(List<String> dictCodeList, List<String> keys);
}
