package com.parser.lk.services.applicationservice.applicationstatus;


import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.telegrambotservice.TelegramNotificationService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service("ParsingErrorStatusService")
public class ParsingErrorService extends BaseStatusService implements StatusInterface  {

    private final TelegramNotificationService telegramNotificationService;

    public ParsingErrorService(TelegramNotificationService telegramNotificationService) {
        this.telegramNotificationService = telegramNotificationService;
    }

    @Override
    public boolean doProcess(Long orderId) {
        if (!this.trySetupOrder(orderId)) {
            return false;
        }

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this.order.getTimestamp()), ZoneId.systemDefault());

        String text = String.format(
                "Номер запроса - %s\nТекст запроса - %s\nДата запроса - %s\n\nЗаявку не удалось обработать\n",
                this.order.getGuid(),
                this.order.getSearchText(),
                dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );

        if (!this.telegramNotificationService.sendNotification(text, null)) {
            this.logger.info(String.format("Message not be delivery. OrderId: %s", this.order.getId()));
        }
        return true;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.PARSING_ERROR;
    }

    @Override
    public String getNextStatusName() {
        return null;
    }
}
