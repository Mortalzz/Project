package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_teacher;
import com.wdd.studentmanager.mapper.TeacherMapper;
import com.wdd.studentmanager.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, S_teacher> implements TeacherService {
}
