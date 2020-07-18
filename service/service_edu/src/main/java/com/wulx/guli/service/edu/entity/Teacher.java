package com.wulx.guli.service.edu.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wulx.guli.service.base.model.BaseEntity;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_teacher")
@ApiModel(value="Teacher对象", description="讲师")
public class Teacher extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "讲师姓名",example = "巫凌霄")
    private String name;

    @ApiModelProperty(value = "讲师简介",example = "年轻,有为")
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师",example = "够格")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师",example = "1")
    private Integer level;

    @ApiModelProperty(value = "讲师头像", example = "img")
    private String avatar;

    @ApiModelProperty(value = "排序" ,example = "1")
    private Integer sort;

    @ApiModelProperty(value = "入驻时间" ,example = "2020-02-22")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    private Date joinDate;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除" ,example = "0")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
