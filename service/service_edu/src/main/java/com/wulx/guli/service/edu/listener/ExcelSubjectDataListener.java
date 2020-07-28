package com.wulx.guli.service.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wulx.guli.service.edu.entity.Subject;
import com.wulx.guli.service.edu.entity.excel.ExcelSubjectData;
import com.wulx.guli.service.edu.mapper.SubjectMapper;
import com.wulx.guli.service.edu.service.SubjectService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSubjectDataListener extends AnalysisEventListener<ExcelSubjectData> {

    private SubjectMapper subjectMapper;


    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        log.info("解析到一条数据：{}" , excelSubjectData );
        String levelOneTitle = excelSubjectData.getLevelOneTitle();
        String levelTwoTitle = excelSubjectData.getLevelTwoTitle();
        log.info("一级标题：{}" , levelOneTitle);
        log.info("二级标题：{}" , levelTwoTitle);

        String  parentId = null;
        //一级标题得查询
        Subject subjectLevelOne = getByTitle(levelOneTitle);
        if(subjectLevelOne==null){
            Subject subject = new Subject();
            subject.setParentId("0");
            subject.setSort(0);
            subject.setTitle(levelOneTitle);

            subjectMapper.insert(subject);
            parentId = subject.getId();
            System.out.println(parentId + "===========================================");
        }else {
            //System.out.println(subjectLevelOne);
            parentId = subjectLevelOne.getId();
        }
        //判断二级分类是否重复
        Subject subjectLevelTwo = this.queryTitle(levelTwoTitle, parentId);
        if(subjectLevelTwo == null){
            //将二级分类存入数据库
            Subject subject = new Subject();
            subject.setTitle(levelTwoTitle);
            subject.setParentId(parentId);
            subjectMapper.insert(subject);//添加
        }
    }


    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    /**
     *
     * @param title  标题名
     * @param parentId   父id
     * @return
     */
    private Subject queryTitle(String title,String parentId){
//        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("title",title);
//        queryWrapper.eq("parent_id",parentId);
//        return subjectMapper.selectOne(queryWrapper);

        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", parentId);
        return subjectMapper.selectOne(queryWrapper);
    }

    private Subject getByTitle(String title) {

        QueryWrapper<Subject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title", title);
        queryWrapper.eq("parent_id", "0");//一级分类
        return subjectMapper.selectOne(queryWrapper);
    }
}
