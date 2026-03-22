package com.cognizant.utp;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.cognizant.utp.models.Course;
import com.cognizant.utp.models.Student;
import com.cognizant.utp.models.Trainer;
import com.cognizant.utp.service.CourseService;
import com.cognizant.utp.service.EnrollmentService;
import com.cognizant.utp.service.StudentService;
import com.cognizant.utp.service.TrainerService;

public class App {

    private static final StudentService studentService = new StudentService();
    private static final CourseService courseService = new CourseService();
    private static final TrainerService trainerService = new TrainerService();
    private static final EnrollmentService enrollmentService = new EnrollmentService();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n========== UNIVERSITY TRAINING PROGRAM ==========");
            System.out.println("1. Create Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");

            System.out.println("5. Create Course");
            System.out.println("6. View All Courses");
            System.out.println("7. Update Course");
            System.out.println("8. Delete Course");

            System.out.println("9. Create Trainer");
            System.out.println("10. View All Trainers");
            System.out.println("11. Update Trainer");
            System.out.println("12. Delete Trainer");

            System.out.println("13. Enroll Student in Courses");
            System.out.println("14. View Courses by Student");
            System.out.println("15. View Students by Course");
            System.out.println("16. Update Enrollment Status");
            System.out.println("17. Delete Enrollment");

            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                // ---------- STUDENT ----------
                case 1 -> createStudent(sc);
                case 2 -> viewAllStudents();
                case 3 -> updateStudent(sc);
                case 4 -> deleteStudent(sc);

                // ---------- COURSE ----------
                case 5 -> createCourse(sc);
                case 6 -> viewAllCourses();
                case 7 -> updateCourse(sc);
                case 8 -> deleteCourse(sc);

                // ---------- TRAINER ----------
                case 9 -> createTrainer(sc);
                case 10 -> viewAllTrainers();
                case 11 -> updateTrainer(sc);
                case 12 -> deleteTrainer(sc);

                // ---------- ENROLLMENT ----------
                case 13 -> enrollStudentInCourses(sc);
                case 14 -> viewCoursesByStudent(sc);
                case 15 -> viewStudentsByCourse(sc);
                case 16 -> updateEnrollmentStatus(sc);
                case 17 -> deleteEnrollment(sc);

                case 0 -> {
                    System.out.println("Exiting application...");
                    sc.close();
                    System.exit(0);
                }

                default -> System.out.println("Invalid choice!");
            }
        }
    }

    // ================= STUDENT METHODS =================

    private static void createStudent(Scanner sc) {
        Student s = new Student();

        System.out.print("Name: ");
        s.setName(sc.nextLine());
        System.out.print("Email: ");
        s.setEmail(sc.nextLine());
        System.out.print("Username: ");
        s.setUsername(sc.nextLine());
        System.out.print("Mobile: ");
        s.setMobileNumber(sc.nextLine());
        System.out.print("Department: ");
        s.setDepartment(sc.nextLine());
        System.out.print("Academic Year: ");
        s.setAcademicYear(sc.nextLine());

        s.setStatus("ACTIVE");

        long id = studentService.createStudent(s);
        System.out.println("Student created with ID: " + id);
    }

    private static void viewAllStudents() {
        studentService.getAllStudents()
                .forEach(s -> System.out.println(
                        s.getStudentId() + " | " +
                        s.getName() + " | " +
                        s.getEmail()));
    }

    private static void updateStudent(Scanner sc) {
        System.out.print("Student ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        Student s = studentService.getStudentById(id);
        if (s == null) {
            System.out.println("Student not found");
            return;
        }

        System.out.print("New Name: ");
        s.setName(sc.nextLine());
        System.out.print("New Mobile: ");
        s.setMobileNumber(sc.nextLine());

        System.out.println(
                studentService.updateStudent(s)
                        ? "Student updated"
                        : "Update failed");
    }

    private static void deleteStudent(Scanner sc) {
        System.out.print("Student ID: ");
        long id = sc.nextLong();

        System.out.println(
                studentService.deleteStudent(id)
                        ? "Student deleted"
                        : "Delete failed");
    }

    // ================= COURSE METHODS =================

    private static void createCourse(Scanner sc) {
        Course c = new Course();

        System.out.print("Course Code: ");
        c.setCourseCode(sc.nextLine());
        System.out.print("Course Name: ");
        c.setCourseName(sc.nextLine());
        System.out.print("Duration (weeks): ");
        c.setDurationWeeks(sc.nextInt());
        sc.nextLine();

        c.setStatus("ACTIVE");

        long id = courseService.createCourse(c);
        System.out.println("Course created with ID: " + id);
    }

    private static void viewAllCourses() {
        courseService.getAllCourses()
                .forEach(c -> System.out.println(
                        c.getCourseId() + " | " +
                        c.getCourseCode() + " | " +
                        c.getCourseName()));
    }

    private static void updateCourse(Scanner sc) {
        System.out.print("Course ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        Course c = courseService.getCourseById(id);
        if (c == null) {
            System.out.println("Course not found");
            return;
        }

        System.out.print("New Course Name: ");
        c.setCourseName(sc.nextLine());

        System.out.print("New Duration: ");
        c.setDurationWeeks(sc.nextInt());
        sc.nextLine();

        System.out.println(
                courseService.updateCourse(c)
                        ? "Course updated"
                        : "Update failed");
    }

    private static void deleteCourse(Scanner sc) {
        System.out.print("Course ID: ");
        long id = sc.nextLong();

        System.out.println(
                courseService.deleteCourse(id)
                        ? "Course deleted"
                        : "Delete failed");
    }

    // ================= TRAINER METHODS =================

    private static void createTrainer(Scanner sc) {
        Trainer t = new Trainer();

        System.out.print("Trainer Name: ");
        t.setTrainerName(sc.nextLine());
        System.out.print("Email: ");
        t.setEmail(sc.nextLine());
        System.out.print("Expertise: ");
        t.setExpertise(sc.nextLine());
        System.out.print("Trainer Type: ");
        t.setTrainerType(sc.nextLine());

        long id = trainerService.createTrainer(t);
        System.out.println("Trainer created with ID: " + id);
    }

    private static void viewAllTrainers() {
        trainerService.getAllTrainers()
                .forEach(t -> System.out.println(
                        t.getTrainerId() + " | " +
                        t.getTrainerName() + " | " +
                        t.getExpertise()));
    }

    private static void updateTrainer(Scanner sc) {
        System.out.print("Trainer ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        Trainer t = trainerService.getTrainerById(id);
        if (t == null) {
            System.out.println("Trainer not found");
            return;
        }

        System.out.print("New Expertise: ");
        t.setExpertise(sc.nextLine());

        System.out.println(
                trainerService.updateTrainer(t)
                        ? "Trainer updated"
                        : "Update failed");
    }

    private static void deleteTrainer(Scanner sc) {
        System.out.print("Trainer ID: ");
        long id = sc.nextLong();

        System.out.println(
                trainerService.deleteTrainer(id)
                        ? "Trainer deleted"
                        : "Delete failed");
    }

    // ================= ENROLLMENT METHODS =================

    private static void enrollStudentInCourses(Scanner sc) {
        System.out.print("Student ID: ");
        long studentId = sc.nextLong();

        System.out.print("Number of courses: ");
        int count = sc.nextInt();

        List<Long> courseIds = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            System.out.print("Course ID: ");
            courseIds.add(sc.nextLong());
        }

        enrollmentService.enrollStudentInMultipleCourses(studentId, courseIds);
        System.out.println("Enrollment successful");
    }

    private static void viewCoursesByStudent(Scanner sc) {
        System.out.print("Student ID: ");
        long studentId = sc.nextLong();

        enrollmentService.getCoursesByStudentId(studentId)
                .forEach(c -> System.out.println(
                        c.getCourseId() + " | " +
                        c.getCourseName()));
    }

    private static void viewStudentsByCourse(Scanner sc) {
        System.out.print("Course ID: ");
        long courseId = sc.nextLong();

        enrollmentService.getStudentsByCourseId(courseId)
                .forEach(s -> System.out.println(
                        s.getStudentId() + " | " +
                        s.getName()));
    }

    private static void updateEnrollmentStatus(Scanner sc) {
        System.out.print("Enrollment ID: ");
        long id = sc.nextLong();
        sc.nextLine();

        System.out.print("New Status: ");
        String status = sc.nextLine();

        System.out.println(
                enrollmentService.updateEnrollmentStatus(id, status)
                        ? "Status updated"
                        : "Update failed");
    }

    private static void deleteEnrollment(Scanner sc) {
        System.out.print("Enrollment ID: ");
        long id = sc.nextLong();

        System.out.println(
                enrollmentService.deleteEnrollment(id)
                        ? "Enrollment deleted"
                        : "Delete failed");
    }
}