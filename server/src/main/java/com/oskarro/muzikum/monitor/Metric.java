package com.oskarro.muzikum.monitor;

import com.oskarro.muzikum.utils.DateAudit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Metric extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String STATUS_200_OK;
    private String STATUS_201_CREATED;
    private String STATUS_202_ACCEPTED;
    private String STATUS_204_NO_CONTENT;
    private String STATUS_205_RESET_CONTENT;

    private String STATUS_OTHER;

}
