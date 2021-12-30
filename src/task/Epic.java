package task;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<SubTask> subTasks;

    public Epic(String title, String subtitle, String status, ArrayList<SubTask> subTasks) {
        super(title, subtitle, status);
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "Epic{" + // имя класса
                "title='" + title + '\'' + // поле1=значение1
                ", subtitle='" + subtitle + '\'' + // поле2=значение2
                ", id=" + getId() + // поле3=значение3
                '}';
    }


}
