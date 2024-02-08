package com.parser.lk.services.vacanciesparser;

import com.parser.lk.services.requester.headhunteradapter.HeadHunterRequester;
import com.parser.lk.services.requester.headhunteradapter.dto.AreaResponse;
import com.parser.lk.services.requester.headhunteradapter.dto.VacanciesResponse;
import com.parser.lk.services.requester.headhunteradapter.dto.VacancyResponse;
import com.parser.lk.services.vacanciesparser.dto.HeadHunterFiltersParam;
import com.parser.lk.services.vacanciesparser.dto.vacancies.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VacanciesParser {

    private final HeadHunterRequester headHunterRequester;

    @Autowired
    public VacanciesParser(HeadHunterRequester headHunterRequester) {
        this.headHunterRequester = headHunterRequester;
    }

    public Vacancies ParseHeadHunterVacancies(HeadHunterFiltersParam filter) {
        VacanciesResponse vacancies = this.headHunterRequester.getVacancies(filter);
        return this.transformToVacancies(vacancies);
    }

    private Vacancies transformToVacancies(VacanciesResponse vacanciesResponse) {
        Vacancies vacancies = new Vacancies();
        vacancies.setPerPage(vacanciesResponse.getPerPage());
        vacancies.setPage(vacanciesResponse.getPage());
        vacancies.setFound(vacanciesResponse.getFound());
        vacancies.setPages(vacanciesResponse.getPages());
        List<Vacancy> vacanciesList = new ArrayList<>();

        for (VacancyResponse vacancyResponse : vacanciesResponse.getItems()) {
            Vacancy vacancy = new Vacancy();
            vacancy.setOriginUrl(vacancyResponse.getOriginUrl());
            vacancy.setName(vacancyResponse.getName());
            vacancy.setId(vacancyResponse.getId());

            Area area = new Area();
            area.setId(vacancyResponse.getArea().getId());
            area.setName(vacancyResponse.getArea().getName());
            vacancy.setArea(area);

            Salary salary;
            if (vacancyResponse.getSalary() != null) {
                salary = new Salary();
                salary.setCurrency(vacancyResponse.getSalary().getCurrency());
                salary.setSalaryGross(vacancyResponse.getSalary().isSalaryGross());
                salary.setSalaryFrom(vacancyResponse.getSalary().getSalaryFrom());
                salary.setSalaryTo(vacancyResponse.getSalary().getSalaryTo());
            } else {
                salary = null;
            }
            vacancy.setSalary(salary);

            Employment employment;
            if (vacancyResponse.getEmployment() != null) {
                employment = new Employment();
                employment.setName(vacancyResponse.getEmployment().getName());
                employment.setId(vacancyResponse.getEmployment().getId());
            } else {
                employment = null;
            }
            vacancy.setEmployment(employment);


            Experience experience;
            if (vacancyResponse.getExperience() != null) {
                experience = new Experience();
                experience.setId(vacancyResponse.getExperience().getId());
                experience.setName(vacancyResponse.getExperience().getName());
            } else {
                experience = null;
            }
            vacancy.setExperience(experience);

            Schedule schedule;
            if (vacancyResponse.getSchedule() != null) {
                schedule = new Schedule();
                schedule.setId(vacancyResponse.getSchedule().getId());
                schedule.setName(vacancyResponse.getSchedule().getName());
            } else {
                schedule = null;
            }
            vacancy.setSchedule(schedule);

            vacanciesList.add(vacancy);
        }

        vacancies.setVacancies(vacanciesList);

        return vacancies;
    }

    public List<Integer> getAreaIdsFromHeadHunter() {
        AreaResponse areaResponse = this.headHunterRequester.getAreasByDictionary();
        List<Integer> areas = new ArrayList<>();
        for (AreaResponse area : areaResponse.getAreas()) {
            areas.add(Integer.parseInt(area.getId()));
        }

        return areas;
    }

    public int getPagesVacanciesByFilter(HeadHunterFiltersParam filtersParam) {
        return this.headHunterRequester.getPagesVacanciesByFilter(filtersParam);
    }

}
