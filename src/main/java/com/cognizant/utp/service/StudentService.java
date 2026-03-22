package com.cognizant.utp.service;

import java.util.List;

import com.cognizant.utp.daoimpl.StudentDaoImpl;
import com.cognizant.utp.models.Student;

public class StudentService {

    private final StudentDaoImpl studentDao = new StudentDaoImpl();

    public long createStudent(Student student) {
        return studentDao.createStudent(student);
    }

    public Student getStudentById(long studentId) {
        return studentDao.getStudentById(studentId);
    }

    public List<Student> getAllStudents() {
        return studentDao.getAllStudents();
    }

    public boolean updateStudent(Student student) {
        return studentDao.updateStudent(student);
    }

    public boolean deleteStudent(long studentId) {
        return studentDao.deleteStudent(studentId);
    }
}