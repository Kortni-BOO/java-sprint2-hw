package API.handle;

import com.google.gson.*;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import controller.FileBackedTasksManager;
import controller.Managers;
import model.Task;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class EpicHandle implements HttpHandler {
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    FileBackedTasksManager fileBackedTasksManager;

    public EpicHandle() throws IOException {
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

    }
}
