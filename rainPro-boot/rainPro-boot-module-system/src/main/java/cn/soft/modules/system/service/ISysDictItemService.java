package cn.soft.modules.system.service;

import cn.soft.modules.system.entity.SysDictItem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @ClassName ISysDictItemService
 * @Description 字典条目服务类
 * @Author 叶丛林
 * @Date 2022/2/22 9:45 下午
 * @Version 1.0
 **/
public interface ISysDictItemService extends IService<SysDictItem> {

    /**
     * 通过字典ID查询其数据集合
     *
     * @param mainId id
     * @return /
     */
    public List<SysDictItem> selectItemsByMainId(String mainId);
}
