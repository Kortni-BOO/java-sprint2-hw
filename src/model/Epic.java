package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks = new ArrayList<>();


    public Epic(String title, String subtitle, LocalDateTime startTime, long duration) {
        super(title, subtitle, startTime, duration);
        //endTime = getEndTime();
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

