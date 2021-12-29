package task;

import java.util.Objects;

public class Task {
    public String title;
    public String subtitle;
    public int id;
    public String status;

    public Task(String title, String subtitle, int id, String status) {
        this.title = title;
        this.subtitle = subtitle;
        this.id = id;
        this.status = status;
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
        return "Task{" + // имя класса
                "title='" + title + '\'' + // поле1=значение1
                ", subtitle='" + subtitle + '\'' + // поле2=значение2
                ", id=" + id + // поле3=значение3
                '}';
    }


}
