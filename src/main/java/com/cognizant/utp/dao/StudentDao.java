package com.cognizant.utp.dao;

import java.util.List;

import com.cognizant.utp.models.Student;

public interface StudentDao {
    long createStudent(Student student);
    Student getStudentById(long studentId);
    Student getStudentByEmail(String email);
    List<Student> getAllStudents();
    boolean updateStudent(Student student);
    boolean deleteStudent(long studentId);
}
