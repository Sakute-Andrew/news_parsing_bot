package com.projectb.minecraftnews_parsing_bot.infrastructure.dataparser;

import com.projectb.minecraftnews_parsing_bot.domain.enteties.News;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class NewsParserService {

    BufferedImage image;
    private static final String NEWS_URL = "https://www.pravda.com.ua/news/";
    private static final String NEWS_URL_PARSING = "https://www.pravda.com.ua";

    private Set<String> parsedPostUrls = new HashSet<>(); // Зберігати зпарсені пости

    public List<News> parseNews() {
        List<News> newsList = new ArrayList<>();

        try {
            Document document = Jsoup.connect(NEWS_URL).get();
            var latestNewsElement = document.select(".article_header").first();

                String href = latestNewsElement.select("a").attr("href");
                String postUrl = href.startsWith("/") ? NEWS_URL_PARSING + href : href;
            if (postUrl.contains("epravda.com.ua/news")) {
                // Викликати функцію для аналізу новин

            } else if (postUrl.contains("eurointegration.com.ua/news")) {
                // Викликати функцію для аналізу новин на eurointegration.com.ua

            } else if (postUrl.contains("life.pravda.com.ua")) {
                // Викликати функцію для аналізу сторінки на life.pravda.com.ua

            } else {
                // Якщо посилання не відоме, реагувати відповідним чином
            }

                log.info("URL Post: " + postUrl);

                // Перевірка, чи пост вже був зпарсений
                if (!parsedPostUrls.contains(postUrl)) {
                    // Переходимо за посиланням на повну статтю
                    Document fullArticleDocument = Jsoup.connect(postUrl).get();

                    String title = fullArticleDocument.select(".post_title").text();
                    log.info("Post title: " + title);

                    // Перевірка наявності джерела та відео
                    Elements contentElements = fullArticleDocument.select(".post_text > p");
                    StringBuilder contentBuilder = new StringBuilder();

                    int paragraphCount = 0;
                    for (Element contentElement : contentElements) {
                        String paragraphText = contentElement.text();
                        if (!paragraphText.contains("Джерело") && !paragraphText.contains("ВІДЕО ДНЯ") && !paragraphText.contains("Деталі:") && !paragraphText.contains("Передісторія:")) {
                            contentBuilder.append("◼\uFE0F").append(paragraphText).append("\n\n");
                            paragraphCount++;
                            if (paragraphCount >= 2) {
                                break; // вийти з циклу після двох абзаців
                            }
                        }
                    }

                    String content = contentBuilder.toString().trim();
                    log.info("Post text: " + content);

                    String photoUrl = fullArticleDocument.select(".post_photo_news_img").attr("src");
                    log.info("Photo url: " + photoUrl);
                    image = downloadImage(photoUrl); // Завантажуємо зображення за URL


                    News news = new News(title, content, image);
                    newsList.add(news);

                    // Додаємо пост до списку зпарсених
                    parsedPostUrls.add(postUrl);

                    log.info("Site parsed successfully");
                } else {
                    log.info("Post already parsed: " + postUrl);
                }

        } catch (IOException e) {
            log.error("Couldn't parse site");
        }

        return newsList;
    }

    private BufferedImage downloadImage(String imageUrl) {
        try {
            if (!imageUrl.isEmpty()) {
                URL url = new URL(imageUrl);
                InputStream inputStream = url.openStream();
                return ImageIO.read(inputStream);
            }else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
