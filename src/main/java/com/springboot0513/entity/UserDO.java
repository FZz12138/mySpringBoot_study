package com.springboot0513.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data
public class UserDO {
    @SequenceGenerator(sequenceName = "seq_user",name="seq",initialValue=10000000,allocationSize = 50)
    @GeneratedValue(generator = "seq")
    @Id
    @Column(updatable = false)
    private  Long id;

    @Column(length = 11)
    private String username;

    @Column(length = 11)
    private String password;
}
