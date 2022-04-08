package controller;

import API.KVTaskClient;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HTTPTaskManager  extends FileBackedTasksManager {
    URI url;
    KVTaskClient kv;

    public HTTPTaskManager(URI url) throws IOException, InterruptedException {
        this.url = url;
        kv = new KVTaskClient();
    }

    @Override
    public void add(Task task) {
        super.add(task);
        save();
    }


    @Override
    public ArrayList<Task> getTask() {
        ArrayList<Task> tasks = super.getTask();
        save();
        return tasks;
    }


    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epics = super.getEpics();
        save();
        return epics;
    }

    @Override
    public ArrayList<SubTask> getSubtaskByEpic(int id) {
        ArrayList<SubTask> subTask = super.getSubtaskByEpic(id);
        save();
        return subTask;
    }

    @Override
    public void save() {
        try {
            for (Task t : store.values()) {
                String tasks = toString(t);
                if(t.getClass() == Task.class) {
                    kv.put("task", tasks);
                } else if(t.getClass() == Epic.class){
                    kv.put("epic", tasks);
                } else {
                    kv.put("subtask", tasks);
                }
            }
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }

    }

    @Override
    public Task fromString(String value) {
        Task task = new Task("Тест", "test",
                LocalDateTime.of(2022, 12, 3, 8, 55),
                121
        );
        int epicID = 0;
        ArrayList<SubTask> subtasks = new ArrayList<>();
        String line = value.substring(1, value.length() - 1);
        String[] lineContents = line.trim().split(",");
        if (lineContents[1].equals("TASK")){
            Task taskNew = new Task(lineContents[2], lineContents[4],
                    LocalDateTime.parse(lineContents[5], DATE_TIME_FORMATTER),
                    Long.parseLong(lineContents[6])
            );
            taskNew.setId(Integer.parseInt(lineContents[0]));
            checkStatus(lineContents[3], taskNew);
            System.out.println(taskNew);
            task = taskNew;
            store.put(taskNew.getId(), taskNew);
        } else if (lineContents[1].equals("EPIC") ) {
            Epic epic = new Epic(lineContents[2], lineContents[4],
                    LocalDateTime.parse(lineContents[5], DATE_TIME_FORMATTER),
                    Long.parseLong(lineContents[6])
            );
            epic.setId(Integer.parseInt(lineContents[0]));
            checkStatus(lineContents[3], epic);
            epicID = epic.getId();
            task = epic;
            store.put(task.getId(), task);
        }  if (lineContents[1].equals("SUBTASK")) {
            if(Integer.parseInt(lineContents[5]) == epicID) {
                SubTask subtask = new SubTask(lineContents[2], lineContents[4],
                        LocalDateTime.parse(lineContents[6], DATE_TIME_FORMATTER),
                        Long.parseLong(lineContents[7])
                );
                subtask.setEpicId(epicID);
                subtask.setId(Integer.parseInt(lineContents[0]));
                checkStatus(lineContents[3], subtask);
                subtasks.add(subtask);
                task = subtask;
                store.put(task.getId(), task);
            }
        }

        return task;
    }

    public void loadFromServer(String data) {
        String task = kv.load(data);
        fromString(task);
    }

}
