package test;

import controller.InMemoryHistoryManager;
import model.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    InMemoryHistoryManager historyManager = new InMemoryHistoryManager();
    Task task = new Task("test", "test", LocalDateTime.now(), 120);
    Task task1 = new Task("test1", "test1", LocalDateTime.now(), 120);
    Task task2 = new Task("test2", "test2", LocalDateTime.now(), 120);

    @Test
    public void shouldReturn0TaskInHistory() {
        assertNull(historyManager.getHistory(), "История пустая.");
        assertEquals(null, historyManager.getHistory());
    }

    @Test
    public void shouldReturnAGetHistory() {
        historyManager.add(task);
        assertNotNull(historyManager.getHistory(), "История не пустая.");
        assertEquals(1, historyManager.getHistory().size());
    }

    @Test
    public void shouldDuplicationAddLastGetHistory() {
        task.setId(1);
        historyManager.add(task);
        task1.setId(2);
        historyManager.add(task1);
        historyManager.add(task);
        assertEquals(task1.getId(), historyManager.head.data.id);
        assertEquals(task.getId(), historyManager.tail.data.id);
    }

    @Test
    public void shouldRemoveHead() {
        task.setId(1);
        historyManager.add(task);
        task1.setId(2);
        historyManager.add(task1);
        task2.setId(3);
        historyManager.add(task2);
        //удаления из начала
        historyManager.remove(1);
        assertEquals(task1.getId(), historyManager.head.data.id);
    }

    @Test
    public void shouldRemoveMiddle() {
        task.setId(1);
        historyManager.add(task);
        task1.setId(2);
        historyManager.add(task1);
        task2.setId(3);
        historyManager.add(task2);
        //удаления из середины
        historyManager.remove(2);
        assertEquals(task2.getId(), historyManager.head.next.data.id);
    }

    @Test
    public void shouldRemoveTail() {
        task.setId(1);
        historyManager.add(task);
        task1.setId(2);
        historyManager.add(task1);
        task2.setId(3);
        historyManager.add(task2);
        //удаления последнего элемента
        historyManager.remove(3);
        assertEquals(task1.getId(), historyManager.tail.data.id);
    }

}