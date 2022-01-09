package cn.soft.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门表
 */
@Data
@TableName("sys_depart")
public class SysDepart implements Serializable {
    private static final long serialVersionUID = -5037431364629118320L;
}
