public class Subtask extends Task {

    int epicId;

    public Subtask(int id, String taskName, String description, Status status, int epicId) {
        super(id, taskName, description, status);
        this.epicId = epicId;
    }


}
