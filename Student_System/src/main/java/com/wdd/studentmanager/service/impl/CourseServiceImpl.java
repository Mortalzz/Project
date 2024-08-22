package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.mapper.CourseMapper;
import com.wdd.studentmanager.service.CourseService;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, S_course> implements CourseService {
}
