package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.domain.S_selected_course;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.CourseService;
import com.wdd.studentmanager.service.SelectedCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@ResponseBody
@RequestMapping("/course")
public class CourseController {
    @Autowired
    SelectedCourseService selectedCourseService;

    @Autowired
    CourseService courseService;
    @RequestMapping("/get_courseInfo")
    public ResultData GetcourseInfo(HttpServletRequest request){
        HttpSession session=request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");
        //用学生的id匹配选课记录
        int stu_id=currentStu.getId();
        QueryWrapper<S_selected_course> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("studentid",stu_id);
        List<S_selected_course> selectedcourses=selectedCourseService.list(queryWrapper);

        if(selectedcourses==null){
            return ResultData.fail("没有选课");
        }

        List<Integer> courseIds = selectedcourses.stream()
                .map(S_selected_course::getCourseid)
                .collect(Collectors.toList());//通过学生id找出其已选课程的id

        QueryWrapper<S_course> courseQueryWrapper = new QueryWrapper<>();
        courseQueryWrapper.in("id", courseIds);//这里为course里面的id
        List<S_course> courses = courseService.list(courseQueryWrapper);
        if(courses==null){
            return ResultData.fail("没有已选的课程");
        }
        return ResultData.success(courses);
    }

    @RequestMapping("/select_course")
    public ResultData SelectCourse(@RequestParam("Teacher_name") Integer Teacher_id,//name
                                   @RequestParam("Course_name") Integer Course_id,//name
                                   HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");

        //查看课程是否存在
        QueryWrapper<S_course> courseQuery = new QueryWrapper<>();
        courseQuery.eq("id", Course_id);
        courseQuery.eq("teacherid", Teacher_id);
        S_course course = courseService.getOne(courseQuery);

        if (course == null) {
            return ResultData.fail("Course not found");
        }

        // 判断是否选过该课
        QueryWrapper<S_selected_course> selectedCourseQuery = new QueryWrapper<>();
        selectedCourseQuery.eq("courseid", course.getId());
        selectedCourseQuery.eq("studentid", currentStu.getId());

        int count = (int) selectedCourseService.count(selectedCourseQuery);

        if (count > 0) {
            return ResultData.fail("You have already selected this course");
        }

        //选择该课程
        S_selected_course userSelection = new S_selected_course();
        userSelection.setCourseid(course.getId());
        userSelection.setStudentid(currentStu.getId());

        boolean saveResult = selectedCourseService.save(userSelection);
        if (saveResult) {
            return ResultData.success("Course selected successfully");
        } else {
            return ResultData.fail("Failed to select course, please try again later");
        }

    }
}