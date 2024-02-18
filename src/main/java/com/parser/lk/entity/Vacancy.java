package com.parser.lk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Clob;

@Entity
@Setter
@Getter
public class Vacancy {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String originalUrl;

    @Column(length = 2500)
    private String name;

    private String grade;

    @Column(length = 65536)
    private String functionalDescription;

    @Column(length = 65536)
    private String vacancyDescription;

    private String experience;

    private String schedule;

    private String employment;

    private String area;

    /** Ид вакансиии с ХХ */
    private String externalId;

    /** Ид от Димы Старцева */
    private String guid;

    private boolean processed = false;

    private Integer salaryFrom;

    private Integer salaryTo;

    private Boolean salaryGross;

    private String currency;

}
