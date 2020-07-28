package com.wulx.guli.service.edu.service;

import com.wulx.guli.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wulx.guli.service.edu.entity.form.CourseInfoForm;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoForm courseInfoForm);

    CourseInfoForm getCourseInfoById(String id);

    String updateCourseInfo(CourseInfoForm courseInfoForm);
}
