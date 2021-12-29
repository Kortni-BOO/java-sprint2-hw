package task;

import java.util.ArrayList;

public class EpicList {
    public Object task;
    public ArrayList<Object> subTask;

    public EpicList(Object task, ArrayList<Object> subTask) {
        this.task = task;
        this.subTask = subTask;
    }

    @Override
    public String toString() {
        return "Task{" + // имя класса
                "title='" + task + '\'' +
                ", subtitle='" + subTask + '\'' +
                '}';
    }
}
