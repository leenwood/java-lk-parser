package com.example.demo.services.requester.dto;


import lombok.Data;

@Data
public class PokemonResponse {

    private String name;

    private PokemonAbilityInfoResponse[] abilities;
}
