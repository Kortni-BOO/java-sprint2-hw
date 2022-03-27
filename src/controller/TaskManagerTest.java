package controller;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest <T extends TaskManager> {
    protected static HashMap<Integer, Task> store = new HashMap<>();
    protected static InMemoryHistoryManager newList = new InMemoryHistoryManager();
    T element;

    @Test
    public void shouldReturnAllTask() {
        element.getTask();
    }

    //Получение списка всех задач. Тут вернуть колекцию
    public ArrayList<Task> getTask() {
        ArrayList<Task> tasks = new ArrayList<>();
        for(Task task : store.values()) {
            if(task.getClass() == Task.class) {
                tasks.add(task);
                newList.add(task);
            }
        }
        return tasks;
    }

    //Получение списка всех эпиков.
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epics = new ArrayList<>();
        for(Task epic : store.values()) {
            if(epic.getClass() == Epic.class) {
                epics.add((Epic)epic);
                //storage.add((Epic)epic);
                newList.add(epic);
            }
        }
        return epics;
    }

    //Получение списка всех подзадач определённого эпика.
    public ArrayList<SubTask> getSubtaskByEpic(int id) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        if(!store.containsKey(id)) {
            //System.out.println("l");
            return null;
        } else {
            Epic epic = (Epic) store.get(id);
            newList.add(epic);
            subTasks.addAll(epic.getSubtask());
            //System.out.println(subTasks);
        }
        //return epic.subTasks;
        return subTasks;
    }

    public Task getTaskById(int id) {
        return store.get(id);
    }

    public abstract void add(Task task);

    //Обновление задачи любого типа по идентификатору.
    //Новая версия объекта передаётся в виде параметра.
    public void updateTask(int id, Task task) {
        task.setId(id);
        if(checkTime(task)) {
            store.put(id, task);
        }
        //store.put(id, task);
    }

    //Удаление ранее добавленных задач — всех.
    public void removeTask() {
        store.clear();
    }

    //Удаление ранее добавленных задач — по идентификатору.
    public void removeTaskById(int id){
        if(store.get(id).getClass() == Epic.class) {
            Epic epic = (Epic) store.get(id);
            epic.getSubtask().clear();
        }
        store.remove(id);
        newList.remove(id);
    }

    //Обновление статуса эпика
    public void updateStatusEpic(Epic epic) {
        ArrayList<Status> sub = new ArrayList<>();
        for (SubTask status : epic.getSubtask()) {
            sub.add(status.getStatus());
        }
        if(sub.isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        for(int i = 0; i < sub.size(); i++) {
            if(sub.contains(Status.NEW) && sub.contains(Status.DONE)) {
                epic.setStatus(Status.IN_PROGRESS);
            } else {
                epic.setStatus(sub.get(i));
            }
        }
    }

    public void computationTimeEpic(Epic epic) {
        ArrayList<SubTask> sub = epic.getSubtask();
        epic.startTime = epic.getSubtask().get(0).startTime;
        if(sub.size() > 1) {
            for(int i = 0; i < sub.size(); i++) {
                for(int j = 0; j < sub.size(); j++) {
                    if(sub.get(i).startTime.isBefore(sub.get(j).startTime)) {
                        epic.startTime = sub.get(i).startTime;
                    }
                }
            }
        }
        System.out.println("Method computationTimeEpic epic.startTime = " + epic.startTime);
        long time = 0;
        for (SubTask duration : epic.getSubtask()) {
            time += duration.duration;
            epic.endTime = epic.startTime.plusMinutes(time);
        }
        System.out.println(time);
    }

    //Возвращает последние просмотренных задач
    public List<Task> history() {
        List<Task> storage = newList.getHistory();
        return storage;
    }

    public TreeSet<Task> getPrioritizedTasks() {
        //Set<Ticket> tickets = new TreeSet<>(comparator);
        Comparator<Task> comparator = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                if(o1.startTime.isAfter(o2.startTime)) {
                    return 1;
                } else if (o1.startTime.isBefore(o2.startTime)) {
                    return -1;
                } else {
                    return 0;
                }
            }
        };
        TreeSet<Task> tasks = new TreeSet<>(comparator);
        ArrayList<Task> tasksSort = new ArrayList<>();
        for(Task taskClass : store.values()) {
            if(taskClass.getClass() == Task.class || taskClass.getClass() == SubTask.class) {
                tasksSort.add(taskClass);
            }
        }
        tasks.addAll(tasksSort);
        System.out.println("Method getPrioritizedTasks : " + tasks.toString());
        return tasks;
    }

    // Проверка на задачу с уже заданныи временем
    public boolean checkTime(Task task) {
        boolean isValid = true;
        for(Task taskNow : store.values()) {
            if(task.startTime.equals(taskNow.startTime)) {
                System.out.println("Данное время " + task.startTime + " занято задачей " + taskNow.title +
                        ". Новая задача  " + task.title + " на время " + task.startTime + " не добавленна."
                );
                isValid = false;
            } else {
                isValid = true;
            }
        }
        return isValid;
    }


}