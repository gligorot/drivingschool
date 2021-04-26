package com.example.drivingschool.repository;

import com.example.drivingschool.model.Instructor;
import com.example.drivingschool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    // should behave similarly to getOne(user.id);
    Instructor getInstructorByUser(User user);
}
