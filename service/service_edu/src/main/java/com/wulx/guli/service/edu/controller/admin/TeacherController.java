package com.wulx.guli.service.edu.controller.admin;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.entity.Teacher;
import com.wulx.guli.service.edu.entity.query.TeacherQuery;
import com.wulx.guli.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    /**
     * 获取所有teacher的集合
     * @return
     */
    @ApiOperation("teacher集合")
    @GetMapping("/list")
    public R listAll(){

        List<Teacher> list = teacherService.list();
        return   R.ok().data("items",list).message("获取讲师列表成功");
    }

    /**
     * note : 扩展说明
     * 根据id删除
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id删除",notes = "根据id删除指定的讲师")
    @DeleteMapping("/remove/{id}")
     public R removeById(@ApiParam(value = "讲师id",readOnly = true) @PathVariable Integer id){

        boolean b = teacherService.removeById(id);
        if(b){
            return R.ok().message("删除成功");
        }
       return R.error().message("删除失败");
    }

    /**
     *
     * @param page
     * @param limit
     * @param teacherQuery  //不是必须的
     * @return
     */
    @ApiOperation("分页查询")
    @GetMapping("/listPage/{page}/{limit}")
     public R listPage(
            @ApiParam(value = "当前页面数",required = true) @PathVariable Long page,
            @ApiParam(value = "每页记录数",required = true) @PathVariable Long limit,
            @ApiParam(value = "讲师id")  TeacherQuery teacherQuery
            ){
        IPage<Teacher> teacherIPage = teacherService.selectPage(page, limit, teacherQuery);
        return R.ok().data("teacherIPage",teacherIPage);

    }
    @ApiOperation("新增讲师")
    @PostMapping("save")
    public R save(
            @ApiParam(value = "新增的讲师",required = true)
           @RequestBody Teacher teacher
    ){
        boolean save = teacherService.save(teacher);
        if(save){
            return R.ok().message("新增成功");
        }else {
            return R.error().message("失败~~~~");
        }
    }
    @ApiOperation("修改讲师")
    @PutMapping("update")
    public R update(
            @RequestBody Teacher teacher
    ){
        boolean b = teacherService.updateById(teacher);
        if (b){
            return R.ok().message("修改成功");
        }else {
            return R.error().message("修改失败");
        }
    }

    @ApiOperation("根据id查找teacher")
    @GetMapping("selectById/{id}")
    public R selectById(
            @ApiParam("讲师id")
            @PathVariable  Long id
    ){
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return R.ok().data("item",teacher);
        }else {
            return R.error().message("错误id");
        }
    }


}

