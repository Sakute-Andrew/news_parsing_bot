package com.projectb.minecraftnews_parsing_bot.infrastructure.dataparser;

import com.projectb.minecraftnews_parsing_bot.domain.enteties.News;
import com.projectb.minecraftnews_parsing_bot.domain.telegram.TelegramBot;
import com.projectb.minecraftnews_parsing_bot.infrastructure.perephraser.Paraphraser;
import com.projectb.minecraftnews_parsing_bot.infrastructure.perephraser.ParaphrasingController;
import com.projectb.minecraftnews_parsing_bot.infrastructure.perephraser.ParaphrasingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@Component
public class NewsSchedulerService {

    @Autowired
    private NewsParserService newsParserService;

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private Paraphraser paraphrase;// Додайте цю залежність

    @Scheduled(fixedRate = 30000) // 3 хвилини в мілісекундах
    public void publishNewsToTelegram() {
        List<News> newsList = newsParserService.parseNews(); // Парсимо новини

        for (News news : newsList) {
            if (news.getPhoto() != null) {
                telegramBot.sendPhotoToChat(news.getTitle(),news.getContent(), news.getPhoto());
                log.info("Sending with photo"); // Відправляємо в канал
            } else {
                telegramBot.sentPostToChat(news.getTitle(),news.getContent());
            }
        }
    }
}