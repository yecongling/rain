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
}
