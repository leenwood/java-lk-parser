package com.parser.lk.services.documentmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FilesManager {


    @Value("${application.fileoutput.path}")
    private String filesPath;

    private final Logger logger = LoggerFactory.getLogger(FilesManager.class);

    private final XlsxDocumentService xlsxDocumentService;

    public FilesManager(XlsxDocumentService xlsxDocumentService) {
        this.xlsxDocumentService = xlsxDocumentService;
    }


    public Map<String, Map<String, String>> getDocumentsList() {
        logger.info(String.format("classpath:/%s/*", this.filesPath));
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources(String.format("classpath:/%s/*", this.filesPath));

            for (Resource resource : resources) {
                System.out.println("File: " + resource.getFilename());
            }
        } catch (IOException e) {
            logger.error(e.toString());
        }
        Map<String, Map<String, String>> map = new HashMap<>();
        Map<String, String> map2 = new HashMap<>();
        map2.put("url", "source://files");
        map.put("file1", map2);
        return map;
    }

    public void createExcelDocument(Long id) {
        this.xlsxDocumentService.createXlsxDocumentByOrderId(id);
    }

}
