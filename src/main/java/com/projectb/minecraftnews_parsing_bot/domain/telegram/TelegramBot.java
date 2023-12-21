package com.projectb.minecraftnews_parsing_bot.domain.telegram;

import com.projectb.minecraftnews_parsing_bot.infrastructure.botconfig.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }
    @Override
    public void onUpdateReceived(Update update) {

    }

    public void sentPostToChat( String title, String caption){
        SendMessage message = new SendMessage();
        message.enableHtml(true);
        message.setChatId(config.getChannelId());
        message.setText("<b>"+title+"</b>" + "\n\n" + caption + "\n\n" + "✅️ " + "<a href=\"https://t.me/paragraph_ukraine\">ПАРАГРАФ § Україна</a>");
        try{
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }


    public void sendPhotoToChat(String title, String caption, BufferedImage image) {
        try {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(config.getChannelId());
        sendPhoto.setParseMode("HTML");
        sendPhoto.setCaption("<b>"+title+"</b>" + "\n\n" + caption + "\n\n" + "✅️ " + "<a href=\"https://t.me/paragraph_ukraine\">ПАРАГРАФ § Україна</a>");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        sendPhoto.setPhoto(new InputFile(is, "photo.png")); // Замість "photo.png" ви можете вказати ім'я файлу за потреби
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            log.error("Couldn't send photo");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }

}
