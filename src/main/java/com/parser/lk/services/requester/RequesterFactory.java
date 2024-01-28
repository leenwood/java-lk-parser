package com.parser.lk.services.requester;


import com.parser.lk.services.requester.headhunteradapter.HeadHunterRequester;
import org.apache.naming.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class RequesterFactory {

    //TODO когда будет возможность переписать на autowired и получать из context
    private final HeadHunterRequester headHunterRequester;


    @Autowired
    public RequesterFactory(HeadHunterRequester headHunterRequester) {
        this.headHunterRequester = headHunterRequester;
    }

    public RequesterInterface getRequester(RequesterEnum requesterType) {

        if (requesterType == RequesterEnum.HEAD_HUNTER_REQUESTER) {

            return this.headHunterRequester;

        }
        return null;
    }
}
