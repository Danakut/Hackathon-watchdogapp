package cz.danakut.coursewatchdog.watchdogapp;

import java.sql.SQLException;
import java.util.List;

public interface CourseRepository {

   public Course findCourse(int id) throws SQLException;
   public List<Course> findAllCourses() throws SQLException;
}
