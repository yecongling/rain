package cn.soft.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 部门角色人员信息
 */
@Data
@TableName("sys_depart_role_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart_role_user对象", description="部门角色人员信息")
public class SysDepartRoleUser implements Serializable {
    private static final long serialVersionUID = 8945845387237836719L;
}
