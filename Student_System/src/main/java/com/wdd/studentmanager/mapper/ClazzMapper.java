package com.wdd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdd.studentmanager.domain.S_clazz;
import com.wdd.studentmanager.domain.S_course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClazzMapper extends BaseMapper<S_clazz> {
}
