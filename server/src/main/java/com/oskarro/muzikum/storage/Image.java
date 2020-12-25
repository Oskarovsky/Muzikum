package com.oskarro.muzikum.storage;

import com.oskarro.muzikum.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private String type;

    private String destination;

    @Lob
    private byte[] pic;

    @OneToOne(optional = true, fetch = FetchType.LAZY)
    @MapsId
    private User user;
}
