package com.wulx.guli.service.edu.controller.api;

import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.entity.vo.SubjectVo;
import com.wulx.guli.service.edu.service.SubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rx.internal.util.SubscriptionList;

import java.util.List;

@CrossOrigin  // 跨域
@Api(tags = "课程分类")
@RestController
@RequestMapping("/api/edu/subject")
public class ApiSubjectController {
    @Autowired
    SubjectService subjectService;;
    @ApiOperation(value = "嵌套数据列表")
    @GetMapping("nested-list")
    public R nestedList(){
        List<SubjectVo> list =  subjectService.nestedList();
        return R.ok().data("items", list);
    }

}
