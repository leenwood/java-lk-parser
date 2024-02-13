package com.parser.lk.services.applicationservice.applicationstatus;

import com.parser.lk.entity.Order;
import com.parser.lk.entity.Vacancy;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.repository.VacancyRepository;
import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.applicationservice.StatusInterface;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ParseStatusService")
public class ParseService implements StatusInterface {

    private final VacanciesParser vacanciesParser;

    private final OrderRepository orderRepository;

    private final VacancyRepository vacancyRepository;

    private final Logger logger = LoggerFactory.getLogger(ParseService.class);

    @Value("${application.period.parse.vacancies}")
    private Integer period;


    @Autowired
    public ParseService(
            VacanciesParser vacanciesParser,
            OrderRepository orderRepository, VacancyRepository vacancyRepository
    ) {
        this.vacanciesParser = vacanciesParser;
        this.orderRepository = orderRepository;
        this.vacancyRepository = vacancyRepository;
    }


    @Override
    public boolean doProcess(Long orderId) {
        Optional<Order> orderOptional = this.orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return false;
        }
        Order order = orderOptional.get();

        List<Integer> area;
        if (order.isAllRegion()) {
            area = this.getAllRegionsId();
        } else {
            area = order.getRegionId();
        }

        for (Integer cursor : area) {
            List<Integer> areaCursor = new ArrayList<>();
            areaCursor.add(cursor);
            HeadHunterFiltersParam filters = new HeadHunterFiltersParam(
                    order.getSearchText(),
                    order.getHasSalary(),
                    this.period,
                    areaCursor,
                    order.getExperience(),
                    order.getSchedule(),
                    order.getEmployment(),
                    order.getVacancySearchFields(),
                    order.getIndustries()
            );
            int pages = this.vacanciesParser.getPagesVacanciesByFilter(filters);
            System.out.printf("Количество страниц %s%n", pages);
            parseVacancies(filters, pages, order.getGuid());
        }

        return true;
    }

    private void parseVacancies(HeadHunterFiltersParam filters, int pages, String guid) {
        for (int currentPage = filters.getPage(); currentPage < pages; currentPage++) {
            filters.setPage(currentPage);
            Vacancies vacancies = this.vacanciesParser.ParseHeadHunterVacancies(filters);
            System.out.printf("Текущая страница %s | Всего вакансий найдено %s%n", currentPage, vacancies.getFound());
            for (com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancy vacancy : vacancies.getVacancies()) {
                Vacancy transformVacancy = this.transformToVacancy(vacancy);
                transformVacancy.setGuid(guid);
                this.vacancyRepository.save(transformVacancy);

                if (transformVacancy.getId() < 0) {
                    this.logger.error(String.format(
                            "ParseStatusService::doProcess | Не удалось сохранить вакансию с externalId %s в БД",
                            transformVacancy.getExternalId())
                    );
                }
            }

        }
    }

    private List<Integer> getAllRegionsId() {
        return this.vacanciesParser.getAreaIdsFromHeadHunter();
    }

    private Vacancy transformToVacancy(com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancy vacancy) {
        Vacancy vacancyTransform = new Vacancy();
        vacancyTransform.setName(vacancy.getName());
        vacancyTransform.setOriginalUrl(vacancy.getOriginUrl());
        vacancyTransform.setArea(vacancy.getArea().getId());
        vacancyTransform.setExperience(vacancy.getExperience().getId());
        vacancyTransform.setExternalId(vacancy.getId());
        vacancyTransform.setEmployment(vacancy.getEmployment().getId());
        vacancyTransform.setSchedule(vacancy.getSchedule().getId());
        return vacancyTransform;
    }

    @Override
    public String getStatusName() {
        return NameStatusServiceEnum.PARSING;
    }

    @Override
    public String getNextStatusName() {
        return NameStatusServiceEnum.POST_PROCESSING;
    }
}
