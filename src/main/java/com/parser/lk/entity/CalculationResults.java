package com.parser.lk.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class CalculationResults {


    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long orderId;

    private String guid;

    private String formulaAlias;

    private Long formulaId;

    private String result;

    private Date createDate;

}
