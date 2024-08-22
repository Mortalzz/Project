package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_dormit;
import com.wdd.studentmanager.mapper.DormitoryMapper;
import com.wdd.studentmanager.service.DormitoryService;
import org.springframework.stereotype.Service;

@Service
public class DormitoryServiceImpl extends ServiceImpl<DormitoryMapper, S_dormit> implements DormitoryService {
}
