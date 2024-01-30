package com.parser.lk.services.requester.sendrequest;


import com.parser.lk.services.requester.sendrequest.dto.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallbackRequester {

    private final RestTemplate restTemplate;

    @Autowired
    public CallbackRequester(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public boolean sendCallback(String callbackUrl) {
        ResponseEntity<RequestResponse> response = this.restTemplate.getForEntity(
                callbackUrl,
                RequestResponse.class
        );

        return response.getBody().isSuccess();
    }
}
