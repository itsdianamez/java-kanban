package model;

public class Subtask extends Task {

    public Subtask() {
        super();
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" + "taskName='" + getTaskName() + ", description='" + getDescription() + ", status=" + getStatus() +
                ", id=" + getId() + "epicId=" + getEpicId() +
                '}';
    }

    int epicId;

    public Subtask(int id, String taskName, String description, Status status, int epicId) {
        super(id, taskName, description, status);
        this.epicId = epicId;
    }


}
