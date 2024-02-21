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
    private List<Integer> regionId;

    private boolean allRegion = false;

    private Boolean hasVmi;

    private Boolean hasSalary;

    private String experience;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> industries;

    /** График */
    private String schedule;

    /** Тип занятонсти */
    private String employment;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> vacancySearchFields;

    /** Ид приходяший от димы, именуется как GUID */
    @Column(unique = true)
    private String guid;

}
