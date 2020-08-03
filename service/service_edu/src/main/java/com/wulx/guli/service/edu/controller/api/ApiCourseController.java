package com.wulx.guli.service.edu.controller.api;

import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.entity.Course;
import com.wulx.guli.service.edu.entity.query.WebCourseQuery;
import com.wulx.guli.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin  // 跨域
@Api(tags = "课程")
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {

    @Autowired
    CourseService courseService;

    /**
     * 未添加分页功能
     * @param webCourseQuery
     * @return
     */
    @ApiOperation("课程列表")
    @GetMapping("list")
    public R list(
            @ApiParam(value = "查询条件",required = true)
             WebCourseQuery webCourseQuery
    ){
        List<Course> list = courseService.webCourseList(webCourseQuery);
        return R.ok().data("courseList",list);
    }

}
