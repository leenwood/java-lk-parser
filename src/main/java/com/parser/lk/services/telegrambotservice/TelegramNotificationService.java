package com.parser.lk.services.telegrambotservice;


import org.springframework.stereotype.Service;

@Service
public class TelegramNotificationService {

    private final TelegramBot telegramBot;

    public TelegramNotificationService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }


    public boolean sendNotification(String message, String url) {
        return this.telegramBot.sendMessage(message, url);
    }

}
