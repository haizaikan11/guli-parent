package com.wulx.guli.service.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wulx.guli.service.edu.entity.Chapter;
import com.wulx.guli.service.edu.entity.Course;
import com.wulx.guli.service.edu.entity.Video;
import com.wulx.guli.service.edu.entity.vo.ChapterVo;
import com.wulx.guli.service.edu.entity.vo.VideoVo;
import com.wulx.guli.service.edu.mapper.ChapterMapper;
import com.wulx.guli.service.edu.mapper.VideoMapper;
import com.wulx.guli.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wulx.guli.service.edu.service.CourseService;
import com.wulx.guli.service.edu.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    VideoService videoService;
    /**
     * 根据id删除章节及章节下的课时
     * @param id
     * @return
     */
    @Override
    public boolean removeChapterById(String id) {
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        // 该章节下的课时
        queryWrapper.eq("chapter_id",id);
        videoService.removeById(queryWrapper);

        return this.removeById(id);
    }


    @Autowired
    VideoMapper videoMapper;

    /**
     * 根据课程id获取章节集合，并将相应章节下的课时集合放入children
     * @param courseId
     * @return
     */
    @Override
    public List<ChapterVo> nestedList(String courseId) {
        // 创建章节视图对象集合
        ArrayList<ChapterVo> chapterVos = new ArrayList<>();

        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        // 查询条件：课程id
        chapterQueryWrapper.eq("course_id",courseId);
        chapterQueryWrapper.orderByDesc("sort","id");
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);
        // 通过课程id获取到所有的课时对象
        QueryWrapper<Video> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.orderByDesc("sort","id");
        videoQueryWrapper.eq("course_id",courseId);
        List<Video> videoList = videoMapper.selectList(videoQueryWrapper);
        // 遍历章节集合，将获取到的章节持久对象数据放入视图对象
        for (Chapter chapter : chapterList) {
            // 创建课时视图对象集合，不能复用
            ArrayList<VideoVo> videoVos = new ArrayList<>();
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter,chapterVo);
            // 遍历课时集合
            for (Video video : videoList) {
                if (chapter.getId().equals(video.getChapterId())){
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video,videoVo);
                    videoVos.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVos);

            //将章节视图对象放入集合
            chapterVos.add(chapterVo);
        }

        return chapterVos;
    }
}
