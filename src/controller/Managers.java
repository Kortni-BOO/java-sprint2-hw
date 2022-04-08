package controller;

import API.HttpTaskServer;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class Managers {
    public static FileBackedTasksManager getDefaultFl() throws IOException {
        File file = new File("./src/resources", "history.csv");
        return new FileBackedTasksManager();
    }
    public static HTTPTaskManager getDefault() throws IOException, InterruptedException {
            URI url = URI.create("http://localhost:8078/register");
            return new HTTPTaskManager(url);
    }
}
