package model;

import java.time.LocalDateTime;

public class SubTask extends Task {
    public int epicId;

    public SubTask(String title, String subtitle, LocalDateTime startTime, long duration) {
        super(title, subtitle, startTime, duration);
        //this.epicId = epicId;
    }
    //получаем id эпика
    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    @Override
    public String toString() {
        return title + "," + subtitle + "," + getStatus() + "," + getId() + "," + getEpicId() + "," + startTime;
    }


}
