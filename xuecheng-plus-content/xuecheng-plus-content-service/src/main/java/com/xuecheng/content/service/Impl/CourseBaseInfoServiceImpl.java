package com.xuecheng.content.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.Exception.XuechengException;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.mapper.CourseMarketMapper;
import com.xuecheng.content.model.Constans.dictionary;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.model.po.CourseCategory;
import com.xuecheng.content.model.po.CourseMarket;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author Luffy5522
 * @date: 2023/2/24 19:00
 * @description:
 */
@Slf4j
@Service
public class CourseBaseInfoServiceImpl implements CourseBaseInfoService {

    @Resource
    CourseBaseMapper courseBaseMapper;

    @Resource
    CourseMarketMapper courseMarketMapper;

    @Resource
    CourseCategoryMapper courseCategoryMapper;

    @Resource
    CourseMarketServiceImpl courseMarketService;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams,
                                                      QueryCourseParamsDto queryCourseParamsDto) {

        // 构造查询条件
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();

        // 对审核状态进行查询
        queryWrapper.eq(Strings.isNotEmpty(queryCourseParamsDto.getAuditStatus()),
                CourseBase::getAuditStatus, queryCourseParamsDto.getAuditStatus());

        // 对课程名称进行模糊查询
        queryWrapper.like(Strings.isNotEmpty(queryCourseParamsDto.getCourseName()),
                CourseBase::getName, queryCourseParamsDto.getCourseName());

        // 对发布状态进行查询
        queryWrapper.like(Strings.isNotEmpty(queryCourseParamsDto.getPublishStatus()),
                CourseBase::getStatus, queryCourseParamsDto.getPublishStatus());

        //分页对象
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(),
                pageParams.getPageSize());

        // 查询数据内容获得结果
        Page<CourseBase> courseBasePage = courseBaseMapper.selectPage(page, queryWrapper);

        // 获得查询结果的列表
        List<CourseBase> list = courseBasePage.getRecords();

        long total = courseBasePage.getTotal();

        return new PageResult<>(list, total,
                pageParams.getPageNo(), pageParams.getPageSize());
    }

    // 新建课程
    @Transactional
    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto dto) {

        ////合法性校验
        //if (StringUtils.isBlank(dto.getName())) {
        //    throw new XuechengException("课程分类为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getMt())) {
        //    throw new XuechengException("课程分类为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getSt())) {
        //    throw new XuechengException("课程分类为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getGrade())) {
        //    throw new XuechengException("课程等级为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getTeachmode())) {
        //    throw new XuechengException("教育模式为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getUsers())) {
        //    throw new XuechengException("适应人群为空");
        //}
        //
        //if (StringUtils.isBlank(dto.getCharge())) {
        //    throw new XuechengException("收费规则为空");
        //}

        //新增对象
        CourseBase courseBaseNew = new CourseBase();
        //将填写的课程信息赋值给新增对象
        BeanUtils.copyProperties(dto, courseBaseNew);
        //设置审核状态
        courseBaseNew.setAuditStatus(dictionary.AUDIT_STATUS_NOT_PASS.getCode());
        //设置发布状态
        courseBaseNew.setStatus(dictionary.STATUS_OUT_PUBLISH.getCode());
        //机构id
        courseBaseNew.setCompanyId(companyId);
        //添加时间
        courseBaseNew.setCreateDate(LocalDateTime.now());
        //插入课程基本信息表
        int insert = courseBaseMapper.insert(courseBaseNew);
        Long courseId = courseBaseNew.getId();
        //课程营销信息
        CourseMarket courseMarketNew = new CourseMarket();
        BeanUtils.copyProperties(dto, courseMarketNew);
        courseMarketNew.setId(courseId);
        //收费规则
        String charge = dto.getCharge();

        //收费课程必须写价格且价格大于0
        if (charge.equals(dictionary.PRICE_STATUS_PAY.getCode())) {
            if (dto.getPrice() == null) {
                throw new XuechengException("收费价格不能为空");
            }


            float price = dto.getPrice().floatValue();
            if (price <= 0) {
                throw new XuechengException("收费课程价格不能小于0");
            }
        }

        //插入课程营销信息
        int insert1 = courseMarketMapper.insert(courseMarketNew);

        if (insert <= 0 || insert1 <= 0) {
            throw new RuntimeException("新增课程基本信息失败");
        }
        //添加成功
        //返回添加的课程信息

        return getCourseBaseInfoDto(courseId);

    }

    // 根据id查询课程
    @Override
    public CourseBaseInfoDto selectCourseBaseInfoById(Long courseId) {
        // 1.根据id进行查询
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            log.info("课程id不存在");
            throw new XuechengException("课程id不存在");
        }

        // 2.对courseMarket进行查询
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        if (courseMarket == null) {
            log.info("课程营销信息不存在");
            throw new XuechengException("课程营销信息不存在");
        }

        // 3.返回基本信息
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        // 3.1 进行属性连接
        BeanUtil.copyProperties(courseBase, courseBaseInfoDto);
        BeanUtil.copyProperties(courseMarket, courseBaseInfoDto);

        return courseBaseInfoDto;
    }

    // 修改课程信息
    @Override
    public CourseBaseInfoDto updateCourseBase(EditCourseDto editCourseDto) {
        Long courseId = editCourseDto.getId();
        // 1.查询课程是否存在
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            log.info("查询时,课程id不存在");
            throw new XuechengException("课程id不存在");
        }
        // 2.查询修改是否正确
        // 2.1 业务逻辑中只要判断价格
        if (editCourseDto.getCharge().equals(dictionary.PRICE_STATUS_PAY.getCode())) {
            if (editCourseDto.getPrice() == null || editCourseDto.getPrice().floatValue() <= 0) {
                log.info("收费课程,价格不能为0");
                throw new XuechengException("收费课程,价格不能为0");
            }
        }


        // 3.判断courseMarket是否存在,若存在,则修改,否则添加
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        courseMarketService.saveOrUpdate(courseMarket);

        // 4.更新
        BeanUtil.copyProperties(editCourseDto, courseBase);
        courseBase.setChangePeople(editCourseDto.getUsers());
        courseBase.setChangeDate(LocalDateTime.now());

        return selectCourseBaseInfoById(courseId);
    }

    public CourseBaseInfoDto getCourseBaseInfoDto(Long id) {

        CourseBase courseBase = courseBaseMapper.selectById(id);
        CourseMarket courseMarket = courseMarketMapper.selectById(id);

        if (courseBase == null || courseMarket == null) {
            throw new RuntimeException("id错误");
        }

        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtil.copyProperties(courseBase, courseBaseInfoDto);
        BeanUtil.copyProperties(courseMarket, courseBaseInfoDto);

        //查询分类名称
        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());

        return courseBaseInfoDto;
    }
}
