package com.xuecheng.content.model.dto;

import com.xuecheng.content.model.po.CourseCategory;
import lombok.Data;

import java.util.List;

/**
 * @Author Luffy5522
 * @date: 2023/2/24 21:34
 * @description:
 */
@Data
public class CourseCategoryTreeDto extends CourseCategory {
    List childrenTreeNodes;

}
