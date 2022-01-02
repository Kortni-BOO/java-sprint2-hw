package task;

import java.util.ArrayList;

public class Epic extends Task {
    public ArrayList<SubTask> subTasks;

    public Epic(String title, String subtitle, ArrayList<SubTask> subTasks) {
        super(title, subtitle);
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", id=" + getId() +
                '}';
    }


}
