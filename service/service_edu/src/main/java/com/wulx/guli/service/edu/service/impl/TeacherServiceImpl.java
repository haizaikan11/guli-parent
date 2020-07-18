package com.wulx.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wulx.guli.service.edu.controller.admin.TeacherController;
import com.wulx.guli.service.edu.entity.Teacher;
import com.wulx.guli.service.edu.entity.query.TeacherQuery;
import com.wulx.guli.service.edu.mapper.TeacherMapper;
import com.wulx.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {


    @Override
    public IPage<Teacher> selectPage(Long page, Long limit, TeacherQuery teacherQuery) {
        Page<Teacher> pageParam = new Page<>(page,limit);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");

        if(teacherQuery == null){
            return baseMapper.selectPage(pageParam,wrapper);
        }
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getJoinDateBegin();
        String end = teacherQuery.getJoinDateEnd();teacherQuery.getLevel();

        if (!StringUtils.isEmpty(name)){
            //左%会使索引失败
            wrapper.likeRight("name",name);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("join_date",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("join_date",end);
        }
        if(level != null){
            wrapper.eq("level" ,level);
        }

        return baseMapper.selectPage(pageParam,wrapper);
    }
}
