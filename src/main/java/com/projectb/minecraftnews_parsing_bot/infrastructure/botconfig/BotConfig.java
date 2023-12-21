package com.projectb.minecraftnews_parsing_bot.infrastructure.botconfig;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Data
@Configuration
@EnableScheduling
@PropertySource("application.properties")
public class BotConfig {

    @Value(value = "${bot.name}")
    String botName;
    @Value(value = "${bot.token}")
    String token;
    @Value(value = "${telegram.channel.id}")
    private String channelId;
    @Value(value = "${api.key}")
    private String paraphrasesApiKey;

}
