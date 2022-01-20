package cn.soft.modules.system.mapper;

import cn.soft.common.system.vo.DictModel;
import cn.soft.common.system.vo.DictModelMany;
import cn.soft.modules.system.entity.SysDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典表mapper接口
 */
@Repository
public interface SysDictMapper extends BaseMapper<SysDict> {
    /**
     * 通过查询指定table的 text code key 获取字典值，可批量查询
     *
     * @param table
     * @param text
     * @param code
     * @param keys
     * @return
     */
    @Deprecated
    List<DictModel> queryTableDictTextByKeys(@Param("table") String table, @Param("text") String text, @Param("code") String code, @Param("keys") List<String> keys);

    /**
     * 可通过多个字典code查询翻译文本
     *
     * @param dictCodeList 多个字典code
     * @param keys         数据列表
     * @return
     */
    List<DictModelMany> queryManyDictByKeys(@Param("dictCodeList") List<String> dictCodeList, @Param("keys") List<String> keys);
}
