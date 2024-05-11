package com.parser.lk.services.requester.chatgpt;

import com.parser.lk.services.requester.chatgpt.dto.ChatGPTRequest;
import com.parser.lk.services.requester.chatgpt.dto.ChatGPTResponse;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Service(name = "ChatGPTRequester")
public class ChatGPTRequester {

    @Value("${application.chatgpt.url}")
    private String chatGPTUrl;

    private final RestTemplate restTemplate;

    public ChatGPTRequester() {
        this.restTemplate = new RestTemplate();
    }


    public ChatGPTResponse sendRequest(
            String description,
            String prompt
    ) {
        ChatGPTRequest chatGPTRequest = new ChatGPTRequest(String.format("%s \n %s", prompt, description));
        ResponseEntity<ChatGPTResponse> response = this.restTemplate.postForEntity(
                this.chatGPTUrl,
                chatGPTRequest,
                ChatGPTResponse.class
        );

        return response.getBody();
    }

}
