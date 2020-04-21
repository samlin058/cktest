package com.lemon.pojo;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author samlin
 * @since 2020-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ApiRequestParam对象", description="")
public class ApiRequestParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联的接口编号")
    private Integer apiId;

    @ApiModelProperty(value = "参数名称")
    private String name;

    @ApiModelProperty(value = "字段类型（string、int...）")
    private String paramType;

    @ApiModelProperty(value = "参数类型（1：query参数；2：body参数；3：header）")
    private Integer type;

    @ApiModelProperty(value = "参数值示例")
    private String exampleData;

    @ApiModelProperty(value = "字段描述")
    private String description;
    
    @TableField(exist=false)
    private  String value;
    
    @TableField(exist=false)
    private  Integer valueId;
}  
