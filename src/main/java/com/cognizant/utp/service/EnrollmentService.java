package com.cognizant.utp.service;

import java.util.List;

import com.cognizant.utp.daoimpl.EnrollmentDaoImpl;
import com.cognizant.utp.models.Course;
import com.cognizant.utp.models.Enrollment;
import com.cognizant.utp.models.Student;

public class EnrollmentService {

    private final EnrollmentDaoImpl enrollmentDao = new EnrollmentDaoImpl();

    public long enrollStudent(long studentId, long courseId, long trainerId) {

        Enrollment enrollment = new Enrollment();
        enrollment.setStudentId(studentId);
        enrollment.setCourseId(courseId);
        enrollment.setTrainerId(trainerId);
        enrollment.setStatus("ENROLLED");

        return enrollmentDao.enrollStudent(enrollment);
    }

    public void enrollStudentInMultipleCourses(long studentId, List<Long> courseIds) {
        enrollmentDao.enrollStudentInMultipleCourses(studentId, courseIds);
    }

    public List<Course> getCoursesByStudentId(long studentId) {
        return enrollmentDao.getCoursesByStudentId(studentId);
    }

    public List<Student> getStudentsByCourseId(long courseId) {
        return enrollmentDao.getStudentsByCourseId(courseId);
    }

    public boolean updateEnrollmentStatus(long enrollmentId, String status) {
        return enrollmentDao.updateEnrollmentStatus(enrollmentId, status);
    }

    public boolean deleteEnrollment(long enrollmentId) {
        return enrollmentDao.deleteEnrollment(enrollmentId);
    }
}