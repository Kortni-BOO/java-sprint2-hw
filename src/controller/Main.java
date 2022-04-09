package controller;

import API.HttpTaskServer;
import API.KVServer;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Пришло время практики!");

        Task task = new Task("Купить помидорки", "На салат",
                LocalDateTime.of(2022, 12, 3, 4, 55),
                121
        );
        ArrayList<SubTask> subtasks = new ArrayList<>();
        Epic epic = new Epic("Найти второй носок", "С китами",
                LocalDateTime.of(2022, 12, 3, 8, 55),
                121
        );

        epic.setStatus(Status.DONE);
        SubTask subtask = new SubTask("Разобрать ящик", "в комнате",
                LocalDateTime.of(2022, 12, 3, 6, 00),
                20
        );
        subtask.setEpicId(epic.getId());
        subtask.setStatus(Status.NEW);
        SubTask subtask1 = new SubTask("Найти корзину", "солома",
                LocalDateTime.of(2022, 12, 3, 10, 55),
                21
        );

        subtask1.setEpicId(epic.getId());
        subtask1.setStatus(Status.NEW);
        SubTask subtask2 = new SubTask("Помыть машину", "и коврики",
                LocalDateTime.of(2022, 12, 3, 1, 55),
                139);
        subtask2.setEpicId(epic.getId());

        HttpTaskServer httpTaskServer = new HttpTaskServer();

        new KVServer().start();

        URI url = URI.create("http://localhost:8078/register");
        HTTPTaskManager httpTaskManager = new HTTPTaskManager(url);
        httpTaskManager.add(task);
        httpTaskManager.add(epic);
        httpTaskManager.add(subtask);
        httpTaskManager.save();
        httpTaskServer.start();
        httpTaskManager.loadFromServer("task");
        httpTaskManager.loadFromServer("epic");
        httpTaskManager.loadFromServer("subtask");

        subtask2.setStatus(Status.NEW);
        subtasks.add(subtask);
        subtasks.add(subtask1);
        subtasks.add(subtask2);

        httpTaskManager.add(subtask);
        httpTaskManager.add(subtask1);
        httpTaskManager.add(subtask2);


        ArrayList<SubTask> newSubTask = new ArrayList<>();

        httpTaskManager.getEpics();
        httpTaskManager.getSubtaskByEpic(epic.getId());
        httpTaskManager.history();
        httpTaskManager.getSubtaskByEpic(epic.getId());
        httpTaskManager.getTask();
        System.out.println("___________________________________________________");
        System.out.println(epic.getId());
        System.out.println(subtask.getEpicId());

        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        //fileBackedTasksManager.toString(task);
        fileBackedTasksManager.add(task);
        fileBackedTasksManager.getTask();
        fileBackedTasksManager.history();
        fileBackedTasksManager.save();

    }
}
