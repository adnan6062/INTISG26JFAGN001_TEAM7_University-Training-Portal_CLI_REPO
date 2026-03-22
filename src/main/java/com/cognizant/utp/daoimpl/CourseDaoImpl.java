package com.cognizant.utp.daoimpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.cognizant.utp.dao.CourseDao;
import com.cognizant.utp.models.Course;
import com.cognizant.utp.util.DBConnection;

public class CourseDaoImpl implements CourseDao {

    @Override
    public long createCourse(Course course) {
        String sql = """
            INSERT INTO courses (course_code, course_name, duration_weeks, status)
            VALUES (?, ?, ?, ?)
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, course.getCourseCode());
            ps.setString(2, course.getCourseName());
            ps.setInt(3, course.getDurationWeeks());
            ps.setString(4, course.getStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error creating course", e);
        }
        return -1;
    }

    @Override
    public Course getCourseById(long courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ? AND is_deleted = FALSE";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapCourse(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching course", e);
        }
        return null;
    }

    @Override
    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM courses WHERE is_deleted = FALSE";

        List<Course> courses = new ArrayList<>();

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                courses.add(mapCourse(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching courses", e);
        }
        return courses;
    }

    @Override
    public boolean updateCourse(Course course) {
        String sql = """
            UPDATE courses
            SET course_name = ?, duration_weeks = ?, status = ?
            WHERE course_id = ? AND is_deleted = FALSE
            """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, course.getCourseName());
            ps.setInt(2, course.getDurationWeeks());
            ps.setString(3, course.getStatus());
            ps.setLong(4, course.getCourseId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating course", e);
        }
    }

    @Override
    public boolean deleteCourse(long courseId) {
        String sql = "UPDATE courses SET is_deleted = TRUE WHERE course_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, courseId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting course", e);
        }
    }

    private Course mapCourse(ResultSet rs) throws SQLException {
        Course c = new Course();

        c.setCourseId(rs.getLong("course_id"));
        c.setCourseCode(rs.getString("course_code"));
        c.setCourseName(rs.getString("course_name"));
        c.setDurationWeeks(rs.getInt("duration_weeks"));
        c.setStatus(rs.getString("status"));

        c.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        c.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        c.setDeleted(rs.getBoolean("is_deleted"));

        return c;
    }

	@Override
	public Course getCourseByCode(String courseCode) {
	    String sql = """
	        SELECT * FROM courses
	        WHERE course_code = ?
	          AND is_deleted = FALSE
	        """;

	    try (Connection con = DBConnection.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	        ps.setString(1, courseCode);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return mapCourse(rs);
	            }
	        }

	    } catch (SQLException e) {
	        throw new RuntimeException("Error fetching course by code", e);
	    }

	    return null;
	}
}
