package controller;

import model.Task;
import java.util.List;

public interface HistoryManager{

    void add(Task task);

    //void remove(Node <Task> node);
    void remove(int id);

    List<Task> getHistory();
}
