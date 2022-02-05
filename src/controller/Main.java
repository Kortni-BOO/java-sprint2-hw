package controller;


import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //LinkedList<String>
        System.out.println("Пришло время практики!");
        Task task = new Task("Купить помидорки", "На салат");
        Task task2 = new Task("Прочитать про сквозную авторизация", "Ну просто");
        Task task3 = new Task("Купить цветы", "Любоваться");

        List<String> list = new LinkedList<>();


        HashMap<Integer, Task> store = new HashMap<>();
        //store.put(task.id, task);
        ArrayList<SubTask> subtasks = new ArrayList<>();
        Epic epic = new Epic("TODO", "ACDC", subtasks);
        //InMemoryTasksManager manager = new InMemoryTasksManager();
        InMemoryTasksManager manager = Managers.getDefault();


        manager.add(epic);
        SubTask subtask = new SubTask("Отдать", "лишний вес", epic.getId());
        subtask.setStatus(Status.NEW);
        SubTask subtask1 = new SubTask("Похудеть", "до 55",epic.getId());
        subtask1.setStatus(Status.NEW);
        SubTask subtask2 = new SubTask("Купить платье>", "с разрезом",epic.getId());
        subtask2.setStatus(Status.NEW);

        //manager.removeTask();

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
        manager.getSubtaskByEpic(1);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);

        manager.updateTask(1,taskNew);

        SubTask subtaskNew = new SubTask("Отдать", "ничего", 2);
        manager.updateTask(3,subtaskNew);

        Epic epicNew = new Epic("Song", "Go to the floor", subtasks);
        manager.updateTask(4,epicNew);

        manager.removeTaskById(1);
        manager.updateStatusEpic(epic);
        manager.history();
    }
}
