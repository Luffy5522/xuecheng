package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

/**
 * @Author Luffy5522
 * @date: 2023/2/25 8:57
 * @description:
 */
public interface CourseCategoryService {

    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
