public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Task task = new Task(manager.generateId(), "Переезд", "Организовать переезд", Status.NEW);
        Task task1 = new Task(manager.generateId(), "Покушать", "Приготовить", Status.NEW);
        Epic epic =  new Epic(manager.generateId(), "Сделать ремонт", "Покрасить стены");
        Epic epic1 =  new Epic(manager.generateId(), "Учеба", "Сделать дз");
        Subtask subtask = new Subtask(manager.generateId(), "покрасить", "покрасить стены", Status.NEW, epic.getId());
        Subtask subtask1 = new Subtask(manager.generateId(), "конспект", "написать конспект", Status.NEW, epic1.getId());
        manager.addTask(task);
        manager.addTask(task1);
        manager.addEpic(epic);
        manager.addEpic(epic1);
        manager.addSubtask(subtask);
        manager.addSubtask(subtask1);
        System.out.println(manager.getEpicById(3));
        System.out.println(manager.getTaskById(1));
        System.out.println(manager.getSubtaskById(5));
        System.out.println("Обычные таски: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Сабтаски" + manager.getAllSubtasks());

    }
}