package com.xuecheng.content.service.Impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @Author Luffy5522
 * @date: 2023/2/25 8:58
 * @description: 课程分类树形结构查询
 */
@Slf4j
@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Resource
    CourseCategoryMapper courseCategoryMapper;

    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {


        //查询数据库得到的课程分类
        List<CourseCategoryTreeDto> courseCategoryTreeDtoList = courseCategoryMapper.selectTreeNodes(id);
        //最终返回的列表
        List<CourseCategoryTreeDto> categoryTreeDtoList = new ArrayList<>();
        HashMap<String, CourseCategoryTreeDto> mapTemp = new HashMap<>();
        // 将数据封装到list中,只包含了根节点的直接下属节点
        courseCategoryTreeDtoList.stream().forEach(item -> {
            mapTemp.put(item.getId(), item);

            //只将根节点的下级节点放入list
            if (item.getParentid().equals(id)) {
                categoryTreeDtoList.add(item);
            }

            // 找到该节点的父节点对象
            CourseCategoryTreeDto courseCategoryTreeDto = mapTemp.get(item.getParentid());

            if (courseCategoryTreeDto != null) {
                if (courseCategoryTreeDto.getChildrenTreeNodes() == null) {
                    courseCategoryTreeDto.setChildrenTreeNodes
                            (new ArrayList<CourseCategoryTreeDto>());
                }
                // 找到子节点,放到他父节点的childTreeNode中
                courseCategoryTreeDto.getChildrenTreeNodes().add(item);
            }

        });
        return categoryTreeDtoList;
    }
}
