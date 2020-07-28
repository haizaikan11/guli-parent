package com.wulx.guli.service.edu.service.impl;


import com.wulx.guli.service.edu.entity.Course;
import com.wulx.guli.service.edu.entity.CourseDescription;
import com.wulx.guli.service.edu.entity.form.CourseInfoForm;
import com.wulx.guli.service.edu.mapper.CourseDescriptionMapper;
import com.wulx.guli.service.edu.mapper.CourseMapper;
import com.wulx.guli.service.edu.service.CourseDescriptionService;
import com.wulx.guli.service.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
