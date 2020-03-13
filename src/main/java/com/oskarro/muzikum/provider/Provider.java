package com.oskarro.muzikum.provider;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Provider {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String url;

    private String description;
}
