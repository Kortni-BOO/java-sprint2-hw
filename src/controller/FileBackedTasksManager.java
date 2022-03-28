package controller;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import error.ManagerSaveException;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

public class FileBackedTasksManager extends InMemoryTasksManager {
    public File file;
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("mm");

    public FileBackedTasksManager(File file) {
        this.file = file;
    }

    @Override
    public void resetId() {
        //newId = 0;
        super.resetId();
    }

    @Override
    public void add(Task task){
        super.add(task);
        save();
    }

    @Override
    public ArrayList<Task> getTask() {
        ArrayList<Task> tasks = super.getTask();
        save();
        return tasks;
    }


    @Override
    public ArrayList<Epic> getEpics() {
        ArrayList<Epic> epics = super.getEpics();
        save();
        return epics;
    }

    @Override
    public ArrayList<SubTask> getSubtaskByEpic(int id) {
        ArrayList<SubTask> subTask = super.getSubtaskByEpic(id);
        save();
        return subTask;
    }

    @Override
    public void computationTimeEpic(Epic epic) {
        super.computationTimeEpic(epic);
    }

    public void save() {
        file.delete();
        try (Writer fileWriter = new FileWriter(file, true)) {
            fileWriter.write("id,type,name,status,description,epic,startTime,duration");
            fileWriter.write("\n");
            for(Task t : store.values()) {
                fileWriter.write(toString(t));
                fileWriter.write("\n");
            }
            fileWriter.write("\n");
            fileWriter.write(historyToString(newList));
        } catch (IOException ex) {
            throw new ManagerSaveException(ex.getMessage());
        } catch (NullPointerException ignored) {}
    }

    @Override
    public List<Task> history() {
        return super.history();
    }

    //метод сохранения задачи в строку
    public String toString(Task task) {
        StringBuilder builder = new StringBuilder();
        if(task.getClass() == Task.class) {
            builder.append(task.getId() + ","
                    + TypeTask.TASK + ","
                    + task.title + ","
                    + task.getStatus() + ","
                    + task.subtitle + ","
                    + DATE_TIME_FORMATTER.format(task.startTime) + ","
                    + task.duration);
        } else if(task.getClass() == Epic.class) {
            builder.append(task.getId() + ","
                    + TypeTask.EPIC + ","
                    + task.title + ","
                    + task.getStatus() + ","
                    + task.subtitle + ","
                    + DATE_TIME_FORMATTER.format(task.startTime) + ","
                    + task.duration);
        } else {
            String sub = task.toString();

            String[] s = sub.split(",");
            builder.append(task.getId() + ","
                    + TypeTask.SUBTASK + ","
                    + task.title + ","
                    + task.getStatus() + ","
                    + task.subtitle + ","
                    + s[4] + ","
                    + DATE_TIME_FORMATTER.format(task.startTime) + ","
                    + task.duration);
        }
        String asString = builder.toString();
        return asString;
    }

    //метод создания задачи из строки String value
    public static Task fromString(String value) {
        Task task = new Task("Тест", "test",
                LocalDateTime.of(2022, 12, 3, 8, 55),
                121
        );
        String[] lines = value.split("\\n");
        int epicID = 0;
        ArrayList<SubTask> subtasks = new ArrayList<>();
        //-2
        for(int i = 1; i < lines.length - 2; i++) {
            String[] lineContents = lines[i].trim().split(",");
            if (lineContents[1].equals("TASK")){
                Task taskNew = new Task(lineContents[2], lineContents[4],
                        LocalDateTime.parse(lineContents[5], DATE_TIME_FORMATTER),
                        Long.parseLong(lineContents[6])
                );
                taskNew.setId(Integer.parseInt(lineContents[0]));
                checkStatus(lineContents[3], taskNew);
                System.out.println(taskNew);
                task = taskNew;
                store.put(task.getId(), task);
            } else if (lineContents[1].equals("EPIC") ) {
                Epic epic = new Epic(lineContents[2], lineContents[4],
                        LocalDateTime.parse(lineContents[5], DATE_TIME_FORMATTER),
                        Long.parseLong(lineContents[6])
                );
                epic.setId(Integer.parseInt(lineContents[0]));
                checkStatus(lineContents[3], epic);
                epicID = epic.getId();
                task = epic;
                store.put(task.getId(), task);
            }  if (lineContents[1].equals("SUBTASK")) {
                if(Integer.parseInt(lineContents[5]) == epicID) {
                    SubTask subtask = new SubTask(lineContents[2], lineContents[4],
                            LocalDateTime.parse(lineContents[5], DATE_TIME_FORMATTER),
                            Long.parseLong(lineContents[6])
                    );
                    subtask.setEpicId(epicID);
                    subtask.setId(Integer.parseInt(lineContents[0]));
                    checkStatus(lineContents[3], subtask);
                    subtasks.add(subtask);
                    task = subtask;
                    store.put(task.getId(), task);
                }
            }
        }
        return task;
    }

    public static void checkStatus(String value, Task task) {
        if(value.equals("NEW")) {
            task.setStatus(Status.NEW);
        } else if (value.equals("DONE")){
            task.setStatus(Status.DONE);
        } else {
            task.setStatus(Status.IN_PROGRESS);
        }
    }

    //метод для сохранения менеджера истории
    public static String historyToString(HistoryManager manager) {
        StringBuilder builder = new StringBuilder();
        for(Task k : manager.getHistory()) {
            builder.append(k.getId() + ",");
        }
        String asString = builder.toString();
        return asString;
    }

    //метод для востановления менеджера истории из CSV
    public static List<Integer> historyFromString(String value) throws IOException {
        String[] list = value.split("\\n");
        String[] id = list[list.length - 1].split(",");
        List<Integer> ids = new ArrayList<>();
        if(ids.size() != 0) {
            for(int i = 0; i < id.length; i++) {
                ids.add(Integer.parseInt(id[i]));
            }
        }
        return ids;
    }

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return super.getPrioritizedTasks();
    }


    static FileBackedTasksManager loadFromFile(File file) throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        String value = Files.readString(Path.of(file.getPath()));
        fromString(value);
        List<Integer> ids = new ArrayList<>();
        ids = historyFromString(value);
        for(int i = 0; i < ids.size(); i++) {
            newList.add(store.get(ids.get(i)));
        }
        System.out.println(store.toString());
        return fileBackedTasksManager;
    }

    public static void main(String[] args) throws IOException {
        /*
        Task task = new Task("Купить помидорки", "На салат",
                LocalDateTime.of(2022, 12, 3, 4, 55),
                121
        );
        task.setStatus(Status.NEW);
        System.out.println("Это 1 " + task.getId());

        File file = new File("./src/resources", "history.csv");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);

        fileBackedTasksManager.getTask();
        ArrayList<SubTask> subtasks = new ArrayList<>();
        Epic epic = new Epic("Найти второй носок", "С китами",
                LocalDateTime.of(2022, 12, 3, 8, 55),
                121
        );

        epic.setStatus(Status.DONE);
        fileBackedTasksManager.add(epic);
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



        subtask2.setStatus(Status.NEW);
        subtasks.add(subtask);
        subtasks.add(subtask1);
        subtasks.add(subtask2);
        fileBackedTasksManager.add(task);
        System.out.println("Это 2 " + task.getId());

        epic.setSubtasks(subtasks);

        System.out.println("Sub " + epic.getSubtask());

        epic.setSubtasks(subtasks);
        fileBackedTasksManager.add(subtask);
        fileBackedTasksManager.add(subtask1);
        fileBackedTasksManager.getTask();
        fileBackedTasksManager.save();

        Task task3 = new Task("Пить чай без сахара", "зеленый",
                LocalDateTime.of(2022, 12, 3, 2, 55),
                121
        );
        task3.setStatus(Status.NEW);

        System.out.println("_____________________________________________________________________________________");
        Task task2 = new Task("Купить вкусняшки", "Для Хло",
                LocalDateTime.of(2022, 12, 3, 2, 55),
                121
        );

        System.out.println("NEW=============================================================================NEW");
        //System.out.println(epic.endTime);
        task.setStatus(Status.NEW);
        fileBackedTasksManager.computationTimeEpic(epic);
        System.out.println("EndTime " + epic.endTime);
        fileBackedTasksManager.add(task2);
        fileBackedTasksManager.add(task3);
        fileBackedTasksManager.getPrioritizedTasks();
        loadFromFile(file);

        Epic epic1 = new Epic("Найти второй носок", "С китами",
                LocalDateTime.of(2022, 12, 3, 8, 55),
                12
        );
        ArrayList<SubTask> suub = new ArrayList<>();
        epic.setSubtasks(suub);
        fileBackedTasksManager.updateStatusEpic(epic1);
        System.out.println("EPIC status : " + epic1.getStatus());
        */
    }

}