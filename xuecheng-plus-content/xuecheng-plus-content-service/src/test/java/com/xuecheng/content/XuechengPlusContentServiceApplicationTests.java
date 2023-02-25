package com.xuecheng.content;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.service.CourseCategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class XuechengPlusContentServiceApplicationTests {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    @Autowired
    CourseCategoryService courseCategoryService;

    @Test
    void contextLoads() {
        CourseBase courseBase = courseBaseMapper.selectById(74L);
        Assertions.assertNotNull(courseBase);
    }

    @Test
    void testQueryCourseBaseList() {
        PageParams pageParams = new PageParams(1, 10);
        QueryCourseParamsDto queryCourseParamsDto = new QueryCourseParamsDto();
        queryCourseParamsDto.setCourseName("java");
        queryCourseParamsDto.setAuditStatus("");
        queryCourseParamsDto.setPublishStatus("");
        PageResult<CourseBase> courseBasePageResult = courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParamsDto);
        System.out.println(courseBasePageResult.toString());


    }



    @Test
    void testCourseCategoryService(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos =
                courseCategoryService.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }


}


