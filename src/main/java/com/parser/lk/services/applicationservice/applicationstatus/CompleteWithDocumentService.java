package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.entity.Order;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;
import com.parser.lk.services.telegrambotservice.TelegramNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;


@Service("COMPLETE_WITH_DOCUMENT")
public class CompleteWithDocumentService implements StatusInterface {


    @Value("${application.fileoutput.path}")
    private String filesDirectoryPath;

    @Value("${application.hostname}")
    private String serverAddress;


    @Value("${application.port}")
    private String serverPort;

    private final TelegramNotificationService telegramNotificationService;

    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(CompleteWithDocumentService.class);

    public CompleteWithDocumentService(TelegramNotificationService telegramNotificationService, OrderRepository orderRepository) {
        this.telegramNotificationService = telegramNotificationService;
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean doProcess(Long orderId) {

        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            this.logger.error(String.format("order by id %s not found", orderId));
            return false;
        }
        Order order = orderOptional.get();

        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(order.getTimestamp()), ZoneId.systemDefault());

        String text = String.format(
                "Номер запроса - %s\nТекст запроса - %s\nДата запроса - %s\nДокумент создан, ссылка для скачивания:\n",
                order.getGuid(),
                order.getSearchText(),
                dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
        String url = String.format(
                "%s://%s:%s/api/v1/files/excel/%s",
                "http",
                this.serverAddress,
                this.serverPort,
                order.getGuid()
        );


        if (!this.telegramNotificationService.sendNotification(text, url)) {
            this.logger.info(String.format("Message not be delivery. OrderId: %s", order.getId()));
        }
        return true;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.COMPLETE_WITH_DOCUMENT;
    }

    @Override
    public String getNextStatusName() {
        return null;
    }
}
