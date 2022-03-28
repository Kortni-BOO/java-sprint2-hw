package controller;

import model.Epic;
import model.SubTask;
import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest <T extends TaskManager> {
    T taskManager;
    ArrayList<SubTask> subtasks = new ArrayList<>();
    Task task = new Task("testTask", "testTask",
            LocalDateTime.of(2022, 12, 3, 3, 05),
            120
    );
    Task task2 = new Task("testTask2", "testTask2",
            LocalDateTime.of(2022, 12, 3, 22, 00),
            120
    );
    Task task3 = new Task("testTask3", "testTask3",
            LocalDateTime.of(2022, 12, 3, 22, 00),
            120
    );
    Epic epic = new Epic("testEpic", "testEpic", LocalDateTime.now(), 0);
    SubTask subTask = new SubTask("testSubTask1", "testSubtask1",
            LocalDateTime.of(2022, 12, 3, 1, 00),
            60
    );
    SubTask subTask2 = new SubTask("testSubTask2", "testSubTask2",
            LocalDateTime.of(2022, 12, 3, 8, 55),
            1
    );

    @BeforeEach
    public void beforeEach() {
        taskManager.resetId();
        taskManager.removeTask();
    }
    @Test
    public void shouldReturnAllTask() {
        taskManager.add(task);
        assertEquals(1, taskManager.getTask().size());
        assertEquals(task, taskManager.getTask().get(0));
    }

    @Test
    public void shouldReturnAllEpic() {
        taskManager.add(epic);
        System.out.println(taskManager.getEpics().size());
        assertEquals(1, taskManager.getEpics().size());
        assertEquals(epic, taskManager.getEpics().get(0));
    }

    @Test
    public void shouldReturnSubtaskByEpic() {
        subtasks.add(subTask);
        epic.setSubtasks(subtasks);
        subTask.setEpicId(epic.getId());
        taskManager.add(epic);
        taskManager.add(subTask);
        taskManager.getSubtaskByEpic(epic.getId());
        assertEquals(1, taskManager.getSubtaskByEpic(epic.getId()).size());
        assertEquals(subTask, taskManager.getSubtaskByEpic(epic.getId()).get(0));
    }

    @Test
    public void shouldReturnTaskById() {
        taskManager.add(task);
        taskManager.getTaskById(task.getId());
        assertEquals(1,taskManager.getTaskById(task.getId()).getId());
    }

    @Test
    public void shouldUpdateTask() {
        taskManager.add(task);
        Task taskNew = new Task("не тест", "test", LocalDateTime.now(), 2);
        taskManager.updateTask(task.getId(), taskNew);
        assertEquals(taskNew, taskManager.getTaskById(task.getId()));
    }

    @Test
    public void shouldRemoveTask() {
        taskManager.add(task3);
        taskManager.add(epic);
        taskManager.removeTask();
        assertEquals(0, taskManager.getTask().size());
        assertEquals(0, taskManager.getEpics().size());
    }

    @Test
    public void shouldRemoveTaskById() {
        task.setId(1);
        taskManager.add(task3);
        taskManager.removeTaskById(1);
        assertEquals(0, taskManager.getTask().size());
    }

    // Обновление статуса Эпика отдельно проверяется в тестах EpicTest

    @Test
    public void shouldComputationTimeEpic() {
        epic.setId(1);
        taskManager.add(epic);
        subTask.setId(2);
        subtasks.add(subTask);
        subTask2.setId(3);
        subtasks.add(subTask2);
        epic.setSubtasks(subtasks);
        taskManager.computationTimeEpic(epic);
        assertEquals(subTask.startTime, epic.startTime);
        assertEquals(61, epic.duration);
    }

    @Test
    public void shouldReturnHistory() {
        taskManager.add(task);
        taskManager.add(epic);
        taskManager.getTask();
        taskManager.getEpics();
        assertEquals(2, taskManager.history().size());
    }

    @Test
    public void shouldGetPrioritizedTasks() {
        taskManager.add(task2);// 22:00
        taskManager.add(task); //3:05
        assertEquals(taskManager.getTask().get(0), task2);
        assertEquals(taskManager.getPrioritizedTasks().first(), task);
    }


}