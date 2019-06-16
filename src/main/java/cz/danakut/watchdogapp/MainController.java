package cz.danakut.watchdogapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import cz.danakut.fill_a_db.*;

@Controller
public class MainController {

    private DatabaseRepository repository = null;
    private List<Exception> exceptions = new ArrayList<>();

    public MainController() {

        try {
            repository = new DatabaseRepository();
        } catch (SQLException e) {
            exceptions.add(e);
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
        if (repository == null) {
            return new ModelAndView("redirect:/database-error.html");
        }
        try {
            List<Course> courseList = repository.findUpcomingCourses();
            moavi.addObject("courseList", courseList);
        } catch (SQLException e) {
            exceptions.add(e);
            e.printStackTrace();
            return new ModelAndView("redirect:/database-error.html");
        }
        return moavi;
    }

    @RequestMapping("/database-error.html")
    public ModelAndView showDatabaseError() {
        ModelAndView moavi = new ModelAndView("database-error");
        List<String> messages = new ArrayList<>();
        for (Exception anException : exceptions) {
            messages.add(anException.getClass().getName());
        }
        moavi.addObject("errors", messages);
        return moavi;
    }

}
