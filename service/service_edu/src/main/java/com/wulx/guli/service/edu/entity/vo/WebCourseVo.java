package com.wulx.guli.service.edu.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("课程对象")
@Data
public class WebCourseVo {
    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程销售价格")
    private String price;
    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;
    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;
    @ApiModelProperty(value = "销售数量")
    private Long buyCount;
    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;
    @ApiModelProperty(value = "课程描述")
    private String description;
    @ApiModelProperty(value = "讲师ID")
    private String teacherId;
    @ApiModelProperty(value = "讲师姓名")
    private String teacherName;
    @ApiModelProperty(value = "讲师介绍")
    private String intro;
    @ApiModelProperty(value = "讲师头像图片路径")
    private String avatar;
    @ApiModelProperty(value = "一级分类ID")
    private String subjectLevelOneId;
    @ApiModelProperty(value = "一级分类标题")
    private String subjectLevelOne;
    @ApiModelProperty(value = "二级分类ID")
    private String subjectLevelTwoId;
    @ApiModelProperty(value = "二级分类标题")
    private String subjectLevelTwo;
}