import model.Status;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager;
    private Task task1;
    private Task task2;
    private Task task3;
    Node node;

    @BeforeEach
    public void setUp() {
        historyManager = new InMemoryHistoryManager();
        task1 = new Task(1, "Task1", "Task1 description", Status.NEW);
        task2 = new Task(2, "Task2", "Task2 description", Status.NEW);
        task3 = new Task(3, "Task3", "Task3 description", Status.NEW);
    }

    @Test
    void shouldAddTaskToHistory() {
        historyManager.add(task1);
        List<Task> history = historyManager.getHistory();
        assertEquals(task1, history.getFirst());
        assertEquals(1, history.size());
    }

    @Test
    void shouldAddNullTaskToHistory() {
        historyManager.add(null);
        assertTrue(historyManager.getHistory().isEmpty());
    }

    @Test
    void shouldRemoveTaskFromHistory() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(task1.getId());
        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task2, history.getFirst());
    }

    @Test
    void getHistoryShouldReturnTask() {
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task1, history.getFirst());
        assertEquals(task2, history.get(1));
        assertEquals(task3, history.get(2));

    }

    @Test
    void shouldAddFirstTaskToEmptyHistory() {
        historyManager.linkLast(task1);

        List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
        assertEquals(task1, history.getFirst());
        assertTrue(history.contains(task1));
    }

    @Test
    void shouldAddTaskToEndOfNonEmptyHistory() {
        historyManager.linkLast(task1);
        historyManager.linkLast(task2);
        historyManager.linkLast(task3);

        List<Task> history = historyManager.getHistory();
        assertEquals(3, history.size());
        assertEquals(task1, history.get(0));
        assertEquals(task2, history.get(1));
        assertEquals(task3, history.get(2));
    }

    @Test
    void shouldIncrementSize() {
        assertEquals(0, historyManager.getHistory().size());

        historyManager.linkLast(task1);
        assertEquals(1, historyManager.getHistory().size());

        historyManager.linkLast(task2);
        assertEquals(2, historyManager.getHistory().size());
    }

    @Test
    void shouldReturnEmptyListForEmptyHistory() {
        List<Task> tasks = historyManager.getTasks();

        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldReturnAllTasksInCorrectOrder() {
        historyManager.linkLast(task1);
        historyManager.linkLast(task2);
        historyManager.linkLast(task3);

        List<Task> tasks = historyManager.getTasks();

        assertEquals(3, tasks.size());
        assertEquals(task1, tasks.get(0));
        assertEquals(task2, tasks.get(1));
        assertEquals(task3, tasks.get(2));
    }

    @Test
    void shouldReturnEmptyListAfterClearingHistory() {
        historyManager.linkLast(task1);
        historyManager.linkLast(task2);

        historyManager.remove(task1.getId());
        historyManager.remove(task2.getId());

        List<Task> tasks = historyManager.getTasks();

        assertTrue(tasks.isEmpty());
    }

    @Test
    void shouldRemoveSingleNodeFromList() {
        historyManager.linkLast(task1);
        historyManager.remove(task1.getId());

        assertNull(historyManager.getHead());
        assertNull(historyManager.getTail());
        assertEquals(0, historyManager.getHistory().size());
        assertFalse(historyManager.getHistory().contains(task1.getId()));
    }

    @Test
    void shouldRemoveFirstNodeFromList() {
        historyManager.linkLast(task1);
        historyManager.linkLast(task2);
        historyManager.linkLast(task3);

        historyManager.remove(task1.getId());

        assertEquals(task2, historyManager.getHead().task);
        assertNull(historyManager.getHead().prev);
        assertEquals(2, historyManager.getHistory().size());
        assertFalse(historyManager.getHistory().contains(task1));
    }

    @Test
    void shouldRemoveLastNodeFromList() {
        historyManager.linkLast(task1);
        historyManager.linkLast(task2);
        historyManager.linkLast(task3);

        historyManager.remove(task3.getId());

        assertEquals(task2, historyManager.getTail().task);
        assertNull(historyManager.getTail().next);
        assertEquals(2, historyManager.getHistory().size());
        assertFalse(historyManager.getHistory().contains(task3));
    }

    @Test
    void shouldRemoveMiddleNodeFromList() {
        historyManager.linkLast(task1);
        historyManager.linkLast(task2);
        historyManager.linkLast(task3);

        historyManager.remove((task2.getId()));

        assertEquals(2, historyManager.getHistory().size());
        assertEquals(task1, historyManager.getHead().task);
        assertEquals(task3, historyManager.getTail().task);
        assertFalse((historyManager.getHistory().contains(task2)));
        assertEquals(task3, historyManager.getHead().next.task);
        assertEquals(task1, historyManager.getTail().prev.task);
    }

}
