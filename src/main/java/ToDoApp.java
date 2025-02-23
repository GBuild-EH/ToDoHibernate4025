import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.*;

public class ToDoApp {

    private static SessionFactory sf;

    public static void main(String[] args) {

        try {
            sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }

        boolean runKey = true;
        Scanner sc = new Scanner(System.in);
        while (runKey) {
            System.out.println("TO DO LIST APPLICATION");
            System.out.println("1. Add New Task");
            System.out.println("2. Delete Task");
            System.out.println("3. Show List of Tasks");
            System.out.println("4. Exit");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    addTask(sc);
                    break;
                case 2:
                    System.out.println("Enter Task ID");
                    int taskID = sc.nextInt();
                    sc.nextLine();
                    deleteTask(taskID);
                    break;
                case 3:
                    showTasks();
                    break;
                case 4:
                    runKey = false;
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

        public static int genID() {

            Session session = sf.openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Object q = session.createQuery("SELECT MAX(id) FROM ToDoTask").uniqueResult();
                if (q != null) {
                    return ((int) q) + 1;
                } else return 1;
            } catch (HibernateException e) {
                if (tx!=null) tx.rollback();
                e.printStackTrace();
                return -1;
            }
        }

        public static void addTask(Scanner in) {

            Session session = sf.openSession();
            Transaction tx = null;
            ToDoTask task = null;

            System.out.print("Please describe the new task: ");
            String desc = in.nextLine();
            System.out.print("Enter date due (MM/DD/YYYY) or skip to assign for next week: ");
            String due = in.nextLine();
            if (due.contains("-") || due.contains("/")) {
                String[] dueArray = due.split("[-/]+");
                GregorianCalendar dueDate = new GregorianCalendar(Integer.parseInt(dueArray[2]), Integer.parseInt(dueArray[0]) - 1, Integer.parseInt(dueArray[1]));
                System.out.print("Enter date started (MM/DD/YYYY) or skip to set as today: ");
                String start = in.nextLine();
                if (start.contains("-") || start.contains("/")) {
                    String[] startArray = start.split("[-/]+");
                    GregorianCalendar startDate = new GregorianCalendar(Integer.parseInt(startArray[2]), Integer.parseInt(startArray[0]) - 1, Integer.parseInt(startArray[1]));
                    task = new ToDoTask(genID(), desc, dueDate, startDate);
                } else if (start.isEmpty()) {
                    task = new ToDoTask(genID(), desc, dueDate);
                } else {
                    System.out.println("Invalid start date, please try again");
                }
            } else if (due.isEmpty()) {
                task = new ToDoTask(genID(), desc);
            } else
                System.out.println("Invalid due date, please try again");
            System.out.println();

            if (task != null) {
                try {
                    tx = session.beginTransaction();
                    session.save(task);
                    tx.commit();
                } catch (HibernateException e) {
                    if (tx != null) tx.rollback();
                    e.printStackTrace();
                } finally {
                    session.close();
                }
            }
        }

        public static void deleteTask(int id) {
        Session session = sf.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            ToDoTask task = (ToDoTask) session.get(ToDoTask.class, id);
            session.delete(task);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    public static void showTasks() {
        Session session = sf.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List tasks = session.createQuery("From ToDoTask").list();
            for (Object task : tasks) {
                System.out.println(task);
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}