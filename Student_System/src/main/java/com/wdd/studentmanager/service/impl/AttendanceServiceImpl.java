package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_attendance;
import com.wdd.studentmanager.mapper.AttendanceMapper;
import com.wdd.studentmanager.service.AttendanceService;
import org.springframework.stereotype.Service;

@Service
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, S_attendance> implements AttendanceService {
}
