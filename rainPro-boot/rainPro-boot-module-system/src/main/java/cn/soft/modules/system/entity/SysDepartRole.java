package cn.soft.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 部门角色
 */
@Data
@TableName("sys_depart_role")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_depart_role对象", description="部门角色")
public class SysDepartRole implements Serializable {
    private static final long serialVersionUID = -1936779971914066900L;
}
