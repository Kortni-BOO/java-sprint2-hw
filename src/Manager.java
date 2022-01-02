import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Manager {
    //коллекция для хранения задач
    HashMap<Integer, Task> store = new HashMap<>();
    int newId = 0;

    public int getNewId() {
        return ++newId;
    }

    //Получение списка всех задач. Тут вернуть колекцию
    public Collection<Task> getTask() {
        //погоди
        ArrayList<Task> epics = new ArrayList<>();
        for(Task task : store.values()) {
            if(task.getClass() == Task.class) {
                epics.add(task);
            }
        }
        //System.out.println(epics);
        return epics;
    }

    //Получение списка всех эпиков. Тут список эпиков?
    public Collection<Epic> getEpics() {
        ArrayList<Epic> epics = new ArrayList<>();
        for(Task epic : store.values()) {
            if(epic.getClass() == Epic.class) {
                epics.add((Epic)epic);
            }
        }
        //System.out.println(epics);
        return epics;
    }

    //Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> getSubtaskByEpic(Epic epic) {
        //System.out.println(epic.subTasks);
        return epic.subTasks;
    }

    //Получение задачи любого типа по идентификатору.
    /*
    public Task getTaskById(int id) {
        Object ob = new Object();
        for(Integer i : store.keySet()) {
            if(i == id) {
                ob = store.get(i);
            }
        }
        System.out.println(ob);
        return (Task)ob;
    }

     */
    public Task getTaskById(int id) {
        //System.out.println(store.get(id));
        return store.get(id);
    }


    //Добавление новой задачи, эпика и подзадачи.
    //Сам объект должен передаваться в качестве параметра.
    public void add(Task task) {
        task.setId(getNewId());
        store.put(task.getId(), task);
        //System.out.println(store);
    }

    //Обновление задачи любого типа по идентификатору.
    //Новая версия объекта передаётся в виде параметра.
    public void updateTask(int id, Task task) {
        task.setId(id);
        store.put(id, task);
        //System.out.println(store);
    }

    //Удаление ранее добавленных задач — всех.
    public void removeTask() {
        store.clear();
    }
    //Удаление ранее добавленных задач — по идентификатору.
    public void removeTaskById(int id){
        store.remove(id);
    }

    //Обновление статуса эпика
    public void updateStatusEpic(Epic epic) {
        ArrayList<String> sub = new ArrayList<>();
        //epic.status = "";

        for (SubTask status : epic.subTasks) {
            sub.add(status.getStatus());
        }

        for(int i = 0; i < sub.size(); i++) {

            if(sub.isEmpty()) {
                epic.setStatus("NEW");
            } else if(sub.contains("NEW") && sub.contains("DONE")) {
                epic.setStatus("IN_PROGRESS");
            } else {
                epic.setStatus(sub.get(i));
            }

        }
    }

}

/*
            if(status.equals(status) && status.equals("NEW")) {
                epic.setStatus("NEW");
            } else if(status.equals(status) && status.equals("DONE")) {
                epic.setStatus("DONE");
            } else {
                epic.setStatus("IN_PROGRESS");
            }


                    for(int i = 0; i < sub.size(); i++) {
            if(sub.get(i).equals("NEW")) {
                epic.setStatus("NEW");
            } else if(sub.get(i).equals(sub.get(i)) && sub.get(i).equals("DONE")) {
                epic.setStatus("DONE");
            } else {
                epic.setStatus("IN_PROGRESS");
            }
        }


*/
