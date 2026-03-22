package com.cognizant.utp.dao;

import java.util.List;

import com.cognizant.utp.models.Course;

public interface CourseDao {
    long createCourse(Course course);
    Course getCourseById(long courseId); 
    Course getCourseByCode(String courseCode);
    List<Course> getAllCourses();
    boolean updateCourse(Course course);
    boolean deleteCourse(long courseId);
}