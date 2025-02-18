import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {
    int counter = 0;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();

    public int generateId(){
        return ++counter;
    }

    public void addTask(Task task) {
        tasks.put(task.getId(), task);
    }

    public void addEpic(Epic epic) {
        epics.put(epic.getId(), epic);
    }
    public Subtask getSubtaskById(int id){
        return subtasks.get(id);
    }
    public Task getTaskById(int id){
        return tasks.get(id);
    }
    public Epic getEpicById(int id){
        return epics.get(id);
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.epicId).addSubtask(subtask.getId());
        updateEpicStatus(subtask.epicId);

    }

    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if(epic == null || epic.getSubtaskIds().isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        boolean allDone = true;
        boolean anyInProgress = false;

        for(int id: epic.getSubtaskIds()) {
            Status subtaskStatus = subtasks.get(id).getStatus();
            if(subtaskStatus != Status.DONE) {
                allDone = false;
            }
            if(subtaskStatus == Status.IN_PROGRESS) {
                anyInProgress = true;
            }
        }
        if(allDone) {
            epic.setStatus(Status.DONE);
        } else if (anyInProgress) {
            epic.setStatus(Status.IN_PROGRESS);
        }
        else {
            epic.setStatus(Status.NEW);
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    public ArrayList<Epic> getAllEpics(){
        return new ArrayList<>(epics.values());
    }
    public ArrayList<Subtask>  getAllSubtasks(){
        return new ArrayList<>(subtasks.values());
    }
    public void deleteTaskById(int id){
        tasks.remove(id);
    }
    public void deleteSubtaskById(int id){
        if(subtasks.containsKey(id)) {
            int epicId = subtasks.get(id).epicId;
            epics.get(epicId).removeSubtask(id);
            subtasks.remove(id);
            updateEpicStatus(epicId);
        }
    }
    public void deleteEpicId(int id) {
        if(epics.containsKey(id)) {
            for(int subtaskId:  epics.get(id).getSubtaskIds()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }
    public void updateSubtask(Subtask subtask){
        subtasks.put(subtask.getId(), subtask);
        updateEpicStatus(subtask.epicId);
    }
    public void updateTask(Task task){
        tasks.put(task.getId(), task);
    }


}
