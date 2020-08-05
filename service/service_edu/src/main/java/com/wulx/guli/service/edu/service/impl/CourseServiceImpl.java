package com.wulx.guli.service.edu.service.impl;


import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wulx.guli.service.edu.entity.Course;
import com.wulx.guli.service.edu.entity.CourseDescription;
import com.wulx.guli.service.edu.entity.form.CourseInfoForm;
import com.wulx.guli.service.edu.entity.query.CourseQuery;
import com.wulx.guli.service.edu.entity.query.WebCourseQuery;
import com.wulx.guli.service.edu.entity.vo.CourseVo;
import com.wulx.guli.service.edu.entity.vo.WebCourseVo;
import com.wulx.guli.service.edu.mapper.CourseDescriptionMapper;
import com.wulx.guli.service.edu.mapper.CourseMapper;
import com.wulx.guli.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;


import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    CourseDescriptionMapper courseDescriptionMapper;
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        //创建一个course
        Course course = new Course();


        //将courseInfoFrom中的和course相同属性的数据复制到course
        BeanUtils.copyProperties(courseInfoForm,course);
        //设置课程状态
        course.setStatus(Course.COURSE_DRAFT);
        //插入数据到数据库，并将数据返回到course对象
        baseMapper.insert(course);
        //课程详细介绍
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }
    @Override
    public String updateCourseInfo(CourseInfoForm courseInfoForm) {
        //创建一个course
        Course course = new Course();
        //将courseInfoFrom中的和course相同属性的数据复制到course
        BeanUtils.copyProperties(courseInfoForm,course);
        //设置课程状态
        course.setStatus(Course.COURSE_DRAFT);
        //修改数据到数据库，并将数据返回到course对象
        int i = baseMapper.updateById(course);
        if(i == 0){
            baseMapper.insert(course) ;
        }
        //课程详细介绍
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseInfoForm.getDescription());
        int i1 = courseDescriptionMapper.updateById(courseDescription);
        if(i1 == 0){
            courseDescriptionMapper.insert(courseDescription);
        }

        return course.getId();
    }

    @Override
    public IPage<CourseVo> selectPage(Long page, Long limit, CourseQuery courseQuery) {

        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        // 根据添加时间排序
        queryWrapper.orderByDesc("c.publish_time");

        String title = courseQuery.getTitle();
        String teacherId = courseQuery.getTeacherId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String subjectId = courseQuery.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }


        IPage<CourseVo> pageModel = new Page<>(page,limit);
        // 查出页面信息
        List<CourseVo> list = baseMapper.selectPageByCourseQuery(pageModel,queryWrapper);
        pageModel.setRecords(list);
        return pageModel;
    }

    /**
     * 用户页面的课程列表
     * @param webCourseQuery
     * @return
     */
    @Override
    public List<Course> webCourseList(WebCourseQuery webCourseQuery) {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        //查询已发布的课程
        queryWrapper.eq("status", Course.COURSE_NORMAL);

        // 一级标题的查询
        if (!StringUtils.isEmpty(webCourseQuery.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQuery.getSubjectParentId());
            // 只有存在一级标题，才有二级标题
            if (!StringUtils.isEmpty(webCourseQuery.getSubjectId())) {
                queryWrapper.eq("subject_id", webCourseQuery.getSubjectId());
            }
        }
        // 购买人数排序
        if (!StringUtils.isEmpty(webCourseQuery.getBuyCountSort())) {
            queryWrapper.orderByDesc("buy_count");
        }
        // 添加时间排序
        if (!StringUtils.isEmpty(webCourseQuery.getPublishTimeSort())) {
            queryWrapper.orderByDesc("publish_time");
        }
        // 价格排序
        if (!StringUtils.isEmpty(webCourseQuery.getPriceSort())) {
            // 默认排序
            if(webCourseQuery.getType() == null || webCourseQuery.getType() == 1){
                queryWrapper.orderByAsc("price");
            }else{

                queryWrapper.orderByDesc("price");
            }
        }

        return baseMapper.selectList(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WebCourseVo selectWebCourseVoById(String id) {
            // 更新浏览数
            baseMapper.updateViewCountById(id);
            // 返回查询到的页面数据
        return  baseMapper.selectWebCourseVoById(id);
    }

    @Cacheable(value = "index",key = "'getHotCourse'")
    @Override
    public List<Course> getHotCourse() {

        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count");
        queryWrapper.last("limit 8");
        List<Course> courseList = baseMapper.selectList(queryWrapper);
        return courseList;

    }


    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        Course course = baseMapper.selectById(id);
        if(course == null){
            return null;
        }
        CourseInfoForm courseInfoForm = new CourseInfoForm();

        BeanUtils.copyProperties(course,courseInfoForm);
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);
        if(courseDescription !=null){
            courseInfoForm.setDescription(courseDescription.getDescription());
        }
        return courseInfoForm;
    }


}
