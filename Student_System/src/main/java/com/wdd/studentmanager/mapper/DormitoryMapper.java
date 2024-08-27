package com.wdd.studentmanager.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wdd.studentmanager.domain.S_dormit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DormitoryMapper extends BaseMapper<S_dormit> {
    @Select("SELECT * FROM s_dormit")
    List<S_dormit> getAlldormit();

}
