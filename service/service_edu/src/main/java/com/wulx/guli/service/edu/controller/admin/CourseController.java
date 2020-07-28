package com.wulx.guli.service.edu.controller.admin;


import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.entity.form.CourseInfoForm;
import com.wulx.guli.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@CrossOrigin  //跨域
@Api(tags = "课程管理")
@RestController
@RequestMapping("admin/edu/course")
public class CourseController {

    @Autowired
    CourseService courseService;
    @ApiOperation("新增课程")
    @PostMapping("save-course-info")
    public R saveCourseInfo(
            @ApiParam(value = "添加的课程基本信息",required = true)
            @RequestBody
            CourseInfoForm courseInfoForm){
       String courseId =  courseService.saveCourseInfo(courseInfoForm);


       if(courseId == null){
           return R.error().message("保存失败");
       }
        return R.ok().data("courseId",courseId).message("保存成功");
    }
    @ApiOperation("修改课程")
    @PostMapping("update-course-info")
    public R updateCourseInfo(
            @ApiParam(value = "修改的课程基本信息",required = true)
            @RequestBody
            CourseInfoForm courseInfoForm){
       String courseId =  courseService.updateCourseInfo(courseInfoForm);


       if(courseId == null){
           return R.error().message("保存失败");
       }
        return R.ok().data("courseId",courseId).message("保存成功");
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("course-info/{id}")
    public R getById(
            @ApiParam(value = "id值",required = true)
           @PathVariable String id){
        CourseInfoForm courseInfoForm = courseService.getCourseInfoById(id);
        if (courseInfoForm != null) {
            return R.ok().message("查询成功").data("item",courseInfoForm);
        }
        return R.error().message("数据不存在");
    }
}

