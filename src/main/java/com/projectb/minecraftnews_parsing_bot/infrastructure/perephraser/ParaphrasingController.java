package com.projectb.minecraftnews_parsing_bot.infrastructure.perephraser;
import com.projectb.minecraftnews_parsing_bot.infrastructure.botconfig.BotConfig;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ParaphrasingController {


    @Autowired
    private ParaphrasingService paraphrasingService;

    @PostMapping("/paraphrase")
    public String paraphrase(@RequestParam String inputText) {
        //  API ключ
        String apiKey = "Tt1ABM76YyUppuxI74KZ6aGsjuMRMqXq";

        try {
            return paraphrasingService.paraphraseText(apiKey, inputText);
        } catch (IOException e) {
            e.printStackTrace();
            return "Error occurred while paraphrasing.";
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}

