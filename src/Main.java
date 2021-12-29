import task.Epic;
import task.EpicList;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Пришло время практики!");
        Task task = new Task("Купить телефон", "Для крутых фото", 1,"NEW");
        Task task2 = new Task("Купить телефон", "Для крутых фото", 2,"NEW");
        Task task3 = new Task("Отдать долги", "За все тачки и квартиру",3, "NEW");
        Task task4 = new Task("Отдать долги", "За все тачки и квартиру", 4, "NEW");

        SubTask subtask = new SubTask("Отдать долги>", "За все тачки и квартиру",5, "NEW", 2);
        SubTask subtask1 = new SubTask("Похудеть", "до 55",6, "NEW", 1);
        SubTask subtask2 = new SubTask("Купить платье>", "с разрезом",7, "NEW",3);
        ArrayList<Object> subtasks = new ArrayList<>();
        subtasks.add(subtask);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        //ArrayList<Subtask> subtasks = new ArrayList<>();

        EpicList epic1 = new EpicList(task, subtasks);
        //System.out.println(epic1.task);

        System.out.println(task);
        System.out.println(subtask);
        System.out.println(task2);
        System.out.println(task3);
        System.out.println(task4);
        System.out.println(task.id == task2.id);
        System.out.println(task2.id == task4.id);


    }
}
