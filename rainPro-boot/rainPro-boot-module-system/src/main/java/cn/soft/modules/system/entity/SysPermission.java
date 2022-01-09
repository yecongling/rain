package cn.soft.modules.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 菜单权限表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysPermission implements Serializable {
    private static final long serialVersionUID = -9067269139874010804L;
}
