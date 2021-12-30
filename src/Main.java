import task.Epic;
import task.EpicList;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Task task = new Task("Купить телефон", "Для крутых фото","NEW");
        Task task2 = new Task("Купить телефон", "Для крутых фото","NEW");
        Task task3 = new Task("Купить цветы", "За все тачки и квартиру","NEW");


        SubTask subtask = new SubTask("Отдать долги>", "За все тачки и квартиру","NEW", 2);
        SubTask subtask1 = new SubTask("Похудеть", "до 55","NEW", 1);
        SubTask subtask2 = new SubTask("Купить платье>", "с разрезом","NEW",3);
        HashMap<Integer, Task> store = new HashMap<>();
        //store.put(task.id, task);
        ArrayList<SubTask> subtasks = new ArrayList<>();
        subtasks.add(subtask);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        Epic epic = new Epic("TODO", "Go to the floor", "NEW", subtasks);
        Manager manager = new Manager();
        manager.add(task);
        Task taskNew = new Task("Job java-developer", "Great life","NEW");

        manager.add(task2);
        manager.add(task3);
        manager.add(epic);
        manager.getEpics();
        manager.getTask();
        manager.getSubtaskByEpic(epic);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);

        manager.updateTask(1,taskNew);

        SubTask subtaskNew = new SubTask("Отдать долги>", "За все тачки и квартиру","NEW", 2);
        manager.updateTask(3,subtaskNew);

        Epic epicNew = new Epic("LOOp", "Go to the floor", "NEW", subtasks);
        manager.updateTask(4,epicNew);

        manager.removeTaskById(1);
        //System.out.println(manager.add(task));
        //ArrayList<Subtask> subtasks = new ArrayList<>();

        //EpicList epic1 = new EpicList(task, subtasks);
        //System.out.println(epic1.task);

        //System.out.println(task);
        //System.out.println(subtask);
        //System.out.println(task2);
        //System.out.println(task3);
        //System.out.println(task4);
        //System.out.println(task.id  + " " + task2.id);
        //System.out.println(task2.id == task4.id);
        //System.out.println(store.values());


    }
}
