package cn.soft.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 部门角色（和角色无关）、人员关联信息
 */
@Data
@TableName("sys_depart_role_user")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "sys_depart_role_user对象", description = "部门角色人员信息")
public class SysDepartRoleUser implements Serializable {
    private static final long serialVersionUID = 8945845387237836719L;

    /**
     * 主键id
     */
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键id")
    private java.lang.String id;
    /**
     * 用户id
     */
    @Excel(name = "用户id", width = 15)
    @ApiModelProperty(value = "用户id")
    private java.lang.String userId;
    /**
     * 角色id
     */
    @Excel(name = "角色id", width = 15)
    @ApiModelProperty(value = "角色id")
    private java.lang.String droleId;

    public SysDepartRoleUser() {

    }

    public SysDepartRoleUser(String userId, String droleId) {
        this.userId = userId;
        this.droleId = droleId;
    }
}
