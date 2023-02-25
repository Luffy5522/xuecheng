package com.xuecheng.content.controller;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author Luffy5522
 * @date: 2023/2/24 21:35
 * @description:
 */
@Api(value = "课程分类相关接口",tags = "课程分类相关接口")
@RestController
public class CourseCategoryController {

    @Resource
    CourseCategoryService courseCategoryService;

    @ApiModelProperty("课程查询分类查询接口")
    @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes(){
        return courseCategoryService.queryTreeNodes("1");
    }
}
