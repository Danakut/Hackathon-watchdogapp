package cz.danakut.coursewatchdog.watchdogapp;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Course {
    int id;
    CourseType type;
    Date startDate;
    Date endDate;
    String startTime;
    String endTime;
    String topic;
    int knowledgeLevel;
    String name;
    RegistrationStatus status;
    String quickLocation;
    Location location;
    List<String> instructors;
    String link;
    String description;
    Timestamp lastUpdate;

    public Course() {

    }

    @Override
    public String toString() {
        String result = id + "|" + type + "|" + startDate + "|" + endDate + "|" + startTime + "|" + endTime + "|" + topic + "|" +
                knowledgeLevel + "|" + name + "|" + status + "|" + quickLocation + "|" + location + "|" + instructors + "|" +
                link + "|" + description + "|" + lastUpdate + "||";

        return result;
    }

    public String toLongString() {
        String result = topic + ", úroveň " + knowledgeLevel + ", název akce: ";

        result += name + "\n";


        result += type + ", ";

        if (type == CourseType.WORKSHOP || type == CourseType.AKCE) {
            result += "datum: " + startDate + ", " + startTime + " - " + endTime + "\n";
        } else {
            result += "od: " + startDate + ", do: " + endDate + "\n";
            result += "čas: " + startTime + " - " + endTime + "\n";
        }

        result += "místo konání: " + quickLocation + ", ";

        result += "lektor: " + instructors + "\n";

        result += "odkaz na web: " + link + "\n";

        result += "stav registrace: " + status + "\n";

        result += "poslední update: " + lastUpdate + "\n \n";

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        Course thatObj = null;

        if (!(this.getClass().isInstance(obj.getClass()))) {
            return false;
        } else {
            thatObj = (Course) obj;
        }

        if ((this.name.equals(thatObj.name))
                && this.startDate.equals(thatObj.startDate)
                && this.startTime.equals(thatObj.startTime)
                && this.quickLocation.equals(thatObj.quickLocation)) {
            return true;
        }

        return false;
    }

    @Override
    public int hashCode() {
        int nameHash = name.hashCode();
        int dateHash = startDate.hashCode();
        int locationHash = quickLocation.hashCode();
        return nameHash * dateHash * locationHash;
    }

    public int getId() {
        return id;
    }

    public CourseType getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getTopic() {
        return topic;
    }

    public int getKnowledgeLevel() {
        return knowledgeLevel;
    }

    public String getName() {
        return name;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public String getQuickLocation() {
        return quickLocation;
    }

    public Location getLocation() {
        return location;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
}
