package model;

import controller.TypeTask;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    public String title;
    public String subtitle;
    public int id;
    protected Status status;
    public LocalDateTime startTime;
    public long duration;
    public LocalDateTime endTime;
    public TypeTask typeTask;

    public Task(String title, String subtitle, LocalDateTime startTime, long duration) {
        this.title = title;
        this.subtitle = subtitle;
        this.startTime = startTime;
        this.duration = duration;
        //this.typeTask = typeTask;
    }

    public void getEndTime() {
        endTime = startTime.plusMinutes(duration);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) &&
                Objects.equals(subtitle, task.subtitle) &&
                Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        int hash = 17;
        if (title != null) {
            hash = hash + title.hashCode();
        }
        hash = hash * 31;

        if (subtitle != null) {
            hash = hash + subtitle.hashCode();
        }

        if (status != null) {
            hash = hash + status.hashCode();
        }
        return hash;
    }

    @Override
    public String toString() {
        return title + "," + subtitle + "," + getStatus() + "," + getId() + "," + startTime + "," + duration;
    }
}
