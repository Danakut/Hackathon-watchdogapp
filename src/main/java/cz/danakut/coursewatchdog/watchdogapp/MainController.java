package cz.danakut.coursewatchdog.watchdogapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.List;
import cz.danakut.fill_a_db.*;

@Controller
public class MainController {

    private DatabaseRepository repository;

    public MainController() {

        try {
            repository = new DatabaseRepository();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/")
    public ModelAndView showIndex() {
        return new ModelAndView("index");
    }

    @RequestMapping("/dashboard.html")
    public ModelAndView showDashboard() {
        ModelAndView moavi = new ModelAndView("dashboard");
        try {
            List<Course> courseList = repository.findUpcomingCourses();
            moavi.addObject("courseList", courseList);
        } catch (SQLException e) {
            e.printStackTrace();
            return new ModelAndView("redirect:/database-error");
        }
        return moavi;
    }

    @RequestMapping("/database-error")
    public ModelAndView showDatabaseError() {
        return new ModelAndView("index");
    }

}
