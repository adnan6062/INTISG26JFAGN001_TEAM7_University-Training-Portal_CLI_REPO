package com.cognizant.utp.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.cognizant.utp.dao.EnrollmentDao;
import com.cognizant.utp.models.Course;
import com.cognizant.utp.models.Enrollment;
import com.cognizant.utp.models.Student;
import com.cognizant.utp.util.DBConnection;

public class EnrollmentDaoImpl implements EnrollmentDao {

    // ---------------- SINGLE ENROLL ----------------

    @Override
    public long enrollStudent(Enrollment enrollment) {
        String sql = """
            INSERT INTO enrollments
            (student_id, course_id, trainer_id, enrollment_date, status)
            VALUES (?, ?, ?, CURDATE(), ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, enrollment.getStudentId());
            ps.setLong(2, enrollment.getCourseId());
            ps.setLong(3, enrollment.getTrainerId());
            ps.setString(4, enrollment.getStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error enrolling student", e);
        }
        return -1;
    }

    // ---------------- MULTIPLE COURSE ENROLL ----------------

    @Override
    public void enrollStudentInMultipleCourses(long studentId, List<Long> courseIds) {
        String sql = """
            INSERT INTO enrollments
            (student_id, course_id, enrollment_date, status)
            VALUES (?, ?, CURDATE(), 'ENROLLED')
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);

            for (Long courseId : courseIds) {
                ps.setLong(1, studentId);
                ps.setLong(2, courseId);
                ps.addBatch();
            }

            ps.executeBatch();
            con.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Error enrolling student in courses", e);
        }
    }

    // ---------------- READ: COURSES BY STUDENT ----------------

    @Override
    public List<Course> getCoursesByStudentId(long studentId) {
        String sql = """
            SELECT c.*
            FROM courses c
            JOIN enrollments e ON c.course_id = e.course_id
            WHERE e.student_id = ?
              AND e.is_deleted = FALSE
              AND c.is_deleted = FALSE
            """;

        List<Course> courses = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Course c = new Course();
                    c.setCourseId(rs.getLong("course_id"));
                    c.setCourseCode(rs.getString("course_code"));
                    c.setCourseName(rs.getString("course_name"));
                    c.setDurationWeeks(rs.getInt("duration_weeks"));
                    c.setStatus(rs.getString("status"));

                    c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    c.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    c.setDeleted(rs.getBoolean("is_deleted"));

                    courses.add(c);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching courses for student", e);
        }
        return courses;
    }

    // ---------------- READ: STUDENTS BY COURSE ----------------

    @Override
    public List<Student> getStudentsByCourseId(long courseId) {
        String sql = """
            SELECT s.*
            FROM students s
            JOIN enrollments e ON s.student_id = e.student_id
            WHERE e.course_id = ?
              AND e.is_deleted = FALSE
              AND s.is_deleted = FALSE
            """;

        List<Student> students = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setStudentId(rs.getLong("student_id"));
                    s.setName(rs.getString("name"));
                    s.setEmail(rs.getString("email"));
                    s.setUsername(rs.getString("username"));
                    s.setMobileNumber(rs.getString("mobile_number"));
                    s.setDepartment(rs.getString("department"));
                    s.setAcademicYear(rs.getString("academic_year"));
                    s.setStatus(rs.getString("status"));

                    s.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    s.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                    s.setDeleted(rs.getBoolean("is_deleted"));

                    students.add(s);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching students for course", e);
        }
        return students;
    }

    // ---------------- UPDATE ENROLLMENT STATUS ----------------

    @Override
    public boolean updateEnrollmentStatus(long enrollmentId, String status) {
        String sql = """
            UPDATE enrollments
            SET status = ?
            WHERE enrollment_id = ?
              AND is_deleted = FALSE
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setLong(2, enrollmentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating enrollment status", e);
        }
    }

    // ---------------- SOFT DELETE ----------------

    @Override
    public boolean deleteEnrollment(long enrollmentId) {
        String sql = "UPDATE enrollments SET is_deleted = TRUE WHERE enrollment_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, enrollmentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error un-enrolling student", e);
        }
    }
}
