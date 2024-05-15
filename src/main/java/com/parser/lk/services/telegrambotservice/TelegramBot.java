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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {


    @Value("${telegram.bot.name}")
    private String botUsername;


    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.chat.id}")
    private String chatId;

    private final Logger logger = LoggerFactory.getLogger(TelegramBot.class);


    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update.toString());
    }

    public boolean sendMessage(String text, String url) {
        SendMessage message = new SendMessage();
        message.setChatId(this.chatId);
        message.setText(text);

        if (url != null || url != "") {
            message.setReplyMarkup(this.createButton(url));
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            logger.error(e.toString());
            return false;
        }
        return true;
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    private InlineKeyboardMarkup createButton(String url) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List <List<InlineKeyboardButton>> rowsInline = new ArrayList< >();
        List < InlineKeyboardButton > rowInline = new ArrayList < > ();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
        inlineKeyboardButton.setUrl(url);
        inlineKeyboardButton.setText("Download");
        inlineKeyboardButton.setCallbackData("Call back data");

        rowInline.add(inlineKeyboardButton);

        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

}