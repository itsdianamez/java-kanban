import model.Task;

public class Node <T> {
    public Node<T> next;
    public Node<T> prev;
    Task task;

    public Node(Task task) {
        this.task = task;
        next = null;
        prev = null;
    }

    public Node(Node<T> next, Node<T> prev, Task task) {
        this.next = next;
        this.prev = prev;
        this.task = task;
    }


}
