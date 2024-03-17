package com.parser.lk.services.calculationservice.mathematics.method;

import com.parser.lk.entity.CalculationResults;
import com.parser.lk.entity.Order;
import com.parser.lk.entity.Vacancy;
import com.parser.lk.repository.CalculationResultsRepository;
import com.parser.lk.repository.OrderRepository;
import com.parser.lk.repository.VacancyRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public String getAlias() {
        return "AVERAGE";
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void calculate(Long orderId) {
        Optional<Order> orderOptional = this.orderRepository.findOneById(orderId);
        if (orderOptional.isEmpty()) {
            CalculationResults result = new CalculationResults();
            result.setResult("ERROR");
            result.setGuid(null);
            result.setFormulaId(this.getId());
            result.setOrderId(orderId);
            result.setFormulaAlias(this.getAlias());
            result.setCreateDate(new Date());
            this.calculationResultsRepository.save(result);
            return;
        }
        Order order = orderOptional.get();
        int count = 0;
        long value = 0;

        for (Vacancy vacancy : this.vacancyRepository.findAllByGuid(order.getGuid())) {
            count++;

            if (vacancy.getSalaryTo() > 0) {
                value += vacancy.getSalaryTo();
            } else if (vacancy.getSalaryFrom() > 0) {
                value += vacancy.getSalaryFrom();
            } else {
                continue;
            }
        }

        double answer = (double) value / (double) count;


        CalculationResults result = new CalculationResults();
        result.setResult(Double.toString(answer));
        result.setGuid(order.getGuid());
        result.setFormulaId(this.getId());
        result.setOrderId(orderId);
        result.setFormulaAlias(this.getAlias());
        result.setCreateDate(new Date());

        this.calculationResultsRepository.save(result);
        return;
    }

}
