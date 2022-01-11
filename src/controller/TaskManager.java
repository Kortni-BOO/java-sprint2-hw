package controller;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public interface TaskManager {
        //коллекция для хранения задач
        HashMap<Integer, Task> store = new HashMap<>();
        static int newId = 0;

        public int getNewId();

        //Получение списка всех задач. Тут вернуть колекцию
        public Collection<Task> getTask();

        //Получение списка всех эпиков.
        public Collection<Epic> getEpics();

        //Получение списка всех подзадач определённого эпика.
        public ArrayList<SubTask> getSubtaskByEpic(Epic epic);

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
}
