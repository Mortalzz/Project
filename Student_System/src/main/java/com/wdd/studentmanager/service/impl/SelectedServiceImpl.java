package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_selected_course;
import com.wdd.studentmanager.mapper.SelectedCourseMapper;
import com.wdd.studentmanager.service.SelectedCourseService;
import org.springframework.stereotype.Service;

@Service
public class SelectedServiceImpl extends ServiceImpl<SelectedCourseMapper, S_selected_course> implements SelectedCourseService {
}
