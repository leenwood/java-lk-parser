package com.parser.lk.controller;


import com.parser.lk.dto.response.analytics.SuccessResponse;
import com.parser.lk.services.documentmanager.FilesManager;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(("/api/v1/files"))
public class FilesRestController {

    private final FilesManager filesManager;

    public FilesRestController(FilesManager filesManager) {
        this.filesManager = filesManager;
    }

    @PatchMapping("/excel")
    public SuccessResponse createExcelFile(
            @RequestParam(value = "orderId") Long id
    ) {
        this.filesManager.createExcelDocument(id);
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus(true);
        return successResponse;
    }


    @GetMapping("/excel")
    public Map<String, Map<String, String>> getExcelDocumentsList() {
        var list = this.filesManager.getDocumentsList();
        return list;
    }


}




