package cz.danakut.coursewatchdog.watchdogapp;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository implements CourseRepository {

    private static String dbUrl = "jdbc:mariadb://localhost:3306/hackathon";
    private static String user = "jetidea";
    private static String pass = "ideapass";

    Connection conn;
    PreparedStatement findStatement;

    public DatabaseRepository() throws SQLException {
        conn = DriverManager.getConnection(dbUrl, user, pass);
    }

    @Override
    public Course findCourse(int id) throws SQLException {
        findStatement = conn.prepareStatement("SELECT * FROM courses WHERE id = ?");
        findStatement.setInt(1, id);
        ResultSet results = findStatement.executeQuery();
        Course course = null;
        if (results.next()) {
            course = mapDatabaseValuesToCourse(results);
        }
        return course;
    }

    @Override
    public List<Course> findAllCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        findStatement = conn.prepareStatement("SELECT * FROM courses");
        ResultSet results = findStatement.executeQuery();

        while (results.next()) {
            Course course = mapDatabaseValuesToCourse(results);
            list.add(course);

        }
        return list;
    }

    public List<Course> findUpcomingCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        findStatement = conn.prepareStatement("SELECT * FROM courses WHERE startDate >= ? AND startDate < ?");
        findStatement.setDate(1, Date.valueOf(LocalDate.now()));
        findStatement.setDate(2, Date.valueOf(getXMonthsFromNow(2)));
        ResultSet results = findStatement.executeQuery();
        while (results.next()) {
            Course course = mapDatabaseValuesToCourse(results);
            list.add(course);
        }

        return list;
    }

    private Course mapDatabaseValuesToCourse(ResultSet results) throws SQLException {
        Course course = new Course();
        course.id = results.getInt("id");
        course.type = CourseType.WORKSHOP;
        course.startDate = results.getDate("startDate");
        course.endDate = results.getDate("endDate");
        String sTime = results.getString("startTime");
        if (sTime == null) {
            course.startTime = null;
        } else {
            course.startTime = sTime.substring(0,5);
        }
        String eTime = results.getString("endTime");
        if (eTime == null) {
            course.endTime = null;
        } else {
            course.endTime = eTime.substring(0,5);
        }
        course.topic = results.getString("topic");
        course.knowledgeLevel = results.getInt("knowledgeLevel");
        course.name = results.getString("name");
        course.status = RegistrationStatus.OTEVRENA;
        course.quickLocation = results.getString("quickLocation");
        course.link = results.getString("link");
        course.lastUpdate = results.getTimestamp("lastUpdate");
        return course;

    }

    private LocalDate getXMonthsFromNow(int x) {
        LocalDate today = LocalDate.now();
        int totalMonthCount = today.getMonth().getValue() + x;

        if (totalMonthCount > 12) {
            return today.withMonth(totalMonthCount - 12).withYear(today.getYear() + 1);
        } else {
            return today.plusMonths(x);
        }

    }





}


