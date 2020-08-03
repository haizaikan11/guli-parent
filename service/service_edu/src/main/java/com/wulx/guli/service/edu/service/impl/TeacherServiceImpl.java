package com.wulx.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wulx.guli.service.base.result.R;
import com.wulx.guli.service.edu.controller.admin.TeacherController;
import com.wulx.guli.service.edu.entity.Course;
import com.wulx.guli.service.edu.entity.Teacher;
import com.wulx.guli.service.edu.entity.query.TeacherQuery;
import com.wulx.guli.service.edu.feign.OssFileService;
import com.wulx.guli.service.edu.mapper.CourseMapper;
import com.wulx.guli.service.edu.mapper.TeacherMapper;
import com.wulx.guli.service.edu.service.CourseService;
import com.wulx.guli.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Map<String, Object>> selectNameListByKey(String key) {
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        if (!StringUtils.isEmpty(key)){
            //左%会使索引失败
            wrapper.select("name");

            wrapper.likeRight("name",key);
        }
        return baseMapper.selectMaps(wrapper);
    }

    @Autowired
    OssFileService ossFileService;
    @Override
    public boolean removeAvatarById(String id) {
        //根据id获取数据
        Teacher teacher = baseMapper.selectById(id);
        //确保数据库中数据不为空
        if (teacher == null){
            return false;
        }

        String url = teacher.getAvatar();
        //判定url是否存在
        if(StringUtils.isNotEmpty(url)) {

            R r = ossFileService.removeFile(url);
            return r.getSuccess();
        }
        return false;
    }

    @Autowired
    CourseMapper courseMapper;

    @Override
    public Map<String, Object> selectTeacherInfoById(String id) {
        Teacher teacher = baseMapper.selectById(id);
        QueryWrapper<Course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.eq("teacher_id",id);
        List<Course> courseList = courseMapper.selectList(courseQueryWrapper);
        HashMap<String, Object> map = new HashMap<>();
        map.put("teacher",teacher);
        map.put("courseList",courseList);
        return map;
    }
}
