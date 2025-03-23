package pl.edu.pwr.apiconnetion;

import com.google.gson.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AuthorDeserializer implements JsonDeserializer<List<String>> {

    public AuthorDeserializer() {
    }

    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<String> authors = new ArrayList<>();

        if (json.isJsonArray()) {
            JsonArray jsonArray = json.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                authors.add(element.getAsString());
            }
        } else if (json.isJsonPrimitive()) {
            authors.add(json.getAsString());
        }

        return authors;
    }
}

