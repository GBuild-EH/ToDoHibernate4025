import java.util.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "task")

public class ToDoTask
{
    @Id @Column(name = "taskId") private Integer id;
    @Column(name = "taskDesc") private String description;
    @Column(name = "taskDue") private GregorianCalendar dueDate;
    @Column(name = "taskStart") private GregorianCalendar startDate;
    @Column(name = "taskDone") private boolean done;

    public ToDoTask () {}

    public ToDoTask (int id, String desc, GregorianCalendar due, GregorianCalendar start) {
        this.id = id;
        this.description = desc;
        this.dueDate = due;
        this.startDate = start;
        this.done = false;
    }

    //If no start date provided, assume today as the start
    public ToDoTask (int id, String desc, GregorianCalendar due) {
        this(id, desc, due, new GregorianCalendar());
    }

    //If no due date provided, assume due in a week
    public ToDoTask (int id, String desc){
        GregorianCalendar setDue = new GregorianCalendar();
        setDue.add(Calendar.DAY_OF_MONTH, 7);
        this.id = id;
        this.description = desc;
        this.dueDate = setDue;
        this.startDate = new GregorianCalendar();
        this.done = false;
    }

    //Getter methods and completion toggle
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String desc) { this.description = desc; }
    public GregorianCalendar getDueDate() {
        return dueDate;
    }
    public void setDueDate(GregorianCalendar due) { this.dueDate = due; }
    public GregorianCalendar getStartDate() {
        return startDate;
    }
    public void setStartDate(GregorianCalendar start) { this.startDate = start; }
    public boolean isDone() {
        return done;
    }
    public void setDone(boolean done) { this.done = done; }
    public void toggleDone() {
        done = !done;
    }

    public String toString() {
        return id + ": " + description + " Due: " + (dueDate.get(Calendar.MONTH) + 1) + "/" + dueDate.get(Calendar.DAY_OF_MONTH) + "/" + dueDate.get(Calendar.YEAR) +
                " Started: " + (startDate.get(Calendar.MONTH) + 1) + "/" + startDate.get(Calendar.DAY_OF_MONTH) + "/" + startDate.get(Calendar.YEAR) + " " +
                (done ? "Done" : "Not Done");
    }

}