package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.mapper.StudentMapper;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, S_student> implements StudentService {

}
