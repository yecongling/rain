package cn.soft.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 租户信息
 */
@Data
@TableName("sys_tenant")
public class SysTenant implements Serializable {
    private static final long serialVersionUID = 6961370964189869967L;
}
