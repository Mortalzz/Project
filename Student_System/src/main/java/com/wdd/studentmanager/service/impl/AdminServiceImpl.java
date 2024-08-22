package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_admin;
import com.wdd.studentmanager.mapper.AdminMapper;
import com.wdd.studentmanager.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, S_admin> implements AdminService {
}
