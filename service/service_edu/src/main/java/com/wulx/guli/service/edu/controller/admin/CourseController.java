package com.wulx.guli.service.edu.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.entity.Course;
import com.wulx.guli.service.edu.entity.Teacher;
import com.wulx.guli.service.edu.entity.form.CourseInfoForm;
import com.wulx.guli.service.edu.entity.query.CourseQuery;
import com.wulx.guli.service.edu.entity.query.TeacherQuery;
import com.wulx.guli.service.edu.entity.vo.ChapterVo;
import com.wulx.guli.service.edu.entity.vo.CourseVo;
import com.wulx.guli.service.edu.service.ChapterService;
import com.wulx.guli.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @ApiOperation("分页查询")
    @GetMapping("/listPage/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页面数",required = true) @PathVariable Long page,
            @ApiParam(value = "每页记录数",required = true) @PathVariable Long limit,
            @ApiParam(value = "讲师id") CourseQuery courseQuery
    ){
        IPage<CourseVo> courseIPage = courseService.selectPage(page, limit, courseQuery);
        // 前端页面需要使用的是总数和页面的
        return R.ok().data("rows",courseIPage.getRecords()).data("total",courseIPage.getTotal());

    }

    @Autowired
    ChapterService chapterService;

    @ApiOperation("嵌套章节数据列表")
    @GetMapping("nested-list/{courseId}")
    public R nestedListByCourseId(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String courseId){


        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);
        return R.ok().data("items", chapterVoList);
    }
}

