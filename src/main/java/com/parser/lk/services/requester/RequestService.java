package com.parser.lk.services.requester;

import com.parser.lk.services.requester.dto.PokemonResponse;
import com.parser.lk.services.requester.headhunteradapter.HeadHunterAdapterService;
import com.parser.lk.services.requester.headhunteradapter.dto.VacanciesResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RequestService {

    private final HeadHunterAdapterService headHunterAdapterService;
    private final RestTemplate restTemplate;

    public RequestService(HeadHunterAdapterService headHunterAdapterService) {
        this.headHunterAdapterService = headHunterAdapterService;
        this.restTemplate = new RestTemplate();
    }


    public PokemonResponse getPokemon() {
        RestTemplate restTemplate = new RestTemplate();
        String requestRoute = "https://pokeapi.co/api/v2/pokemon/pikachu";
        ResponseEntity<PokemonResponse> response = restTemplate.getForEntity(requestRoute, PokemonResponse.class);
        return response.getBody();
    }



}
