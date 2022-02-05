package controller;

import model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    public Node<Task> head; //first
    public Node<Task> tail; //last
    public int size = 0;
    HashMap<Integer, Node<Task>> history = new HashMap<>();

    @Override
    public void add(Task task) {
        if(history.containsKey(task.getId())) {
            remove(history.get(task.getId()));
            linkLast(task);
        } else {
            linkLast(task);
        }
    };

    public void linkLast(Task task) {
        final Node<Task> l = tail;
        final Node<Task> newNode = new Node<>(l, task, null);
        tail = newNode;
        history.put(task.getId(), newNode);
        if (l == null) {
            head = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    public void print() { // O(n)
        if (head == null) {
            return;
        }
        Node<Task> e = head;
        while (e.next != null) {
            System.out.println(e.data);
            e = e.next;
        }
        System.out.println(e.data);

    }

    @Override
    public void remove(Node <Task> task) {
        Node<Task> element = task;
        Node<Task> next = element.next;
        Node<Task> prev = element.prev;
        if(prev == null) {
            head = next;
        } else {
            prev.next = next;
            element.prev = null;
        }
        if(next == null) {
            tail = prev;
        } else {
            next.prev = prev;
            element.next = null;
        }
        element.data = null;
        size--;
        //System.out.println(element.data);
    };

    public List<Task> getHistory() {
        ArrayList<Task> tasks = new ArrayList<>();
        if (head == null) {
            return null;
        }
        Node<Task> oldHead = head;
        while (oldHead.next != null) {
            tasks.add(oldHead.data);
            oldHead = oldHead.next;
        }
        tasks.add(oldHead.data);

        return tasks;
    };
}
