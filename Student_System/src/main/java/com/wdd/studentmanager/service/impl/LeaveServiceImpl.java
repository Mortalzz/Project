package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_leave;
import com.wdd.studentmanager.mapper.LeaveMapper;
import com.wdd.studentmanager.service.LeaveService;
import org.springframework.stereotype.Service;

@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, S_leave> implements LeaveService {
}
