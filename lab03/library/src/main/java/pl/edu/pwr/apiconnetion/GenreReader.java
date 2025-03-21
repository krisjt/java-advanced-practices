package pl.edu.pwr.apiconnetion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pl.edu.pwr.apiconnetion.models.Genre;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class GenreReader implements Reader<Genre>{

    private static final String URLPATH = "https://wolnelektury.pl/api/genres/";
    private final List<Genre> genres;

    public GenreReader() {
        genres = getData();
    }

    public String getName(int id){
        return genres.get(id).getName();
    }

    public List<Genre> getList() {
        return genres;
    }

    private List<Genre> getData() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(URLPATH))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Genre>>() {}.getType();
            return gson.fromJson(response.body(), listType);
        }catch (InterruptedException | IOException | URISyntaxException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        return Collections.emptyList();
    }
}
