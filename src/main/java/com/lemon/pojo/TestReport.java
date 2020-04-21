package com.lemon.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value="TestReport对象", description="")
public class TestReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用例编号")
    private Integer caseId;

    @ApiModelProperty(value = "接口地址")
    private String requestUrl;

    @ApiModelProperty(value = "请求头")
    private String requestHeaders;

    @ApiModelProperty(value = "请求体数据")
    private String requestBody;

    @ApiModelProperty(value = "响应头")
    private String responseHeaders;

    @ApiModelProperty(value = "响应体")
    private String responseBody;

    @ApiModelProperty(value = "测试结果（通过 or 不通过）")
    private String passFlag;


}
