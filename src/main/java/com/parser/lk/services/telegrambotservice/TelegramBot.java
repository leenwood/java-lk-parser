package com.parser.lk.services.telegrambotservice;

import com.parser.lk.services.applicationservice.applicationstatus.CreatingDocumentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMember;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    @Value("${telegram.bot.name}")
    private String botUsername;


    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chat.id}")
    private String chatId;

    private final Logger logger = LoggerFactory.getLogger(CreatingDocumentService.class);


    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());
    }

    public boolean sendMessage(String text) {
        SendMessage message = new SendMessage();
        message.setChatId(this.chatId);
        message.setText(text);
        try {
            execute(message); // Отправляем сообщение
        } catch (TelegramApiException e) {
            logger.error(e.toString());
            return false;
        }
        return true;
    }

    @Override
    public String getBotUsername() {
        // Возвращает имя вашего бота
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        // Возвращает токен вашего бота
        return this.botToken;
    }

}