package com.wdd.studentmanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wdd.studentmanager.common.ResultData;
import com.wdd.studentmanager.domain.S_course;
import com.wdd.studentmanager.domain.S_dormit;
import com.wdd.studentmanager.domain.S_selected_course;
import com.wdd.studentmanager.domain.S_student;
import com.wdd.studentmanager.mapper.CourseMapper;
import com.wdd.studentmanager.service.CourseService;
import com.wdd.studentmanager.service.SelectedCourseService;
import com.wdd.studentmanager.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    StudentService studentService;

    @RequestMapping("/get_all")
    @ResponseBody
    public ResultData Getallcourse(@RequestBody S_course sCourse){
        System.out.println(sCourse);
        if(sCourse.getName()!=null){
            QueryWrapper<S_course> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name", sCourse.getName());
            List<S_course> courses = courseService.list(queryWrapper);
            return ResultData.success(courses);
        }

        if(sCourse.getTeachername()!=null){
            QueryWrapper<S_course> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teachername    ", sCourse.getTeachername());
            List<S_course> courses = courseService.list(queryWrapper);
            System.out.println(courses);
            return ResultData.success(courses);
        }

        else{
            return ResultData.fail("查找失败");
        }
    }

    @RequestMapping("/get_courseInfo")
    @ResponseBody
    public ResultData GetcourseInfo(@RequestBody S_student student){
        int stu_id=student.getId();

        //用学生的id匹配选课记录
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
        System.out.println(courses);
        return ResultData.success(courses);
    }

    @RequestMapping("/select_course")
    @ResponseBody
    public ResultData SelectCourse(@RequestBody S_course sCourse) {

        //HttpSession session = request.getSession(false);
        //S_student currentStu = (S_student) session.getAttribute("currentUser");
        /*System.out.println(Teacher_name);
        System.out.println(Course_name);
        System.out.println(Stu_id);*/
        System.out.println(sCourse.toString());

        S_student student=studentService.getById(sCourse.getStuId());
        //查看课程是否存在
        QueryWrapper<S_course> courseQuery = new QueryWrapper<>();
        courseQuery.eq("name", sCourse.getName());
        courseQuery.eq("teachername", sCourse.getTeachername());
        S_course course = courseService.getOne(courseQuery);//匹配课程

        if (course == null) {
            return ResultData.fail("Course not found");
        }

        // 判断是否选过该课
        QueryWrapper<S_selected_course> selectedCourseQuery = new QueryWrapper<>();
        selectedCourseQuery.eq("courseid", course.getId());
        selectedCourseQuery.eq("studentid", sCourse.getStuId());

        int count = (int) selectedCourseService.count(selectedCourseQuery);

        if (count > 0) {
            return ResultData.fail("You have already selected this course");
        }

        //选择该课程
        S_selected_course userSelection = new S_selected_course();
        userSelection.setCourseid(course.getId());
        userSelection.setStudentid(sCourse.getStuId());

        boolean saveResult = selectedCourseService.save(userSelection);
        if (saveResult) {
            return ResultData.success("Course selected successfully");
        } else {
            return ResultData.fail("Failed to select course, please try again later");
        }

    }

    @RequestMapping("/getc")
    @ResponseBody
    public ResultData getc(){

        List<String> names=courseMapper.getDistinctCourseNames();
        return ResultData.success(names);
    }
}