package com.integration.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Kuba on 2017-05-13.
 */
@Entity
@Table(name = "RSO_CACHE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RsoCache implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UID")
    private int id;
    @Column(name = "latestRsoId", unique = true)
    private Long rsoId;
}
