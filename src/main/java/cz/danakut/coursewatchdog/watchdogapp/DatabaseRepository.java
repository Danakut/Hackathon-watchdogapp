package cz.danakut.coursewatchdog.watchdogapp;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import cz.danakut.fill_a_db.*;

public class DatabaseRepository {

    private static String dbUrl = "jdbc:mariadb://localhost:3306/hackathon";
    private static String user = "jetidea";
    private static String pass = "ideapass";

    Connection conn;

    public DatabaseRepository() throws SQLException {
        conn = DriverManager.getConnection(dbUrl, user, pass);
    }

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
        course.setId(results.getInt("id"));

        CourseType type;
        switch (results.getString("type")) {
            case "workshop" :
                type = CourseType.WORKSHOP;
                break;
            case "dlouhodoby" :
                type = CourseType.DLOUHODOBY;
                break;
            case "intenzivni":
                type = CourseType.INTENZIVNI;
                break;
            case "akce":
                type = CourseType.AKCE;
                break;
            case "neurceno":
                type = CourseType.NEURCENO;
                break;
            default:
                type = CourseType.NEURCENO;
        }
        course.setType(type);

        course.setStartDate(results.getDate("startDate"));
        course.setEndDate(results.getDate("endDate"));
        String sTime = results.getString("startTime");
        if (sTime == null) {
            course.setStartDate(null);
        } else {
            course.setStartTime(sTime.substring(0,5));
        }
        String eTime = results.getString("endTime");
        if (eTime == null) {
            course.setEndTime(null);
        } else {
            course.setEndTime(eTime.substring(0,5));
        }
        course.setTopic(results.getString("topic"));
        course.setKnowledgeLevel(results.getInt("knowledgeLevel"));
        course.setName(results.getString("name"));
        course.setStatus(RegistrationStatus.OTEVRENA);
        course.setQuickLocation(results.getString("quickLocation"));
        course.setLink(results.getString("link"));
        course.setDescription(results.getString("description"));

        List<String> instructorList = new ArrayList<>();
        String instructorCourseQuery = "SELECT firstname, lastname  FROM coursesAndInstructors " +
                "JOIN instructors ON coursesAndInstructors.instructorId = instructors.id " +
                "WHERE courseId = ?;";
        PreparedStatement instructorStatement = conn.prepareStatement(instructorCourseQuery);
        instructorStatement.setInt(1, course.getId());
        ResultSet instructorResults = instructorStatement.executeQuery();
        while (instructorResults.next()) {
            String name = instructorResults.getString("firstname") + " " + instructorResults.getString("lastname");
            instructorList.add(name);
        }
        instructorStatement.close();
        course.setInstructors(instructorList);

        Location newLocation = new Location();
        String locationQuery = "SELECT locations.name, locations.street, locations.city, locations.postalCode FROM courses JOIN locations " +
                "ON courses.location = locations.id " +
                "WHERE courses.id = ?;";
        PreparedStatement locationStatement = conn.prepareStatement(locationQuery);
        locationStatement.setInt(1, course.getId());
        ResultSet locationResults = locationStatement.executeQuery();
        if (locationResults.next()) {
            newLocation.setName(locationResults.getString("name"));
            newLocation.setStreet(locationResults.getString("street"));
            newLocation.setCity(locationResults.getString("city"));
            newLocation.setPostalCode(locationResults.getString("postalCode"));
        }
        course.setLocation(newLocation);

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


