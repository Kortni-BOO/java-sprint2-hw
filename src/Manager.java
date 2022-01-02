import task.Epic;
import task.SubTask;
import task.Task;
import java.util.ArrayList;
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
        ArrayList<Task> tasks = new ArrayList<>();
        for(Task task : store.values()) {
            if(task.getClass() == Task.class) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    //Получение списка всех эпиков. Тут список эпиков?
    public Collection<Epic> getEpics() {
        ArrayList<Epic> epics = new ArrayList<>();
        for(Task epic : store.values()) {
            if(epic.getClass() == Epic.class) {
                epics.add((Epic)epic);
            }
        }
        return epics;
    }

    //Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> getSubtaskByEpic(Epic epic) {
        return epic.subTasks;
    }

    public Task getTaskById(int id) {
        return store.get(id);
    }

    //Добавление новой задачи, эпика и подзадачи.
    //Сам объект должен передаваться в качестве параметра.
    public void add(Task task) {
        task.setId(getNewId());
        store.put(task.getId(), task);
    }

    //Обновление задачи любого типа по идентификатору.
    //Новая версия объекта передаётся в виде параметра.
    public void updateTask(int id, Task task) {
        task.setId(id);
        store.put(id, task);
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
