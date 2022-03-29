package test;

import controller.InMemoryTasksManager;
import model.Epic;
import model.Status;
import model.SubTask;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    InMemoryTasksManager inMemoryTasksManager = new InMemoryTasksManager();
    ArrayList<SubTask> subtasks = new ArrayList<>();
    Epic epic = new Epic("test", "test", LocalDateTime.now(), 120);
    SubTask subTask1 = new SubTask("test", "test", LocalDateTime.now(), 120);
    SubTask subTask2 = new SubTask("test", "test", LocalDateTime.now(), 120);

    @Test
    public void shouldStatusNewNotSubtaskUpdateStatusEpic() {
        epic.setSubtasks(subtasks);
        inMemoryTasksManager.updateStatusEpic(epic);
        Assertions.assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void shouldStatusNewAllSubtaskNewUpdateStatusEpic() {
        subTask1.setStatus(Status.NEW);
        subTask2.setStatus(Status.NEW);
        subtasks.add(subTask1);
        subtasks.add(subTask2);
        epic.setSubtasks(subtasks);
        inMemoryTasksManager.updateStatusEpic(epic);
        assertEquals(Status.NEW, epic.getStatus());
    }

    @Test
    public void shouldStatusDoneAllSubtaskDoneUpdateStatusEpic() {
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);
        subtasks.add(subTask1);
        subtasks.add(subTask2);
        epic.setSubtasks(subtasks);
        inMemoryTasksManager.updateStatusEpic(epic);
        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    public void shouldStatusDoneOrNewSubtaskIn_ProgressUpdateStatusEpic() {
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.NEW);
        subtasks.add(subTask1);
        subtasks.add(subTask2);
        epic.setSubtasks(subtasks);
        inMemoryTasksManager.updateStatusEpic(epic);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

    @Test
    public void shouldStatusIn_ProgressAllSubtaskIn_ProgressUpdateStatusEpic() {
        subTask1.setStatus(Status.IN_PROGRESS);
        subTask2.setStatus(Status.IN_PROGRESS);
        subtasks.add(subTask1);
        subtasks.add(subTask2);
        epic.setSubtasks(subtasks);
        inMemoryTasksManager.updateStatusEpic(epic);
        assertEquals(Status.IN_PROGRESS, epic.getStatus());
    }

}