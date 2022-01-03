package controller;

import controller.Manager;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Task task = new Task("Купить телефон", "Для крутых фото");
        Task task2 = new Task("Купить телефон", "Для крутых фото");
        Task task3 = new Task("Купить цветы", "За все тачки и квартиру");


        HashMap<Integer, Task> store = new HashMap<>();
        //store.put(task.id, task);
        ArrayList<SubTask> subtasks = new ArrayList<>();
        Epic epic = new Epic("TODO", "Go to the floor", subtasks);
        Manager manager = new Manager();
        manager.add(epic);
        SubTask subtask = new SubTask("Отдать долги>", "За все тачки и квартиру", epic.getId());
        subtask.setStatus(Status.NEW);
        SubTask subtask1 = new SubTask("Похудеть", "до 55",epic.getId());
        subtask1.setStatus(Status.NEW);
        SubTask subtask2 = new SubTask("Купить платье>", "с разрезом",epic.getId());
        subtask2.setStatus(Status.NEW);

        manager.removeTask();

        subtasks.add(subtask);

        subtasks.add(subtask1);
        subtasks.add(subtask2);

        manager.add(subtask);
        manager.add(subtask1);
        manager.add(subtask2);
        manager.add(task);
        Task taskNew = new Task("Job java-developer", "Great life");

        manager.add(task2);
        manager.add(task3);


        manager.getEpics();
        manager.getTask();
        manager.getSubtaskByEpic(epic);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);

        manager.updateTask(1,taskNew);

        SubTask subtaskNew = new SubTask("Отдать долги>", "За все тачки и квартиру", 2);
        manager.updateTask(3,subtaskNew);

        Epic epicNew = new Epic("LOOp", "Go to the floor", subtasks);
        manager.updateTask(4,epicNew);

        manager.removeTaskById(1);
        manager.updateStatusEpic(epic);

    }
}
