import model.Epic;
import model.Status;
import model.Subtask;
import model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;

    public FileBackedTaskManager(HistoryManager historyManager, File file) {
        super(historyManager);
        this.file = file;
    }

    private String toString(Task task) {
        if (task instanceof Subtask subtask) {
            return String.format("%d,%s,%s,%s,%s,%d",
                    subtask.getId(),
                    Type.SUBTASK,
                    subtask.getTaskName(),
                    subtask.getStatus(),
                    subtask.getDescription(),
                    subtask.getEpicId());
        } else if (task instanceof Epic epic) {
            return String.format("%d,%s,%s,%s,%s",
                    epic.getId(),
                    Type.EPIC,
                    epic.getTaskName(),
                    epic.getStatus(),
                    epic.getDescription());

        } else {
            return String.format("%d,%s,%s,%s,%s",
                    task.getId(),
                    Type.TASK,
                    task.getTaskName(),
                    task.getStatus(),
                    task.getDescription());
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic");
            writer.newLine();
            for (Task task : getAllTasks()) {
                writer.write(toString(task));
                writer.newLine();
            }
            for (Epic epic : getAllEpics()) {
                writer.write(toString(epic));
                writer.newLine();
            }
            for (Subtask subtask : getAllSubtasks()) {
                writer.write(toString(subtask));
                writer.newLine();
            }
            writer.newLine();
            List<Task> taskList = getHistory();
            if (!taskList.isEmpty()) {
                writer.write(historyToString(taskList));
            }

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при загрузке файла", e);
        }
    }

    private String historyToString(List<Task> taskList) {
        StringBuilder builder = new StringBuilder();
        for (Task task : taskList) {
            builder.append(task.getId()).append(",");
        }
        if (!builder.isEmpty()) {
            builder.deleteCharAt(builder.length() - 1);
        }
        return builder.toString();
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    private static Task fromString(String value) {
        String[] parts = value.split(",");
        int id = Integer.parseInt(parts[0]);
        Type type = Type.valueOf(parts[1]);
        String name = parts[2];
        Status status = Status.valueOf(parts[3]);
        String description = parts[4];
        switch (type) {
            case Type.TASK:
                Task task = new Task(id, name, description, status);
                return task;

            case Type.EPIC:
                Epic epic = new Epic(id, name, description);
                return epic;

            case Type.SUBTASK:
                int epicId = Integer.parseInt(parts[5]);
                Subtask subtask = new Subtask(id, name, description, status, epicId);
                return subtask;

            default:
                throw new IllegalArgumentException("Неизвестный тип задачи");
        }

    }

    private Task findTask(int id) {
        Task task = getTask(id);
        if (task != null) {
            return task;
        }
        task = getSubtask(id);
        if (task != null) {
            return task;
        }
        return getEpic(id);
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(Managers.getDefaultHistory(), file);

        if (!file.exists() || file.length() == 0) {
            return manager;
        }

        try {
            String content = Files.readString(file.toPath());
            if (content.isEmpty()) {
                return manager;
            }

            String[] lines = content.split("\\n");
            if (lines.length <= 1) {
                return manager;
            }

            boolean readingHistory = false;
            for (int i = 1; i < lines.length; i++) {
                if (lines[i].isEmpty()) {
                    readingHistory = true;
                    continue;
                }

                if (readingHistory) {

                    String[] ids = lines[i].split(",");
                    for (String idStr : ids) {
                        int id = Integer.parseInt(idStr);
                        Task task = manager.findTask(id);
                        if (task != null) {
                            manager.getHistoryManager().add(task);
                        }
                    }
                } else {

                    Task task = fromString(lines[i]);
                    if (task instanceof Subtask subtask) {
                        manager.createSubtask(subtask);
                    } else if (task instanceof Epic epic) {
                        manager.createEpic(epic);
                    } else {
                        manager.createTask(task);
                    }
                }
            }

            return manager;
        } catch (IOException e) {
            throw new ManagerSaveException("Error loading from file", e);
        }
    }
}