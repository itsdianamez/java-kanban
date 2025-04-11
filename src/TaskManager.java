import model.Epic;
import model.Subtask;
import model.Task;

import java.util.List;

public interface TaskManager {
    Task getTask(int id);

    Subtask getSubtask(int id);

    Epic getEpic(int id);

    List<Task> getAllTasks();

    List<Subtask> getAllSubtasks();

    List<Epic> getAllEpics();

    void createTask(Task task);

    void createSubtask(Subtask subtask);

    void createEpic(Epic epic);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    void deleteTask(int id);

    void deleteSubtask(int id);

    void deleteEpic(int id);

    List<Task> getHistory();

    int generateId();

    void updateEpicStatus(int epicId);
}