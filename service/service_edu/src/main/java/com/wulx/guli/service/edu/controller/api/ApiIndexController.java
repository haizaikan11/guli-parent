package com.wulx.guli.service.edu.controller.api;


import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.entity.Course;
import com.wulx.guli.service.edu.entity.Teacher;
import com.wulx.guli.service.edu.service.CourseService;
import com.wulx.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@Api(tags = "首页")
@RestController
@RequestMapping("/api/edu/index")
public class ApiIndexController {

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    @ApiOperation("课程列表")
    @GetMapping
    public R index(){
        //获取首页最热门前8条课程数据
        List<Course> courseList = courseService.getHotCourse();
        //获取首页推荐前4条讲师数据
        List<Teacher> teacherList = teacherService.getHotTeacher();
        return R.ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}
