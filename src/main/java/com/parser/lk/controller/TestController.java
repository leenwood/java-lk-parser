package com.parser.lk.controller;


import com.parser.lk.services.requester.dto.PokemonAbilityInfoResponse;
import com.parser.lk.services.requester.RequestService;
import com.parser.lk.services.requester.headHunterAdapter.dto.VacanciesResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final RequestService requestService;

    public TestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/test", produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        var Pokemon = this.requestService.getPokemon();
        StringBuilder abilitiesNames = new StringBuilder();

        for (PokemonAbilityInfoResponse tmpVar : Pokemon.getAbilities()) {
            abilitiesNames.append(" ").append(tmpVar.getAbility().getName());
        }

        return abilitiesNames.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/vacancies", produces = MediaType.APPLICATION_JSON_VALUE)
    public VacanciesResponse vacancies() {
        VacanciesResponse response = this.requestService.getVacancies();
        return response;
    }
}
