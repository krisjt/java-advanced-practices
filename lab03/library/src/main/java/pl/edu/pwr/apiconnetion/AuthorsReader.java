package pl.edu.pwr.apiconnetion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pl.edu.pwr.apiconnetion.models.Author;
import pl.edu.pwr.service.Subject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class AuthorsReader implements Reader{

    private static final String URLPATH = "https://wolnelektury.pl/api/authors/";
    private static final Subject TYPE = Subject.Author;
    private final List<Author> authors;

    public AuthorsReader() {
        this.authors = getData();
    }

    public Integer getListSize() {
        return authors.size();
    }

    public String getName(int id){
        return authors.get(id).name;
    }

    public Subject getType(){
        return TYPE;
    }

    private List<Author> getData() {
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
            Type listType = new TypeToken<List<Author>>() {
            }.getType();
            return gson.fromJson(response.body(), listType);

        }catch(URISyntaxException | IOException | InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return Collections.emptyList();
    }

}
