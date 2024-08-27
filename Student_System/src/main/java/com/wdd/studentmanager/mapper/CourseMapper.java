package com.wdd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdd.studentmanager.domain.S_course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<S_course> {
    @Select("SELECT DISTINCT name FROM S_course")
    List<String> getDistinctCourseNames();
}
