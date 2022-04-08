package API.handle;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.FileBackedTasksManager;
import controller.Managers;
import model.Task;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class TaskHandle implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    FileBackedTasksManager fileBackedTasksManager;

    public TaskHandle() throws IOException {
        fileBackedTasksManager = Managers.getDefaultFl();
    }
    @Override
    public void handle(HttpExchange h) throws IOException {
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
    }
}
