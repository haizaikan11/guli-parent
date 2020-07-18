package com.wulx.guli.service.edu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wulx.guli.service.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.wulx.guli.service.edu.entity.query.TeacherQuery;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
public interface TeacherService extends IService<Teacher> {
        IPage<Teacher> selectPage(Long page, Long limit, TeacherQuery teacherQuery);
}
