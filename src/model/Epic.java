package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks;

    public Epic(String title, String subtitle) {
        super(title, subtitle);
        //this.subTasks = subTasks;
    }

    public void setSubtasks(ArrayList<SubTask> subTask) {
        this.subTasks = subTask;
    }

    public ArrayList<SubTask> getSubtask() {
        return subTasks;
    }

    @Override
    public String toString() {
        return title + "," + subtitle + "," + getStatus() + "," + getId();
    }

}

