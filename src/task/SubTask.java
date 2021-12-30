package task;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String title, String subtitle, String status, int epicId) {
        super(title, subtitle, status);
        this.epicId = epicId;
    }
    //получаем id эпика
    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "SubTask{" + // имя класса
                "title='" + title + '\'' + // поле1=значение1
                ", subtitle='" + subtitle + '\'' + // поле2=значение2
                ", id=" + getId() + // поле3=значение3
                '}';
    }


}
