package com.parser.lk.services.applicationservice.applicationstatus;


import com.parser.lk.entity.Vacancy;
import com.parser.lk.repository.VacancyRepository;
import com.parser.lk.services.aiservice.AiService;
import com.parser.lk.services.applicationservice.NameStatusServiceEnum;
import com.parser.lk.services.requester.centralbank.CentralBankRequester;
import com.parser.lk.services.vacanciesparser.VacanciesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("PostProcessingStatusService")
public class PostProcessingService extends BaseStatusService implements StatusInterface {

    private final VacancyRepository vacancyRepository;

    private final VacanciesParser vacanciesParser;

    private final AiService aiService;

    private final CentralBankRequester centralBankRequester;


    @Autowired
    public PostProcessingService(
            VacancyRepository vacancyRepository,
            VacanciesParser vacanciesParser,
            AiService aiService,
            CentralBankRequester centralBankRequester
    ) {
        this.vacancyRepository = vacancyRepository;
        this.vacanciesParser = vacanciesParser;
        this.aiService = aiService;
        this.centralBankRequester = centralBankRequester;
    }


    @Override
    public boolean doProcess(Long orderId) {
        if (!this.trySetupOrder(orderId)) {
            return false;
        }


        Integer count = this.vacancyRepository.countByGuidAndProcessed(this.order.getGuid(), false);
        while (count > 0) {
            count--;
            Vacancy vacancy = this.vacancyRepository.findFirstByGuidAndProcessed(this.order.getGuid(), false);
            com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancy vacancyResponse;
            vacancyResponse = this.vacanciesParser.getVacancyById(vacancy.getExternalId());
            vacancy.setVacancyDescription(vacancyResponse.getDescription());

            vacancy.setFunctionalDescription(this.aiService.extractDescription(vacancyResponse.getDescription()));
            vacancy.setGrade(this.aiService.extractGrade(vacancyResponse.getName()));

            if (vacancyResponse.getSalary() != null) {
                try {
                    this.convertSalary(
                            vacancyResponse.getSalary().getCurrency(),
                            vacancy,
                            vacancyResponse
                    );
                } catch (Exception exception) {
                    this.logger.error(exception.toString());
                    this.vacancyRepository.delete(vacancy);
                    continue;
                }

            }

            vacancy.setProcessed(true);
            this.vacancyRepository.save(vacancy);
        }


        return true;
    }

    private void convertSalary(
            String aliasCurrency,
            Vacancy vacancy,
            com.parser.lk.services.vacanciesparser.dto.vacancies.Vacancy vacancyResponse
    ) throws Exception {
        if (aliasCurrency.equals("RUR")) {
            vacancy.setSalaryFrom(vacancyResponse.getSalary().getSalaryFrom());
            vacancy.setSalaryTo(vacancyResponse.getSalary().getSalaryTo());
            vacancy.setSalaryGross(vacancyResponse.getSalary().isSalaryGross());
            vacancy.setCurrency(vacancyResponse.getSalary().getCurrency());
        } else {
            double valute = this.centralBankRequester.getCurrencyByAlias(aliasCurrency);

            int salaryFrom = (int) (vacancyResponse.getSalary().getSalaryFrom() * valute);
            int salaryTo = (int) (vacancyResponse.getSalary().getSalaryTo() * valute);

            vacancy.setSalaryFrom(salaryFrom);
            vacancy.setSalaryTo(salaryTo);
            vacancy.setSalaryGross(vacancyResponse.getSalary().isSalaryGross());
            vacancy.setCurrency(vacancyResponse.getSalary().getCurrency());
        }
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
