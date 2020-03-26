package com.oskarro.muzikum.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Provider {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String url;

    private String description;
}
