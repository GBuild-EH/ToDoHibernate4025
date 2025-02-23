import org.hibernate.*;
import org.hibernate.boot.*;
import org.hibernate.boot.registry.*;
import org.junit.jupiter.api.*;
import java.util.*;

public class ToDoTest {

    private static SessionFactory sf = null;
    private Session session = null;

    @BeforeAll
    static void setup() {
        try {
            StandardServiceRegistry sr
                    = new StandardServiceRegistryBuilder()
                    .configure("hibernate-test.cfg.xml")
                    .build();

            Metadata md = new MetadataSources(sr)
                    .addAnnotatedClass(ToDoTask.class)
                    .getMetadataBuilder()
                    .build();

            sf = md.getSessionFactoryBuilder().build();

        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    @BeforeEach
    void setupThis() {
        session = sf.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearThis() {
        session.getTransaction().commit();
    }

    @AfterAll
    static void tear() {
        sf.close();
    }

    @Test
    void test() {
        ToDoTask task = new ToDoTask();
        task.setDescription("This is a task");
        task.setDueDate(new GregorianCalendar(2025, Calendar.MARCH, 1));
        task.setStartDate(new GregorianCalendar());
        task.setDone(false);

        Assertions.assertNull(task.getId());

        task.setId(1);
        session.persist(task);

        Assertions.assertNotNull(task.getId());
    }
}
