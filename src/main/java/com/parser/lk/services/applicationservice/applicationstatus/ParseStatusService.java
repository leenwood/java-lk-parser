package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.entity.Order;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.services.applicationservice.StatusInterface;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("ParseStatusService")
public class ParseStatusService implements StatusInterface {

    private final VacanciesParser vacanciesParser;

    private final OrderRepository orderRepository;


    private final String statusName = "PARSING";

    private final String nextStatus = "POST_PROCESSING";


    @Autowired
    public ParseStatusService(
            VacanciesParser vacanciesParser,
            OrderRepository orderRepository
    ) {
        this.vacanciesParser = vacanciesParser;
        this.orderRepository = orderRepository;
    }


    @Override
    public boolean doProcess(Long orderId) {
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return false;
        }
        Order order = orderOptional.get();


        String area = null;
        if (order.isAllRegion()) {
            //TODO
        } else {
//            area = order.getRegionId();
        }

        HeadHunterFiltersParam filters = new HeadHunterFiltersParam(
                order.getSearchText(),
                order.getHasSalary(),
                14,
                area,
                order.getExperience()
        );

        Vacancies vacancies = this.vacanciesParser.ParseHeadHunterVacancies(filters);

        return false;
    }

    private List<Integer> getAllRegionsId() {
        return this.vacanciesParser.getAreaIdsFromHeadHunter();
    }

    @Override
    public String getStatusName() {
        return this.statusName;
    }

    @Override
    public String getNextStatusName() {
        return this.nextStatus;
    }
}
