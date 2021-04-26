package com.example.drivingschool.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class Instructor {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;

    @Id
    @Column(name="user_id", insertable = false, updatable = false)
    private Long id; // don't bother with getter/setter since the `user` reference handles everything

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
//    private Set<Training> trainings = new HashSet<>();

    public Instructor() {

    }

    public Instructor(User user) {
        this.user = user;
        this.id = user.getId();

// apparently not needed
// check the comments here (tl;dr needed if not int/long)
// https://stackoverflow.com/questions/44786137/jpa-2-how-to-build-entity-that-has-primary-key-that-is-also-a-foreign-key-usin
//        this.id = user.getId();
    }
}
