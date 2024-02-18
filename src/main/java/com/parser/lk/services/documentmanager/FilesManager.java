package com.parser.lk.services.documentmanager;

import com.parser.lk.entity.Order;
import com.parser.lk.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FilesManager {


    @Value("${application.fileoutput.path}")
    private String filesPath;

    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(FilesManager.class);

    private final XlsxDocumentService xlsxDocumentService;

    public FilesManager(OrderRepository orderRepository, XlsxDocumentService xlsxDocumentService) {
        this.orderRepository = orderRepository;
        this.xlsxDocumentService = xlsxDocumentService;
    }


    public Map<String, Map<String, String>> getDocumentsList() {
        Map<String, Map<String, String>> result = new HashMap<>();

        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(String.format("classpath:/%s/*", this.filesPath));

            for (Resource resource : resources) {
                if (resource.getFilename() == ".gitkeep") {
                    continue;
                }

                String guid = this.extractGuid(resource.getFilename());

                if (guid == null) {
                    continue;
                }

                Order order = this.orderRepository.findFirstByGuid(guid);

                Map<String, String> object = this.convertToObject(resource, order);

                result.put(String.valueOf(order.getId()), object);
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }

        return result;
    }

    private Map<String, String> convertToObject(Resource resource, Order order) throws IOException {
        Map<String, String> object = new HashMap<>();
        object.put("id", String.valueOf(order.getId()));
        object.put("datetime", String.valueOf(new Date(order.getTimestamp())));
        object.put("filename", resource.getFilename());
        object.put("uri", String.valueOf(resource.getURI()));
        object.put("url", String.valueOf(resource.getURL()));
        object.put("description", resource.getDescription());
        return object;
    }

    public void createExcelDocument(Long id) {
        this.xlsxDocumentService.createXlsxDocumentByOrderId(id);
    }

    private String extractGuid(String name) {
        Pattern pattern = Pattern.compile("(guid-\\d+)\\.xlsx");
        Matcher matcher = pattern.matcher(name);

        // Проверяем, совпадает ли строка с регулярным выражением
        if (matcher.matches()) {
            return matcher.group(1); // Возвращаем значение, соответствующее первой группе в скобках
        } else {
            return null; // Если не найдено совпадение
        }
    }

}
