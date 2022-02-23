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
     * 通过编码查询
     *
     * @param code
     * @return
     */
    public List<DictModel> queryDictItemsByCode(String code);

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

    /**
     * 获取字典数据
     *
     * @param dictCode 字典编码
     * @return /
     */
    List<DictModel> getDictItems(String dictCode);

    /**
     * 查询指定table的  text code 获取字典
     *
     * @param table 表名
     * @param text /
     * @param code /
     * @return /
     */
    List<DictModel> queryTableDictItemsByCode(String table, String text, String code);

    /**
     * 通过查询指定table的 text code 获取字典（指定查询条件）
     *
     * @param table 表名
     * @param text /
     * @param code /
     * @param filterSql 过滤条件
     * @return
     */
    public List<DictModel> queryTableDictItemsByCodeAndFilter(String table, String text, String code, String filterSql);

    /**
     * 查询被逻辑删除的数据
     *
     * @return
     */
    public List<SysDict> queryDeleteList();
}
