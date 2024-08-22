package com.wdd.studentmanager.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wdd.studentmanager.domain.S_clazz;
import com.wdd.studentmanager.mapper.ClazzMapper;
import com.wdd.studentmanager.service.ClazzService;
import org.springframework.stereotype.Service;

@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, S_clazz> implements ClazzService {
}
