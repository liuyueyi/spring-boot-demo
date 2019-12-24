package com.git.hui.boot.security.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by @author yihui in 15:43 19/12/24.
 */
@Table(name = "user_info")
@Entity
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String user;

    @Column
    private String password;

    @Column
    private String role;

}
