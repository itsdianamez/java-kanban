package model;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
List<Integer> subtaskId = new ArrayList<>();

    public Epic(int id, String taskName, String description) {
        super(id, taskName, description, Status.NEW);
    }

    public void addSubtask(int subtaskId) {
       this.subtaskId.add(subtaskId);
    }


    public void removeSubtask(int subtaskId) {
        this.subtaskId.remove(subtaskId);
    }

    public List<Integer> getSubtaskIds() {
        return subtaskId;
    }
}
