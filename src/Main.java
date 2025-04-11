import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        Task task = new Task(manager.generateId(), "Переезд", "Организовать переезд", Status.NEW);
        Task task1 = new Task(manager.generateId(), "Покушать", "Приготовить", Status.NEW);

        Epic epic = new Epic(manager.generateId(), "Сделать ремонт", "Покрасить стены");
        Epic epic1 = new Epic(manager.generateId(), "Учеба", "Сделать дз");

        Subtask subtask = new Subtask(manager.generateId(), "Покрасить", "Покрасить стены", Status.NEW, epic.getId());
        Subtask subtask1 = new Subtask(manager.generateId(), "Конспект", "Написать конспект", Status.NEW, epic1.getId());

        manager.createTask(task);
        manager.createTask(task1);
        manager.createEpic(epic);
        manager.createEpic(epic1);
        manager.createSubtask(subtask);
        manager.createSubtask(subtask1);

        System.out.println("Задача с ID 1: " + manager.getTask(1));
        System.out.println("Эпик с ID 3: " + manager.getEpic(3));
        System.out.println("Подзадача с ID 5: " + manager.getSubtask(5));

        System.out.println("Обычные задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());

        System.out.println("История просмотров: " + manager.getHistory());
    }
}