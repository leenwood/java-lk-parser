package com.parser.lk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "parser_order")
public class Order {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String status;

    private String searchText;

    private int timestamp;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> regionId;

    private boolean allRegion = false;

    private Boolean hasVmi;

    private Boolean hasSalary;

    private String experience;

    private String externalId;

}
