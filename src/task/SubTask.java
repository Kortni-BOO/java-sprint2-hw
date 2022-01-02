package task;

public class SubTask extends Task {
    private int epicId;

    public SubTask(String title, String subtitle, int epicId) {
        super(title, subtitle);
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
                ", subtitle='" + subtitle + '\'' + "status=" + getStatus() +
                ", idEpic=" + getEpicId() + " id=" + getId() +
                '}';
    }


}
