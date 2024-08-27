package com.wdd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdd.studentmanager.domain.S_leave;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LeaveMapper extends BaseMapper<S_leave> {
    @Select("SELECT * FROM s_leave WHERE status=0")
    List<S_leave> getAllleaves();
}
