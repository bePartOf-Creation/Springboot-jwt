package com.demo.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;

@Entity @Table @Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String userName;

    @Size(max = 8)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Role> role = new ArrayList<>();

}
