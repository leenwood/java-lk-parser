package com.parser.lk.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "formula_settings")
public class Formula {
    @Id
    private Long id;

    private String alias;

    @Column(length = 2500)
    private String description;

    @Column(length = 3500)
    private String formula;
}
