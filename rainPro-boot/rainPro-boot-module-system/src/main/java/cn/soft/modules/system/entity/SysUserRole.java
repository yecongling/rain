package cn.soft.modules.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysUserRole implements Serializable {
    private static final long serialVersionUID = -3978111591345061344L;
}
