package model;

public class Subtask extends Task {

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "epicId=" + epicId +
                '}';
    }

    int epicId;

    public Subtask(int id, String taskName, String description, Status status, int epicId) {
        super(id, taskName, description, status);
        this.epicId = epicId;
    }


}
