import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        File file = File.createTempFile("tasks", ".csv");
        TaskManager taskManager = Managers.getFileBackedTaskManager(file);
        testTaskManager(taskManager);
        FileBackedTaskManager loadedManager = FileBackedTaskManager.loadFromFile(file);
        System.out.println(loadedManager.getAllTasks());
        System.out.println(loadedManager.getAllEpics());
        System.out.println(loadedManager.getAllSubtasks());

    }

    public static void testTaskManager(TaskManager taskManager) {
        Task task = new Task(taskManager.generateId(), "Переезд", "Организовать  переезд", Status.NEW);
        Task task1 = new Task(taskManager.generateId(), "Покушать", "Приготовить", Status.NEW);

        Epic epic = new Epic(taskManager.generateId(), "Сделать ремонт", "Покрасить стены");
        Epic epic1 = new Epic(taskManager.generateId(), "Учеба", "Сделать дз");

        Subtask subtask = new Subtask(taskManager.generateId(), "Покрасить", "Покрасить стены", Status.NEW, epic.getId());
        Subtask subtask1 = new Subtask(taskManager.generateId(), "Конспект", "Написать конспект", Status.NEW, epic1.getId());

        taskManager.createTask(task);
        taskManager.createTask(task1);
        taskManager.createEpic(epic);
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask);
        taskManager.createSubtask(subtask1);

        System.out.println("Задача с ID 1: " + taskManager.getTask(1));
        System.out.println("Эпик с ID 3: " + taskManager.getEpic(3));
        System.out.println("Подзадача с ID 5: " + taskManager.getSubtask(5));

        System.out.println("Обычные задачи: " + taskManager.getAllTasks());
        System.out.println("Эпики: " + taskManager.getAllEpics());
        System.out.println("Подзадачи: " + taskManager.getAllSubtasks());

        System.out.println("История просмотров: " + taskManager.getHistory());

    }
}