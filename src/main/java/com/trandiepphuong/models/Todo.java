package com.trandiepphuong.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name = "content")
    private String content;
}
