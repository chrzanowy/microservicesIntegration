package com.integration.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kuba on 2017-05-12.
 */
@Entity
@Table(name = "USERS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")
    private int id;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;

    public User(String email, String city, String state) {
        this.email = email;
        this.city = city;
        this.state = state;
    }
}
