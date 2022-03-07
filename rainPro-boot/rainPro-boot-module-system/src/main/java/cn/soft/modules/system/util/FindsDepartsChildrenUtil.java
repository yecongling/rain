package cn.soft.modules.system.util;

import cn.soft.common.util.ConvertUtils;
import cn.soft.modules.system.entity.SysDepart;
import cn.soft.modules.system.model.DepartIdModel;
import cn.soft.modules.system.model.SysDepartTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FindsDepartsChildrenUtil
 * @Description 对应部门表的数据 处理并查找树级数据
 * @Author 叶丛林
 * @Date 2022/3/7 9:41 下午
 * @Version 1.0
 **/
public class FindsDepartsChildrenUtil {

    /**
     * queryTreeList的子方法 ====1=====
     * 该方法是s将SysDepart类型的list集合转换成SysDepartTreeModel类型的集合
     */
    public static List<SysDepartTreeModel> wrapTreeDataToTreeList(List<SysDepart> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        List<DepartIdModel> idList = new ArrayList<>();
        List<SysDepartTreeModel> records = new ArrayList<>();
        for (SysDepart depart : recordList) {
            records.add(new SysDepartTreeModel(depart));
        }
        List<SysDepartTreeModel> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

    /**
     * 获取 DepartIdModel
     * @param recordList
     * @return
     */
    public static List<DepartIdModel> wrapTreeDataToDepartIdTreeList(List<SysDepart> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<DepartIdModel> idList = new ArrayList<>();
        List<SysDepartTreeModel> records = new ArrayList<>();
        for (SysDepart depart : recordList) {
            records.add(new SysDepartTreeModel(depart));
        }
        findChildren(records, idList);
        return idList;
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<SysDepartTreeModel> findChildren(List<SysDepartTreeModel> recordList,
                                                         List<DepartIdModel> departIdList) {
        List<SysDepartTreeModel> treeList = new ArrayList<>();
        for (SysDepartTreeModel branch : recordList) {
            if (ConvertUtils.isEmpty(branch.getParentId())) {
                treeList.add(branch);
                DepartIdModel departIdModel = new DepartIdModel().convert(branch);
                departIdList.add(departIdModel);
            }
        }
        getGrandChildren(treeList,recordList,departIdList);
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<SysDepartTreeModel> treeList,List<SysDepartTreeModel> recordList,List<DepartIdModel> idList) {
        for (int i = 0; i < treeList.size(); i++) {
            SysDepartTreeModel model = treeList.get(i);
            DepartIdModel idModel = idList.get(i);
            for (SysDepartTreeModel m : recordList) {
                if (m.getParentId() != null && m.getParentId().equals(model.getId())) {
                    model.getChildren().add(m);
                    DepartIdModel dim = new DepartIdModel().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }
    }

    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<SysDepartTreeModel> treeList) {
        for (SysDepartTreeModel model : treeList) {
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            } else {
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }
    }
}
