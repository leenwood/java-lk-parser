package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.entity.Order;
import com.parser.lk.entity.Vacancy;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.repository.VacancyRepository;
import com.parser.lk.services.aiservice.AiService;
import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service("PostProcessingStatusService")
public class PostProcessingService implements StatusInterface {

    private final VacancyRepository vacancyRepository;

    private final OrderRepository orderRepository;

    private final VacanciesParser vacanciesParser;

    private final AiService aiService;


    @Autowired
    public PostProcessingService(
            VacancyRepository vacancyRepository,
            OrderRepository orderRepository,
            VacanciesParser vacanciesParser,
            AiService aiService
    ) {
        this.vacancyRepository = vacancyRepository;
        this.orderRepository = orderRepository;
        this.vacanciesParser = vacanciesParser;
        this.aiService = aiService;
    }


    @Override
    public boolean doProcess(Long orderId) {
        System.out.println("PostProcessing");

        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return false;
        }
        Order order = orderOptional.get();
        Integer count = this.vacancyRepository.countByGuidAndProcessed(order.getExternalId(), false);
        while (count > 0) {
            count--;
            Vacancy vacancy = this.vacancyRepository.findOneByGuidAndProcessed(order.getExternalId(), false);
            com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancy vacancyResponse;
            vacancyResponse = this.vacanciesParser.getVacancyById(vacancy.getExternalId());
            vacancy.setVacancyDescription(vacancyResponse.getDescription());


            vacancy.setFunctionalDescription(this.aiService.extractDescription(vacancyResponse.getDescription()));
            vacancy.setGrade(this.aiService.extractGrade(vacancyResponse.getName()));
        }


        return true;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.POST_PROCESSING;
    }

    @Override
    public String getNextStatusName() {
        return NameStatusServiceEnum.MATHEMATICS;
    }
}
