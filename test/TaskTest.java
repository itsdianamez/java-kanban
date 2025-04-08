import model.Status;
import model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskEquality() {
        Task task1 = new Task(1, "model.Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(1, "model.Task 2", "Description 2", Status.IN_PROGRESS);

        assertEquals(task1, task2, "Задачи с одинаковым id должны быть равны.");
    }

    @Test
    void testTaskHashCode() {
        Task task1 = new Task(1, "model.Task 1", "Description 1", Status.NEW);
        Task task2 = new Task(1, "model.Task 2", "Description 2", Status.IN_PROGRESS);

        assertEquals(task1.hashCode(), task2.hashCode(), "Хэш-коды задач с одинаковым id должны быть равны.");
    }
}