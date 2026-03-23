University Training Portal – CLI Application
Overview
This is a menu‑driven Java CLI application built using Core Java, JDBC, MySQL, and Maven.
The project manages Students, Courses, Trainers, and Enrollments following a layered architecture.

Technology Stack

Java
JDBC
MySQL
Maven
Eclipse
Git & GitHub


Project Structure
mysql        - DB connection
dao          - DAO interfaces
daoimpl      - JDBC implementations
service      - Business logic
models       - POJO classes
main         - Application entry point


Features

Create, update, view, delete Students
Create, update, view, delete Courses
Create, update, view, delete Trainers
Enroll students into single or multiple courses
View courses by student
View students by course
Soft delete support
Audit fields handled by database


Database

Database: utpdb
Tables:

students
courses
trainers
enrollments


Audit fields:

created_at
updated_at
is_deleted




JDBC Configuration
Update DBConnection.java:
jdbc:mysql://localhost:3306/utpdb
?useSSL=false
&allowPublicKeyRetrieval=true
&serverTimezone=UTC


How to Run

Create database utpdb in MySQL
Update DB username/password in DBConnection.java
Import project into Eclipse
Run Main.java as Java Application
Use menu options to perform operations


Notes

Audit timestamps are managed by MySQL
Application uses soft delete instead of hard delete
Designed to migrate easily to Spring Boot later


Author
Adnan Shariff
