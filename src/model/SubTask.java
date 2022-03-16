package model;

public class SubTask extends Task {
    public int epicId;

    public SubTask(String title, String subtitle) {
        super(title, subtitle);
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
        return title + "," + subtitle + "," + getStatus() + "," + getId() + "," + getEpicId();
    }


}
