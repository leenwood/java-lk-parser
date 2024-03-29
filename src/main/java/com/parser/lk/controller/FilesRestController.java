package com.parser.lk.controller;


import com.parser.lk.dto.response.analytics.DocumentExcelListResponse;
import com.parser.lk.dto.response.analytics.SuccessResponse;
import com.parser.lk.services.documentmanager.FilesManager;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Map;

@RestController
@RequestMapping(("/api/v1/files"))
public class FilesRestController {

    private final FilesManager filesManager;

    public FilesRestController(FilesManager filesManager) {
        this.filesManager = filesManager;
    }

    @PostMapping("/excel")
    public SuccessResponse createExcelFile(
            @RequestParam(value = "guid") String guid
    ) {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setStatus(this.filesManager.startCreateExcelDocumentByGuid(guid));
        return successResponse;
    }


    @GetMapping("/excel")
    public DocumentExcelListResponse getExcelDocumentsList() {
        return this.filesManager.getDocumentListV2();
    }


    @GetMapping("/excel/{guid}")
    public ResponseEntity<Resource> downloadFileByGuid(
            @PathVariable("guid") String guid
    ) {
        File file = this.filesManager.getFileByGuid(guid);

        if (file == null) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new FileSystemResource(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }


}




