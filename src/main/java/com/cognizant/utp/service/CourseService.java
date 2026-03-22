package com.cognizant.utp.service;

import java.util.List;

import com.cognizant.utp.daoimpl.CourseDaoImpl;
import com.cognizant.utp.models.Course;

public class CourseService {

    private final CourseDaoImpl courseDao = new CourseDaoImpl();

    public long createCourse(Course course) {
        return courseDao.createCourse(course);
    }

    public Course getCourseById(long courseId) {
        return courseDao.getCourseById(courseId);
    }

    public Course getCourseByCode(String courseCode) {
        return courseDao.getCourseByCode(courseCode);
    }

    public List<Course> getAllCourses() {
        return courseDao.getAllCourses();
    }

    public boolean updateCourse(Course course) {
        return courseDao.updateCourse(course);
    }

    public boolean deleteCourse(long courseId) {
        return courseDao.deleteCourse(courseId);
    }
}