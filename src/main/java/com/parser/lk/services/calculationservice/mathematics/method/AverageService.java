package com.parser.lk.services.calculationservice.mathematics.method;

import com.parser.lk.entity.CalculationResults;
import com.parser.lk.entity.Order;
import com.parser.lk.entity.Vacancy;
import com.parser.lk.repository.CalculationResultsRepository;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.repository.VacancyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AverageService implements MathematicsMethodInterface {

    private final OrderRepository orderRepository;

    private final CalculationResultsRepository calculationResultsRepository;

    private final VacancyRepository vacancyRepository;

    public AverageService(
            OrderRepository orderRepository,
            CalculationResultsRepository calculationResultsRepository,
            VacancyRepository vacancyRepository
    ) {
        this.orderRepository = orderRepository;
        this.calculationResultsRepository = calculationResultsRepository;
        this.vacancyRepository = vacancyRepository;
    }

    public String getAlias() {
        return "AVERAGE";
    }

    public Long getId() {
        return null;
    }

    public void calculate(Long orderId) {
        Optional<Order> orderOptional = this.orderRepository.findOneById(orderId);
        if (orderOptional.isEmpty()) {
            CalculationResults result = new CalculationResults();
            result.setResult("ERROR");
            result.setGuid(null);
            result.setFormulaId(this.getId());
            result.setOrderId(orderId);
            result.setFormulaAlias(this.getAlias());
        }
        Order order = orderOptional.get();

        String guid = order.getGuid();

        for (Vacancy vacancy : this.vacancyRepository.findAll()) {

        }
    }

}
