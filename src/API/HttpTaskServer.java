package API;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import controller.FileBackedTasksManager;
import controller.HTTPTaskManager;
import controller.Managers;
import model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private HttpServer server;
    FileBackedTasksManager fileBackedTasksManager = Managers.getDefaultFl();
    //HTTPTaskManager fileBackedTasksManager = Managers.getDefault();

    public HttpTaskServer() throws IOException, InterruptedException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, JsonDeserializerContext) ->
                                LocalDateTime.parse(json.getAsString())
                )
                .registerTypeAdapter(
                        LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                                new JsonPrimitive(src.toString())
                )
                .create();
        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        System.out.println("GET /tasks");
                        for(Task task : fileBackedTasksManager.getPrioritizedTasks()) {
                            String jsonString = gson.toJson(task);
                            System.out.println(jsonString);
                        }
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        System.out.println("/tasks ждёт GET-запрос, а получил " + h.getRequestMethod());
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/tasks/task", (h) -> {
            URI requestURI = h.getRequestURI();
            String query = requestURI.getQuery();
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        System.out.println("GET /tasks/task");
                        if(query == null) {
                            for(Task task : fileBackedTasksManager.getTask()) {
                                String jsonString = gson.toJson(task);
                                System.out.println("task" + jsonString);
                            }
                        } else {
                            String idStr = query.split("=")[1];
                            System.out.println(idStr);
                            int id = Integer.parseInt(idStr);
                            fileBackedTasksManager.getTaskById(id);
                            System.out.println("id task " + fileBackedTasksManager.getTaskById(id));
                        }
                        h.sendResponseHeaders(200, 0);
                        break;
                    case "POST":
                        final String json = new String(h.getRequestBody().readAllBytes(), DEFAULT_CHARSET);
                        final Task newTask = gson.fromJson(json, Task.class);
                        if(query == null) {
                            fileBackedTasksManager.add(newTask);
                        } else {
                            String idStr = query.split("=")[1];
                            System.out.println(idStr);
                            int id = Integer.parseInt(idStr);
                            fileBackedTasksManager.updateTask(id, newTask);
                        }
                        h.sendResponseHeaders(201, 0);
                        break;
                    case "DELETE":
                        if(query == null) {
                            fileBackedTasksManager.removeTask();
                        } else {
                            String idStr = query.split("=")[1];
                            System.out.println(idStr);
                            int id = Integer.parseInt(idStr);
                            System.out.println("1" + fileBackedTasksManager.getTask());
                            fileBackedTasksManager.removeTaskById(id);
                            System.out.println(" 2" + fileBackedTasksManager.getTask());
                        }
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/tasks/epic", (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        for(Task task : fileBackedTasksManager.getEpics()) {
                            String jsonString = gson.toJson(task);
                            System.out.println(jsonString);
                        }
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/tasks/history", (h) -> {
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        for(Task task : fileBackedTasksManager.history()) {
                            String jsonString = gson.toJson(task);
                            System.out.println(jsonString);
                        }
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
        server.createContext("/tasks/subtask", (h) -> {
            URI requestURI = h.getRequestURI();
            String query = requestURI.getQuery();
            try {
                switch (h.getRequestMethod()) {
                    case "GET":
                        System.out.println("GET /tasks/subtask");
                        if(query == null) {
                            System.out.println("Вы не указали индекс");
                        } else {
                            String idStr = query.split("=")[1];
                            System.out.println(idStr);
                            int id = Integer.parseInt(idStr);
                            fileBackedTasksManager.getSubtaskByEpic(id);
                            System.out.println("id task " + fileBackedTasksManager.getSubtaskByEpic(id));
                        }
                        h.sendResponseHeaders(200, 0);
                        break;
                    default:
                        h.sendResponseHeaders(405, 0);
                }
            } finally {
                h.close();
            }
        });
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