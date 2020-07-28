package com.wulx.guli.service.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.wulx.guli.service.edu.entity.Subject;
import com.wulx.guli.service.edu.entity.excel.ExcelSubjectData;
import com.wulx.guli.service.edu.entity.vo.SubjectVo;
import com.wulx.guli.service.edu.listener.ExcelSubjectDataListener;
import com.wulx.guli.service.edu.mapper.SubjectMapper;
import com.wulx.guli.service.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author wulingxiao
 * @since 2020-07-16
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Override
    public void batchImport(InputStream inputStream) {
        //接收一个输入流和监听器和接收类型得class类型
        EasyExcel.read(inputStream, ExcelSubjectData.class,new ExcelSubjectDataListener(baseMapper)).excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }

    @Override
    public List<SubjectVo> nestedList() {
        return baseMapper.selectNestedListByParentId("0");


    }
}
