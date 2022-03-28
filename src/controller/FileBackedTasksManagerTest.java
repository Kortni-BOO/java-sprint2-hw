package controller;

import model.Task;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest extends TaskManagerTest<FileBackedTasksManager> {
    File file = new File("./src/resources", "history.csv");
    Task task = new Task("testTask", "testTask",
            LocalDateTime.of(2022, 12, 3, 3, 05),
            120
    );
    String res = "id,type,name,status,description,epic,startTime,duration\n" +
            "1,TASK,testTask,null,testTask,03:05 03.12.22,120\n" +
            "\n" +
            "1,";

    public FileBackedTasksManagerTest() {
        this.taskManager = new FileBackedTasksManager(file);
    }

    //метод сохранения задачи в строку тестируется в методе save
    @Test
    public void shouldSaveInFile() throws IOException {
        String value = Files.readString(Path.of(file.getPath()));
        String test = value.trim();
        taskManager.add(task);
        taskManager.save();
        assertEquals(res, test);
    }


}