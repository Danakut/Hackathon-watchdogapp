package cz.danakut.coursewatchdog.watchdogapp;

import java.lang.instrument.Instrumentation;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository implements CourseRepository {

    private static String dbUrl = "jdbc:mariadb://localhost:3306/hackathon";
    private static String user = "jetidea";
    private static String pass = "ideapass";

    Connection conn;

    public DatabaseRepository() throws SQLException {
        conn = DriverManager.getConnection(dbUrl, user, pass);
    }

    @Override
    public Course findCourse(int id) throws SQLException {
        PreparedStatement findStatement = conn.prepareStatement("SELECT * FROM courses WHERE id = ?");
        findStatement.setInt(1, id);
        ResultSet results = findStatement.executeQuery();
        Course course = null;
        if (results.next()) {
            course = mapDatabaseValuesToCourse(results);
        }
        findStatement.close();
        return course;
    }

    @Override
    public List<Course> findAllCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        PreparedStatement findStatement = conn.prepareStatement("SELECT * FROM courses");
        ResultSet results = findStatement.executeQuery();

        while (results.next()) {
            Course course = mapDatabaseValuesToCourse(results);
            list.add(course);

        }

        findStatement.close();
        return list;
    }

    public List<Course> findUpcomingCourses() throws SQLException {
        List<Course> list = new ArrayList<>();
        PreparedStatement findStatement = conn.prepareStatement("SELECT * FROM courses WHERE startDate >= ? AND startDate < ?");
        findStatement.setDate(1, Date.valueOf(LocalDate.now()));
        findStatement.setDate(2, Date.valueOf(getXMonthsFromNow(1)));
        ResultSet results = findStatement.executeQuery();
        while (results.next()) {
            Course course = mapDatabaseValuesToCourse(results);
            list.add(course);
        }

        findStatement.close();
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
        course.description = results.getString("description");

        List<String> instructorList = new ArrayList<>();
        String instructorCourseQuery = "SELECT firstname, lastname  FROM coursesAndInstructors " +
                "JOIN instructors ON coursesAndInstructors.instructorId = instructors.id " +
                "WHERE courseId = ?;";
        PreparedStatement instructorStatement = conn.prepareStatement(instructorCourseQuery);
        instructorStatement.setInt(1, course.id);
        ResultSet instructorResults = instructorStatement.executeQuery();
        while (instructorResults.next()) {
            String name = instructorResults.getString("firstname") + " " + instructorResults.getString("lastname");
            instructorList.add(name);
        }
        instructorStatement.close();
        course.instructors = instructorList;

        Location newLocation = new Location();
        String locationQuery = "SELECT locations.name, locations.street, locations.city, locations.postalCode FROM courses JOIN locations " +
                "ON courses.location = locations.id " +
                "WHERE courses.id = ?;";
        PreparedStatement locationStatement = conn.prepareStatement(locationQuery);
        locationStatement.setInt(1, course.id);
        ResultSet locationResults = locationStatement.executeQuery();
        if (locationResults.next()) {
            newLocation.name = locationResults.getString("name");
            newLocation.street = locationResults.getString("street");
            newLocation.city = locationResults.getString("city");
            newLocation.postalCode = locationResults.getString("postalCode");
        }
        course.location = newLocation;

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


