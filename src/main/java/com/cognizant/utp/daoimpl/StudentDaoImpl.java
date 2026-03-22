package com.cognizant.utp.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cognizant.utp.dao.StudentDao;
import com.cognizant.utp.models.Student;
import com.cognizant.utp.util.DBConnection;

public class StudentDaoImpl implements StudentDao {

  

    @Override
    public long createStudent(Student student) {
        String sql = """
            INSERT INTO students (name, email, username, mobile_number, department, academic_year, status) VALUES (?, ?, ?, ?, ?, ?, ?) """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getUsername());
            ps.setString(4, student.getMobileNumber());
            ps.setString(5, student.getDepartment());
            ps.setString(6, student.getAcademicYear());
            ps.setString(7, student.getStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating student", e);
        }

        return -1;
    }



    @Override
    public Student getStudentById(long studentId) {
        String sql = """
            SELECT * FROM students
            WHERE student_id = ?
              AND is_deleted = FALSE
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student by id", e);
        }

        return null;
    }

    @Override
    public Student getStudentByEmail(String email) {
        String sql = """
            SELECT * FROM students
            WHERE email = ?
              AND is_deleted = FALSE
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapStudent(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student by email", e);
        }

        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = """
            SELECT * FROM students
            WHERE is_deleted = FALSE
            ORDER BY created_at DESC
            """;

        List<Student> students = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                students.add(mapStudent(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching all students", e);
        }

        return students;
    }

   

    @Override
    public boolean updateStudent(Student student) {
        String sql = """
            UPDATE students
            SET name = ?, mobile_number = ?, department = ?, academic_year = ?, status = ?
            WHERE student_id = ?
              AND is_deleted = FALSE
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getMobileNumber());
            ps.setString(3, student.getDepartment());
            ps.setString(4, student.getAcademicYear());
            ps.setString(5, student.getStatus());
            ps.setLong(6, student.getStudentId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating student", e);
        }
    }

    

    @Override
    public boolean deleteStudent(long studentId) {
        String sql = """
            UPDATE students
            SET is_deleted = TRUE
            WHERE student_id = ?
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, studentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting student", e);
        }
    }



    private Student mapStudent(ResultSet rs) throws SQLException {
        Student student = new Student();

        student.setStudentId(rs.getLong("student_id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setUsername(rs.getString("username"));
        student.setMobileNumber(rs.getString("mobile_number"));
        student.setDepartment(rs.getString("department"));
        student.setAcademicYear(rs.getString("academic_year"));
        student.setStatus(rs.getString("status"));

        student.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        student.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        student.setDeleted(rs.getBoolean("is_deleted"));

        return student;
    }
}
