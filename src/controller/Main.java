package controller;

import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Task task = new Task("Купить помидорки", "На салат");

        ArrayList<SubTask> subtasks = new ArrayList<>();
        Epic epic = new Epic("Отправка задания", "ТЗ 4", subtasks);
        //InMemoryTasksManager manager = new InMemoryTasksManager();
        InMemoryTasksManager manager = Managers.getDefault();

        manager.add(epic);
        SubTask subtask = new SubTask("Посмотреть", "все замечания", epic.getId());
        subtask.setStatus(Status.NEW);
        SubTask subtask1 = new SubTask("Исправить", "все замечания",epic.getId());
        subtask1.setStatus(Status.NEW);
        SubTask subtask2 = new SubTask("Запушить", "получить замечания или похвалу",epic.getId());
        subtask2.setStatus(Status.NEW);
        subtasks.add(subtask);
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        manager.add(subtask);
        manager.add(subtask1);
        manager.add(subtask2);


        ArrayList<SubTask> newSubTask = new ArrayList<>();
        Epic epicNew = new Epic("Послушать новую песню RHCP", "Black Summer", newSubTask);
        manager.add(epicNew);

        manager.getEpics();
        manager.history();
        manager.getSubtaskByEpic(epic.getId());
        manager.history();
        manager.getSubtaskByEpic(epicNew.getId());
        manager.history();

        manager.add(task);
        manager.getTask();
        manager.history();
        manager.removeTaskById(6);
        manager.history();
        manager.removeTaskById(1);
        manager.history();
    }
}
