package cn.soft.modules.system.mapper;

import cn.soft.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典条目mapper接口（字典明细）
 */
@Repository
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {
    @Select("select * from sys_dict_item where dict_id = #{mainId} order by sort_order asc, item_value asc")
    List<SysDictItem> selectItemsByMainId(String mainId);
}
