package pl.edu.pwr.apiconnetion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import pl.edu.pwr.apiconnetion.models.Kind;
import pl.edu.pwr.service.Subject;

import java.io.IOException;
import java.lang.constant.DirectMethodHandleDesc;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;

public class KindReader implements Reader{
    private static final String URLPATH = "https://wolnelektury.pl/api/kinds/";
    private static final Subject TYPE = Subject.Kind;
    private final List<Kind> kinds;
    public KindReader() {
        kinds = getData();
    }
    public String getName(int id){
        return kinds.get(id).getName();
    }
    public Integer getListSize() {
        return kinds.size();
    }
    public Subject getType(){
        return TYPE;
    }

    private List<Kind> getData() {
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
            Type listType = new TypeToken<List<Kind>>() {}.getType();
            return gson.fromJson(response.body(), listType);
        }catch (InterruptedException | IOException | URISyntaxException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        return Collections.emptyList();
    }
}
