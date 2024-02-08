package com.parser.lk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Entity
@Setter
@Getter
public class Vacancy {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String originalUrl;

    private String name;

    private String grade;

    private String functionalDescription;

    private String vacancyDescription;

    private String experience;

    private String schedule;

    private String employment;

    private String area;

    private String externalId;

}
