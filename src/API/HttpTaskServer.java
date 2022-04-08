package API;

import API.handle.*;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private HttpServer server;

    public HttpTaskServer() throws IOException, InterruptedException {
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", new TasksHandle());
        server.createContext("/tasks/task", new TaskHandle());
        server.createContext("/tasks/epic", new EpicHandle());
        server.createContext("/tasks/history", new HistoryHandle());
        server.createContext("/tasks/subtask", new SubTaskHandle());
    }

    public void start() {
        System.out.println("Запускаем сервер на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        server.start();
    }

    public void stop(int delay) {
        server.stop(delay);
    }
}