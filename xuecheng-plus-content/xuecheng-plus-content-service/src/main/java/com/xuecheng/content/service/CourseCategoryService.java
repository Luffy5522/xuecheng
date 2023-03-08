package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @Author Luffy5522
 * @date: 2023/2/25 8:57
 * @description: 课程分类相关的service
 */
public interface CourseCategoryService {

    /**
     * @param id: 根节点id
     * @return 根节点下的所有子节点
     * @author Luffy5522
     * @description 课程分类查询
     * @date 2023/3/8 19:19
     */

    List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
