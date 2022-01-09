package cn.soft.modules.system.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 字典表
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysDict implements Serializable {
    private static final long serialVersionUID = 7085721441457315638L;
}
