package com.example.drivingschool.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table
public class Candidate {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Column(name="user_id", insertable = false, updatable = false)
    private Long id; // don't bother with getter/setter since the `user` reference handles everything

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // TODO temporary, to test stack overflow error on getting candidate
    // although it's lazily loaded?
//    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
//    private Set<Training> trainings = new HashSet<>();

    public Candidate() {

    }

    public Candidate(User user) {
        this.id = user.getId();
        this.user = user;
    }
}
