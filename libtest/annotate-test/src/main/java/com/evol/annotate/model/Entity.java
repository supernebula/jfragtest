package com.evol.annotate.model;

import java.util.Date;

@Table(name = "entity")
public class Entity {

    @Column("id")
    private int id;

    @Column("title")
    private String title;

    @Column("number")
    private int number;

    @Column("create_time")
    private Date createTime;

}
