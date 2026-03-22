package com.cognizant.utp.dao;

import java.util.List;

import com.cognizant.utp.models.Course;
import com.cognizant.utp.models.Enrollment;
import com.cognizant.utp.models.Student;

public interface EnrollmentDao { 
    long enrollStudent(Enrollment enrollment);
    void enrollStudentInMultipleCourses(long studentId, List<Long> courseIds);
    List<Course> getCoursesByStudentId(long studentId);
    List<Student> getStudentsByCourseId(long courseId);
    boolean updateEnrollmentStatus(long enrollmentId, String status);
    boolean deleteEnrollment(long enrollmentId);
}
