package API.handle;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.FileBackedTasksManager;
import controller.Managers;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class SubTaskHandle implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    FileBackedTasksManager fileBackedTasksManager;

    public SubTaskHandle() throws IOException {
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
    }
}
