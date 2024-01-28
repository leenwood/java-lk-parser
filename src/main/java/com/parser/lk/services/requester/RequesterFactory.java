package com.parser.lk.services.requester;


import com.parser.lk.services.requester.headhunteradapter.HeadHunterRequester;
import org.springframework.stereotype.Service;

@Service
public class RequesterFactory {

    public RequesterInterface getRequester(RequesterEnum requesterType) {
        if (requesterType == RequesterEnum.HEAD_HUNTER_REQUESTER) {

            return new HeadHunterRequester();
        }
        return null;
    }
}
