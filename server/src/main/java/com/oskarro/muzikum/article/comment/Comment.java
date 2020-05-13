package com.oskarro.muzikum.article.comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.oskarro.muzikum.article.post.Post;
import com.oskarro.muzikum.user.User;
import com.oskarro.muzikum.utils.DateAudit;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



@Entity
@Getter
@Setter
@Table(name = "comments")
public class Comment extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Lob
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Post post;

    @ManyToOne
    private User user;
}
