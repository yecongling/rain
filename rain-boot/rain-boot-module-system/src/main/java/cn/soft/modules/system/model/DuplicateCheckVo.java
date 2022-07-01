package cn.soft.modules.system.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName DuplicateCheckVo
 * @Description 重复校验vo
 * @Author 叶丛林
 * @Date 2022/2/23 9:56 下午
 * @Version 1.0
 **/
@Data
@ApiModel(value="重复校验数据模型",description="重复校验数据模型")
public class DuplicateCheckVo implements Serializable {
    private static final long serialVersionUID = -6960904966933035568L;
    /**
     * 表名
     */
    @ApiModelProperty(value="表名",name="tableName",example="sys_log")
    private String tableName;

    /**
     * 字段名
     */
    @ApiModelProperty(value="字段名",name="fieldName",example="id")
    private String fieldName;

    /**
     * 字段值
     */
    @ApiModelProperty(value="字段值",name="fieldVal",example="1000")
    private String fieldVal;

    /**
     * 数据ID
     */
    @ApiModelProperty(value="数据ID",name="dataId",example="2000")
    private String dataId;
}
