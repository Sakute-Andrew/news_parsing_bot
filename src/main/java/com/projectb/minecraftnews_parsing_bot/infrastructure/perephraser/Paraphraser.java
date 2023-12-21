package com.projectb.minecraftnews_parsing_bot.infrastructure.perephraser;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class Paraphraser {

    public String paraphraseText(String text) {
        try {
            URL url = new URL("https://rephrase-tool.com/api/index.php");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setDoOutput(true);

            Map<String, String> postData = new HashMap<>();
            postData.put("method", "getSynText");
            postData.put("text", "The text for the paraphrase");
            postData.put("backLight", "1");

            StringBuilder postDataString = new StringBuilder();
            for (Map.Entry<String, String> entry : postData.entrySet()) {
                if (postDataString.length() != 0) {
                    postDataString.append('&');
                }
                postDataString.append(entry.getKey());
                postDataString.append('=');
                postDataString.append(entry.getValue());
            }

            byte[] postDataBytes = postDataString.toString().getBytes("UTF-8");

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.write(postDataBytes);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String resultSyn = response.toString();
            connection.disconnect();
            JSONObject jsonObject = new JSONObject(resultSyn);
            return jsonObject.getString("modified_text");
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred while paraphrasing.";
        }
    }
}