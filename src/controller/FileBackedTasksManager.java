package controller;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import java.util.TreeSet;

import API.HttpTaskServer;
import API.KVServer;
import error.ManagerSaveException;
import model.Epic;
import model.Status;
import model.SubTask;
import model.Task;

public class FileBackedTasksManager extends InMemoryTasksManager {
    File file = new File("./src/resources", "history.csv");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");

    @Override
    public void resetId() {
        //newId = 0;
        super.resetId();
    }

    @Override
    public void add(Task task) {
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

    //?????????? ???????????????????? ???????????? ?? ????????????
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

    //?????????? ???????????????? ???????????? ???? ???????????? String value
    public Task fromString(String value) {
        Task task = new Task("????????", "test",
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

    //?????????? ?????? ???????????????????? ?????????????????? ??????????????
    public static String historyToString(HistoryManager manager) {
        StringBuilder builder = new StringBuilder();
        for(Task k : manager.getHistory()) {
            builder.append(k.getId() + ",");
        }
        String asString = builder.toString();
        return asString;
    }

    //?????????? ?????? ?????????????????????????? ?????????????????? ?????????????? ???? CSV
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


    public FileBackedTasksManager loadFromFile(File file) throws IOException {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
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

    public static void main(String[] args) throws IOException, InterruptedException {

        Task task = new Task("???????????? ??????????????????", "???? ??????????",
                LocalDateTime.of(2022, 12, 3, 4, 55),
                121
        );
        ArrayList<SubTask> subtasks = new ArrayList<>();
        Epic epic = new Epic("?????????? ???????????? ??????????", "?? ????????????",
                LocalDateTime.of(2022, 12, 3, 8, 55),
                121
        );

        epic.setStatus(Status.DONE);
        SubTask subtask = new SubTask("?????????????????? ????????", "?? ??????????????",
                LocalDateTime.of(2022, 12, 3, 6, 00),
                20
        );
        subtask.setEpicId(epic.getId());
        subtask.setStatus(Status.NEW);
        SubTask subtask1 = new SubTask("?????????? ??????????????", "????????????",
                LocalDateTime.of(2022, 12, 3, 10, 55),
                21
        );

        subtask1.setEpicId(epic.getId());
        subtask1.setStatus(Status.NEW);
        SubTask subtask2 = new SubTask("???????????? ????????????", "?? ??????????????",
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


    }

}