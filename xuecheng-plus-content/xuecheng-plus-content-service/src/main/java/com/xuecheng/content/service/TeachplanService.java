package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;

import java.util.List;

/**
 * @author Mr.M
 * @version 1.0
 * @description 课程基本信息管理业务接口
 * @date 2022/9/6 21:42
 */
public interface TeachplanService {

    /**
     * @param courseId 课程id
     * @return List<TeachplanDto>
     * @description 查询课程计划树型结构
     * @author Mr.M
     * @date 2022/9/9 11:13
     */
     List<TeachplanDto> findTeachplayTree(long courseId);

    /**
     * @param teachplanDto 课程计划信息
     * @return void
     * @description 只在课程计划
     * @author Mr.M
     * @date 2022/9/9 13:39
     */
     void saveTeachplan(SaveTeachplanDto teachplanDto);

}