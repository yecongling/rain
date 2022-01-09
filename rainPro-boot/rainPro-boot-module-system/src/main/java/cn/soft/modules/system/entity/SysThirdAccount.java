package cn.soft.modules.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 第三方登录账号表
 */
@Data
@TableName("sys_third_account")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_third_account对象", description="第三方登录账号表")
public class SysThirdAccount implements Serializable {
    private static final long serialVersionUID = 2941153340287568200L;
}
