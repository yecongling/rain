package cn.soft.modules.system.mapper;

import cn.soft.common.system.vo.DictModel;
import cn.soft.common.system.vo.DictModelMany;
import cn.soft.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典条目mapper接口（字典明细）
 */
@Repository
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {

}
