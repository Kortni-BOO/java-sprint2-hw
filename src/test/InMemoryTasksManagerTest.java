package test;

import controller.InMemoryTasksManager;
import test.TaskManagerTest;

class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTasksManager> {

    public InMemoryTasksManagerTest() {
        this.taskManager = new InMemoryTasksManager();
    }
}