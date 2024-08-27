package com.wdd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdd.studentmanager.domain.S_student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<S_student> {
    @Select("SELECT * FROM s_student") // 假设数据库表名是students
    List<S_student> getAllStudents();

    @Select("SELECT username FROM S_student WHERE id = #{studentId}")
    String getNameById(int studentId);

}
