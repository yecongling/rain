package cn.soft.modules.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRole implements Serializable {
    private static final long serialVersionUID = 1158537451986741883L;
}
