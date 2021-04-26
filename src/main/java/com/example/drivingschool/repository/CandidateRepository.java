package com.example.drivingschool.repository;

import com.example.drivingschool.model.Candidate;
import com.example.drivingschool.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {

    // should behave similarly to getOne(user.id);
    Candidate getCandidateByUser(User user);
}
