package controller;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class InMemoryTasksManager implements TaskManager{
    //коллекция для хранения задач
    HashMap<Integer, Task> store = new HashMap<>();
    ArrayList<Task> storage = new ArrayList<>();
    static int newId = 0;
    int limit = 10;

    @Override
    public int getNewId() {
        return ++newId;
    }

    //Получение списка всех задач. Тут вернуть колекцию
    @Override
    public Collection<Task> getTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        for(Task task : store.values()) {
            if(task.getClass() == Task.class) {
                tasks.add(task);
                storage.add(task);
            }
        }
        return tasks;
    }

    //Получение списка всех эпиков.
    @Override
    public Collection<Epic> getEpics() {
        ArrayList<Epic> epics = new ArrayList<>();
        for(Task epic : store.values()) {
            if(epic.getClass() == Epic.class) {
                epics.add((Epic)epic);
                storage.add((Epic)epic);
            }
        }
        return epics;
    }

    //Получение списка всех подзадач определённого эпика.
    @Override
    public ArrayList<SubTask> getSubtaskByEpic(Epic epic) {
        for (Task sub : epic.subTasks){
            storage.add(sub);
        }
        return epic.subTasks;
    }

    public Task getTaskById(int id) {
        return store.get(id);
    }

    //Добавление новой задачи, эпика и подзадачи.
    //Сам объект должен передаваться в качестве параметра.
    @Override
    public void add(Task task) {
        task.setId(getNewId());
        store.put(task.getId(), task);
    }

    //Обновление задачи любого типа по идентификатору.
    //Новая версия объекта передаётся в виде параметра.
    @Override
    public void updateTask(int id, Task task) {
        task.setId(id);
        store.put(id, task);
    }

    //Удаление ранее добавленных задач — всех.
    @Override
    public void removeTask() {
        store.clear();
    }

    //Удаление ранее добавленных задач — по идентификатору.
    @Override
    public void removeTaskById(int id){
        store.remove(id);
    }

    //Обновление статуса эпика
    @Override
    public void updateStatusEpic(Epic epic) {
        ArrayList<Status> sub = new ArrayList<>();

        for (SubTask status : epic.subTasks) {
            sub.add(status.getStatus());
        }
        for(int i = 0; i < sub.size(); i++) {
            if(sub.isEmpty()) {
                epic.setStatus(Status.NEW);
            } else if(sub.contains(Status.NEW) && sub.contains(Status.DONE)) {
                epic.setStatus(Status.IN_PROGRESS);
            } else {
                epic.setStatus(sub.get(i));
            }

        }
        System.out.println(epic.getStatus());
    }

    //Возвращает последние 10 просмотренных задач
    public ArrayList<Task> history() {
        while (storage.size() > limit) {
            storage.remove(0);
        }


        return storage;
    }

}
