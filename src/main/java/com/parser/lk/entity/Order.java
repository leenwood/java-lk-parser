package com.parser.lk.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

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

    private String regionId;

    private boolean allRegion;

    private boolean hasVmi;

    private boolean hasSalary;

    private String externalId;

}
