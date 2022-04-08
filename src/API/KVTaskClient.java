package API;
import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class KVTaskClient {
    URI uri;
    HttpClient client;
    public String apiKey;

    public KVTaskClient() throws IOException, InterruptedException {
        client = HttpClient.newHttpClient();
        uri = URI.create("http://localhost:8078/register");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        // получаем стандартный обработчик тела запроса с конвертацией содержимого в строку
        HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();

        // отправляем запрос и получаем ответ от сервера
        HttpResponse<String> response = client.send(request, handler);
        apiKey = response.body();
        System.out.println(apiKey);
    }

    //должен сохранять состояние менеджера задач через запрос POST /save/<ключ>?API_KEY=.
    public void put(String key, String json) throws IOException, InterruptedException {
        try{
            GsonBuilder gsonBuilder = new GsonBuilder();
            //gsonBuilder.setPrettyPrinting();
            Gson gson = gsonBuilder
                    .registerTypeAdapter(
                            LocalDateTime.class,
                            (JsonDeserializer<LocalDateTime>) (jsonT, type, JsonDeserializerContext) ->
                                    LocalDateTime.parse(jsonT.getAsString())
                    )
                    .registerTypeAdapter(
                            LocalDateTime.class,
                            (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                                    new JsonPrimitive(src.toString())
                    )
                    .create();

            String jsonTask = gson.toJson(json);
            System.out.println("JSON " + jsonTask.getClass());
            uri = URI.create("http://localhost:8078/save/" + key + "?API_KEY=" + apiKey);
            final HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(jsonTask);
            HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(body).build();
            client = HttpClient.newHttpClient();
            HttpResponse.BodyHandler<String> handler = HttpResponse.BodyHandlers.ofString();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response);
        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }

    }

    //должен возвращать состояние менеджера задач через запрос GET /load/<ключ>?API_KEY=
    public String load(String key) {
        String text = "";
        System.out.println("p");
        URI uri = URI.create("http://localhost:8078/load/" + key + "?API_KEY=" + apiKey);
        client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder() // получаем экземпляр билдера
                .GET()    // указываем HTTP-метод запроса
                .uri(uri) // указываем адрес ресурса
                .build(); // заканчиваем настройку и создаём ("строим") http-запрос
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonElement jsonElement = JsonParser.parseString(response.body());
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for(int i = 0; i < jsonArray.size(); i++) {
                JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
                System.out.println(jsonObject.toString());
                text = jsonObject.toString();
            }

        } catch (IOException | InterruptedException e) { // обрабатываем ошибки отправки запроса
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        return text;
    }
}
