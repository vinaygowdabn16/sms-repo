package com.blogapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="title",nullable=false)
    private String title;

    @Column(name="description",nullable = false)
    private String description;

    @Column(name="content",nullable=false)
    private String content;

    @OneToMany(cascade=CascadeType.ALL, mappedBy="post")
    private Set<Comment> comments = new HashSet<>();
}
