package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.domain.S_selected_course;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.service.CourseService;
import com.wdd.studentmanager.service.SelectedCourseService;
import org.hamcrest.Condition;
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
@RequestMapping("/course")
public class CourseController {
    @Autowired
    SelectedCourseService selectedCourseService;

    @Autowired
    CourseService courseService;
    @RequestMapping("/get_courseInfo")
    @ResponseBody
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
    @ResponseBody
    public ResultData SelectCourse(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        S_student currentStu = (S_student) session.getAttribute("currentUser");
        String Course_name=request.getParameter("Cname");
        String Teacher_name=request.getParameter("Tname");
        //查看课程是否存在
        QueryWrapper<S_course> courseQuery = new QueryWrapper<>();
        courseQuery.eq("name", Course_name);
        courseQuery.eq("teachername", Teacher_name);
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

    @RequestMapping("/get_all")
    @ResponseBody
    public ResultData get_all_course(HttpServletRequest request){
        List<S_course> s_courses=courseService.list();
        return ResultData.success_course(s_courses);
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResultData add_course(HttpServletRequest request){
         String name=request.getParameter("name");
         String teachername=request.getParameter("teachername");
         String coursedate=request.getParameter("coursedate");
         String courseplace=request.getParameter("courseplace");
         S_course s_course=new S_course();
         s_course.setName(name);
         s_course.setTeachername(teachername);
         s_course.setCoursedate(coursedate);
         s_course.setCourseplace(courseplace);
         boolean result=courseService.save(s_course);
         if(result){
             return ResultData.success("增加课程成功!");
         }
         else {
             return ResultData.fail("增加课程失败");
         }
    }

    @RequestMapping("/sub")
    @ResponseBody
    public ResultData sub_course(HttpServletRequest request){
        String Cname=request.getParameter("Cname");
        String Tname=request.getParameter("Tname");
        QueryWrapper<S_course> s_courseQueryWrapper=new QueryWrapper<>();
        s_courseQueryWrapper.eq("name",Cname);
        s_courseQueryWrapper.eq("teachername",Tname);
        boolean result=courseService.remove(s_courseQueryWrapper);
        if(result){
            return ResultData.success("删除课程成功!");
        }
        else {
            return ResultData.fail("删除课程失败");
        }
    }
}