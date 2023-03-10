package com.xuecheng.content.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @Author Luffy5522
 * @date: 2023/3/10 10:22
 * @description: 修改课程的dto
 */

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value = "EditCourseDto", description = "修改课程基本信息")
public class EditCourseDto extends AddCourseDto {

    @ApiModelProperty(value = "课程id", required = true)
    private Long id;
}
