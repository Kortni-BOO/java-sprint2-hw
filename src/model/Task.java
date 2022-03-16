package model;

import java.util.Objects;

public class Task {
    public String title;
    public String subtitle;
    public int id;
    protected Status status;

    public Task(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
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


    //Даем id
    public int getNewId() {
        int newId = 0;
        newId =+ id;
        return newId;
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
        return title + "," + subtitle + "," + getStatus() + "," + getId();
    }
}
