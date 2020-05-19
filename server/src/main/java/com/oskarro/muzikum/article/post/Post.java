package com.oskarro.muzikum.article.post;

import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.utils.DateAudit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "posts")
public class Post extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Size(max = 255)
    @Column(unique = true)
    private String title;

    @NotNull
    @Size(max = 1000)
    private String description;

    @NotNull
    @Lob
    private String content;

    @ManyToOne
    private User user;
}
