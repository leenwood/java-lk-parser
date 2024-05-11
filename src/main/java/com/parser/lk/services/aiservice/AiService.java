package com.parser.lk.services.aiservice;


import com.parser.lk.entity.Vacancy;
import com.parser.lk.services.requester.chatgpt.ChatGPTRequester;
import com.parser.lk.services.requester.chatgpt.dto.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    @Value("${application.chatgpt.prompt}")
    private String prompt;

    private final ChatGPTRequester chatGPTRequester;

    public AiService(ChatGPTRequester chatGPTRequester) {
        this.chatGPTRequester = chatGPTRequester;
    }


    public ChatGPTResponse getChatGPTResponseByVacancy(Vacancy vacancy) {
        return chatGPTRequester.sendRequest(vacancy.getVacancyDescription(), this.prompt);
    }
}
