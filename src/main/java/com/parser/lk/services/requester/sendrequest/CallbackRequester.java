package com.parser.lk.services.requester.sendrequest;


import com.parser.lk.services.requester.sendrequest.dto.NotificationBodyRequest;
import com.parser.lk.services.requester.sendrequest.dto.RequestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CallbackRequester {

    private final RestTemplate restTemplate;

    public CallbackRequester() {
        this.restTemplate = new RestTemplate();
    }


    public boolean sendCallback(String callbackUrl, NotificationBodyRequest message) {
        ResponseEntity<RequestResponse> response = this.restTemplate.postForEntity(
                callbackUrl,
                message,
                RequestResponse.class
        );
        var bodyResponse = response.getBody();
        if (bodyResponse == null) {
            return false;
        }

        return bodyResponse.isSuccess();
    }
}
