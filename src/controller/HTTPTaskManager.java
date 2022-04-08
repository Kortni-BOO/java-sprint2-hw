package controller;

import API.KVTaskClient;
import com.google.gson.*;
import error.ManagerSaveException;
import model.Epic;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        super.fromString(value);
        return super.fromString(value);
    }

}
