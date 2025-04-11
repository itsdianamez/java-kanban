import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = Managers.getDefault();
    }

    @Test
    void testCreateAndGetTask() {
        Task task = new Task(1, "model.Task 1", "Description 1", Status.DONE);
        taskManager.createTask(task);
        Task retrievedTask = taskManager.getTask(1);
        assertEquals(task, retrievedTask, "Задача должна быть получена по ID.");
    }

    @Test
    void testUpdateTask() {
        Task task = new Task(1, "model.Task 1", "Description 1", Status.IN_PROGRESS);
        taskManager.createTask(task);
        Task updatedTask = new Task(1, "Updated model.Task 1", "Updated Description 1", Status.NEW);
        taskManager.updateTask(updatedTask);
        assertEquals(updatedTask, taskManager.getTask(1), "Задача должна быть обновлена.");
    }

    @Test
    void testDeleteTask() {
        Task task = new Task(1, "model.Task 1", "Description 1", Status.IN_PROGRESS);
        taskManager.createTask(task);
        taskManager.deleteTask(1);
        assertNull(taskManager.getTask(1), "Задача должна быть удалена.");
    }

    @Test
    void testGetAllTasks() {
        Task task1 = new Task(1, "model.Task 1", "Description 1", Status.DONE);
        Task task2 = new Task(2, "model.Task 2", "Description 2", Status.DONE);
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        List<Task> tasks = taskManager.getAllTasks();
        assertEquals(2, tasks.size(), "Должны быть возвращены все задачи.");
        assertTrue(tasks.contains(task1) && tasks.contains(task2), "Список задач должен содержать созданные задачи.");
    }


    @Test
    void testCreateAndGetSubtask() {
        Subtask subtask = new Subtask(1, "model.Subtask 1", "Description 1", Status.IN_PROGRESS, 10);
        taskManager.createSubtask(subtask);
        Subtask retrievedSubtask = taskManager.getSubtask(1);
        assertEquals(subtask, retrievedSubtask, "Подзадача должна быть получена по ID.");
    }

    @Test
    void testUpdateSubtask() {
        Subtask subtask = new Subtask(1, "model.Subtask 1", "Description 1", Status.DONE, 10);
        taskManager.createSubtask(subtask);
        Subtask updatedSubtask = new Subtask(1, "Updated model.Subtask 1", "Updated Description 1", Status.IN_PROGRESS, 10);
        taskManager.updateSubtask(updatedSubtask);
        assertEquals(updatedSubtask, taskManager.getSubtask(1), "Подзадача должна быть обновлена.");
    }

    @Test
    void testDeleteSubtask() {
        Subtask subtask = new Subtask(1, "model.Subtask 1", "Description 1", Status.DONE, 10);
        taskManager.createSubtask(subtask);
        taskManager.deleteSubtask(1);
        assertNull(taskManager.getSubtask(1), "Подзадача должна быть удалена.");
    }

    @Test
    void testGetAllSubtasks() {
        Subtask subtask1 = new Subtask(1, "model.Subtask 1", "Description 1", Status.IN_PROGRESS, 10);
        Subtask subtask2 = new Subtask(2, "model.Subtask 2", "Description 2", Status.IN_PROGRESS, 10);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        List<Subtask> subtasks = taskManager.getAllSubtasks();
        assertEquals(2, subtasks.size(), "Должны быть возвращены все подзадачи.");
        assertTrue(subtasks.contains(subtask1) && subtasks.contains(subtask2), "Список подзадач должен содержать созданные подзадачи.");
    }

    @Test
    void testCreateAndGetEpic() {
        Epic epic = new Epic(1, "model.Epic 1", "Description 1");
        taskManager.createEpic(epic);
        Epic retrievedEpic = taskManager.getEpic(1);
        assertEquals(epic, retrievedEpic, "Эпик должен быть получен по ID.");
    }

    @Test
    void testUpdateEpic() {
        Epic epic = new Epic(1, "model.Epic 1", "Description 1");
        taskManager.createEpic(epic);
        Epic updatedEpic = new Epic(1, "Updated model.Epic 1", "Updated Description 1");
        taskManager.updateEpic(updatedEpic);
        assertEquals(updatedEpic, taskManager.getEpic(1), "Эпик должен быть обновлен.");
    }

    @Test
    void testDeleteEpic() {
        Epic epic = new Epic(1, "model.Epic 1", "Description 1");
        taskManager.createEpic(epic);
        taskManager.deleteEpic(1);
        assertNull(taskManager.getEpic(1), "Эпик должен быть удален.");
    }

    @Test
    void testGetAllEpics() {
        Epic epic1 = new Epic(1, "model.Epic 1", "Description 1");
        Epic epic2 = new Epic(2, "model.Epic 2", "Description 2");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        List<Epic> epics = taskManager.getAllEpics();
        assertEquals(2, epics.size(), "Должны быть возвращены все эпики.");
        assertTrue(epics.contains(epic1) && epics.contains(epic2), "Список эпиков должен содержать созданные эпики.");
    }

    @Test
    void testHistory() {
        Task task1 = new Task(1, "model.Task 1", "Description 1", Status.DONE);
        Task task2 = new Task(2, "model.Task 2", "Description 2", Status.DONE);
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        taskManager.getTask(1);
        taskManager.getTask(2);

        List<Task> history = taskManager.getHistory();
        assertEquals(2, history.size(), "История должна содержать 2 задачи.");
        assertEquals(task1, history.get(0), "Первая задача в истории должна быть model.Task 1.");
        assertEquals(task2, history.get(1), "Вторая задача в истории должна быть model.Task 2.");
    }


    @Test
    void testHistoryWithDifferentTaskTypes() {
        Task task = new Task(1, "model.Task 1", "Description 1", Status.DONE);
        Subtask subtask = new Subtask(2, "model.Subtask 1", "Description 1", Status.DONE, 10);
        Epic epic = new Epic(3, "model.Epic 1", "Description 1");

        taskManager.createTask(task);
        taskManager.createSubtask(subtask);
        taskManager.createEpic(epic);

        taskManager.getTask(1);
        taskManager.getSubtask(2);
        taskManager.getEpic(3);

        List<Task> history = taskManager.getHistory();
        assertEquals(3, history.size(), "История должна содержать 3 задачи.");
        assertEquals(task, history.get(0), "Первая задача в истории должна быть model.Task.");
        assertEquals(subtask, history.get(1), "Вторая задача в истории должна быть model.Subtask.");
        assertEquals(epic, history.get(2), "Третья задача в истории должна быть model.Epic.");
    }

    @Test
    void deleteSubtaskShouldRemoveFromEpic() {
        Epic epic = new Epic(1, "Epic 1", "Epic Description");
        taskManager.createEpic(epic);
        Subtask subtask = new Subtask(1, "model.Subtask 1", "Description 1", Status.DONE, epic.getId());
        taskManager.createSubtask(subtask);
        taskManager.getEpic(epic.getId()).addSubtask(subtask.getId());
        assertEquals(1, taskManager.getEpic(epic.getId()).getSubtaskIds().size(), "В Эпике должна быть одна подзадача");
        taskManager.deleteSubtask(subtask.getId());
        assertTrue(taskManager.getEpic(epic.getId()).getSubtaskIds().isEmpty());
    }

    @Test
    void shouldRemoveSubtasksWhenEpicIsDeleted() {
        int epicId = taskManager.generateId();
        Epic epic = new Epic(epicId, "Epic 1", "Epic Description");
        epic.setId(epicId);
        taskManager.createEpic(epic);

        int subtaskId = taskManager.generateId();
        Subtask subtask = new Subtask(subtaskId, "Subtask 1", "Description 1", Status.NEW, epicId);
        subtask.setId(subtaskId);
        taskManager.createSubtask(subtask);
        epic.getSubtaskIds().add(subtaskId);

        int subtaskId1 = taskManager.generateId();
        Subtask subtask1 = new Subtask(subtaskId1, "Subtask 2", "Description 2", Status.NEW, epicId);
        subtask.setId(subtaskId1);
        taskManager.createSubtask(subtask1);
        epic.getSubtaskIds().add(subtaskId1);

        assertNotNull(taskManager.getEpic(epicId));
        assertNotNull(taskManager.getSubtask(subtaskId));
        assertNotNull(taskManager.getSubtask(subtaskId1));

        taskManager.deleteEpic(epicId);
        assertNull(taskManager.getSubtask(subtaskId));
        assertNull(taskManager.getSubtask(subtaskId1));
        assertNull(taskManager.getEpic(epicId));
    }

    @Test
    void taskFieldsModification() {
        int taskId = taskManager.generateId();
        Task task = new Task(taskId, "Task 1", "Description 1", Status.NEW);
        taskManager.createTask(task);
        task.setStatus(Status.IN_PROGRESS);
        task.setTaskName("Task 2");
        assertEquals(task.getTaskName(), "Task 2");
        assertEquals(task.getStatus(), Status.IN_PROGRESS);

    }

    @Test
    void epicFieldsModification() {
        int epicId = taskManager.generateId();
        Epic epic = new Epic(epicId, "Epic 1", "Description 1");
        taskManager.createTask(epic);
        epic.setStatus(Status.IN_PROGRESS);
        epic.setTaskName("Task 2");
        assertEquals(epic.getTaskName(), "Task 2");
        assertEquals(epic.getStatus(), Status.IN_PROGRESS);

    }

    @Test
    void subtaskFieldsModification() {
        int subtaskId = taskManager.generateId();
        Subtask subtask = new Subtask(subtaskId, "Subtask 1", "Description 1", Status.NEW, 1);
        taskManager.createTask(subtask);
        subtask.setStatus(Status.IN_PROGRESS);
        subtask.setTaskName("Task 2");
        assertEquals(subtask.getTaskName(), "Task 2");
        assertEquals(subtask.getStatus(), Status.IN_PROGRESS);

    }

}