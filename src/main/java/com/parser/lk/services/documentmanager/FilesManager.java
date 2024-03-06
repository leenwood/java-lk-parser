package com.parser.lk.services.documentmanager;

import com.parser.lk.dto.FileStatusEnum;
import com.parser.lk.dto.response.analytics.DocumentExcelListResponse;
import com.parser.lk.dto.response.analytics.DocumentExcelResponse;
import com.parser.lk.entity.Order;
import com.parser.lk.entity.OrderExcelFileParam;
import com.parser.lk.repository.OrderExcelFileParamRepository;
import com.parser.lk.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FilesManager {


    @Value("${application.fileoutput.path}")
    private String filesDirectoryPath;

    @Value("${server.address}")
    private String serverAddress;


    @Value("${server.port}")
    private String serverPort;


    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(FilesManager.class);

    private final XlsxDocumentService xlsxDocumentService;

    private final OrderExcelFileParamRepository orderExcelFileParamRepository;

    public FilesManager(OrderRepository orderRepository, XlsxDocumentService xlsxDocumentService, OrderExcelFileParamRepository orderExcelFileParamRepository) {
        this.orderRepository = orderRepository;
        this.xlsxDocumentService = xlsxDocumentService;
        this.orderExcelFileParamRepository = orderExcelFileParamRepository;
    }

    public DocumentExcelListResponse getDocumentListV2() {
        DocumentExcelListResponse list = new DocumentExcelListResponse();
        for (OrderExcelFileParam file : this.orderExcelFileParamRepository.findByStatus(FileStatusEnum.READY)) {
            DocumentExcelResponse document = new DocumentExcelResponse();
            document.setId(file.getId());
            document.setPath(
                    String.format(
                            "%s://%s:%s/api/v1/files/excel/%s",
                            "http",
                            this.serverAddress,
                            this.serverPort,
                            file.getGuid()
                    ));
            document.setFilename(file.getFilename());
            list.getData().add(document);
        }
        list.setCount(this.orderExcelFileParamRepository.countByStatus(FileStatusEnum.READY));
        return list;
    }

    @Deprecated
    public Map<String, Map<String, String>> getDocumentsList() {
        Map<String, Map<String, String>> result = new HashMap<>();

        try {
            String folderPath = String.format("%s", this.filesDirectoryPath);

            File folder = new File(folderPath);

            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();

                if (files != null) {
                    for (File file : files) {

                        if (file.getName() == ".gitkeep") {
                            continue;
                        }

                        String guid = this.extractGuid(file.getName());

                        if (guid == null) {
                            continue;
                        }

                        Order order = this.orderRepository.findFirstByGuid(guid);

                        Map<String, String> object = this.convertToObject(file, order);

                        result.put(String.valueOf(order.getId()), object);
                    }
                } else {
                    this.logger.info("Folder is empty");
                }
            } else {
                throw new Exception("Path is not define or folder doesn't exist");
            }
        } catch (Throwable e) {
            logger.error(e.toString());
        }

        return result;
    }

    private Map<String, String> convertToObject(File resource, Order order) throws IOException {
        Map<String, String> object = new HashMap<>();
        object.put("id", String.valueOf(order.getId()));
        object.put("datetime", String.valueOf(new Date(order.getTimestamp())));
        object.put("filename", resource.getName());
        object.put("path",
                String.format(
                        "%s://%s:%s/api/v1/files/excel/%s",
                        "http",
                        this.serverAddress,
                        this.serverPort,
                        order.getGuid()
                )
        );
        return object;
    }

    public void createExcelDocument(String guid) {
        this.xlsxDocumentService.createXlsxDocumentByOrderId(guid);
    }

    private String extractGuid(String name) {
        Pattern pattern = Pattern.compile("(guid-\\d+)\\.xlsx");
        Matcher matcher = pattern.matcher(name);


        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            return null;
        }
    }

    public File getFileByGuid(String guid) {
        String filePath = String.format("%s/%s.xlsx", this.filesDirectoryPath, guid);
        File file = new File(filePath);
        if (file.exists()) {
            return file;
        }
        return null;
    }
}
