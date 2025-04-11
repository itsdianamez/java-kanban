import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    private Map<Integer, Node> history = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) {
            return;
        }
        remove(task.getId());
        linkLast(task);
    }

    @Override
    public void remove(int id) {
        Node node = history.get(id);
        if (node != null) {
            removeNode(node);
            history.remove(id);
        }
    }


    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task task) {
        Node node = new Node(task);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        history.put(task.getId(), node);
    }


    public List<Task> getTasks() {
        List<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.task);
            current = current.next;
        }

        return tasks;

    }

    public void removeNode(Node node) {
        if (node == null) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
        history.remove(node.task.getId());
    }


}



