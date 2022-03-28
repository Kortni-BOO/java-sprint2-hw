package controller;

class InMemoryTasksManagerTest extends TaskManagerTest<InMemoryTasksManager> {

    public InMemoryTasksManagerTest() {
        this.taskManager = new InMemoryTasksManager();
    }
}