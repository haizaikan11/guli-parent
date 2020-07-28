package com.wulx.guli.service.edu.controller.admin;


import com.wulx.guli.service.base.exception.GuliException;
import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.base.result.ResultCodeEnum;
import com.wulx.guli.service.edu.entity.vo.SubjectVo;
import com.wulx.guli.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@CrossOrigin
@Api(tags = "课程管理")
@RestController
@RequestMapping("/admin/edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;
    @PostMapping("/import")
    @ApiOperation("excel批量导入课程类型级别")
    public R batchImport(@ApiParam(value = "excel表",required = true) MultipartFile file){
        try {
            subjectService.batchImport(file.getInputStream());
            return R.ok().message("导入成功");
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("nested-list")
    public R nestedList(){
        List<SubjectVo> list =  subjectService.nestedList();
        return R.ok().data("items", list);
    }
}

