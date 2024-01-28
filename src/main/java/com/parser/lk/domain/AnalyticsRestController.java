package com.parser.lk.domain;


import com.parser.lk.models.response.analytics.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/analytics")
public class AnalyticsRestController {

    @GetMapping("/work-test")
    public SuccessResponse serverResponse() {
        SuccessResponse response = new SuccessResponse();
        response.setStatus(true);
        return response;
    }

}
