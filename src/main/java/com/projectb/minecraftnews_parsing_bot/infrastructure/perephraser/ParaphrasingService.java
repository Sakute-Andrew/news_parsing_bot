package com.projectb.minecraftnews_parsing_bot.infrastructure.perephraser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject; // Імпорт для org.json
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class ParaphrasingService {

    public String paraphraseText(String apiKey, String inputText) throws IOException, JSONException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost request = new HttpPost("https://rephrase-tool.com/api/index.php");

        request.addHeader("apikey", apiKey);
        request.addHeader("Content-Type", "application/json");

        String jsonData = "{\n" +
                "  \"text\": \"" + inputText + "\"\n" +
                "}";

        StringEntity entity = new StringEntity(jsonData);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);

        String responseBody = EntityUtils.toString(response.getEntity());

        JSONObject jsonObject = new JSONObject(responseBody);
        return jsonObject.getString("paraphrased");
    }
}

