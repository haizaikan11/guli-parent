package com.wulx.guli.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wulx.guli.service.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wulx.guli.service.edu.entity.form.CourseInfoForm;
import com.wulx.guli.service.edu.entity.query.CourseQuery;
import com.wulx.guli.service.edu.entity.query.WebCourseQuery;
import com.wulx.guli.service.edu.entity.vo.CourseVo;
import com.wulx.guli.service.edu.entity.vo.WebCourseVo;

import java.util.List;

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

    IPage<CourseVo> selectPage(Long page, Long limit, CourseQuery courseQuery);

    List<Course> webCourseList(WebCourseQuery webCourseQuery);

    WebCourseVo selectWebCourseVoById(String id);
}
