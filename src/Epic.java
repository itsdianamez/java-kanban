import java.util.ArrayList;

public class Epic extends Task {
ArrayList<Integer> subtaskId = new ArrayList<>();

    public Epic(int id, String taskName, String description) {
        super(id, taskName, description, Status.NEW);

    }

    public void addSubtask(int subtaskId){
       this.subtaskId.add(subtaskId);
    }


    public void removeSubtask(int subtaskId){
        this.subtaskId.remove(subtaskId);
    }

    public ArrayList<Integer> getSubtaskIds(){
        return subtaskId;
    }
}
