package controller;

import model.Epic;
import model.SubTask;
import model.Task;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {
        //Получение списка всех задач. Тут вернуть колекцию
        public ArrayList<Task> getTask();

        //Получение списка всех эпиков.
        public ArrayList<Epic> getEpics();

        //Получение списка всех подзадач определённого эпика.
        //public ArrayList<SubTask> getSubtaskByEpic(Epic epic);
        public ArrayList<SubTask> getSubtaskByEpic(int id);

        public Task getTaskById(int id);


        //Добавление новой задачи, эпика и подзадачи.
        //Сам объект должен передаваться в качестве параметра.
        public void add(Task task);

        //Обновление задачи любого типа по идентификатору.
        //Новая версия объекта передаётся в виде параметра.
        public void updateTask(int id, Task task);

        //Удаление ранее добавленных задач — всех.
        public void removeTask();
        //Удаление ранее добавленных задач — по идентификатору.
        public void removeTaskById(int id);

        //Обновление статуса эпика
        public void updateStatusEpic(Epic epic);
        //Подсчет завешения эпика
        public void computationTimeEpic(Epic epic);

        public List<Task> history();

        //возвращает список задач и подзадач в заданном порядке
        public TreeSet<Task> getPrioritizedTasks();
}
