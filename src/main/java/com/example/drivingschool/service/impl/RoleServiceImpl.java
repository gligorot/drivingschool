package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.Candidate;
import com.example.drivingschool.model.Instructor;
import com.example.drivingschool.model.User;
import com.example.drivingschool.repository.CandidateRepository;
import com.example.drivingschool.repository.InstructorRepository;
import com.example.drivingschool.repository.RoleRepository;
import com.example.drivingschool.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final InstructorRepository instructorRepostory;
    private final CandidateRepository candidateRepository;

    public RoleServiceImpl(RoleRepository roleRepository, InstructorRepository instructorRepostory, CandidateRepository candidateRepository) {
        this.roleRepository = roleRepository;
        this.instructorRepostory = instructorRepostory;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public Instructor createInstructorTableEntryForUser(User user) {

        Instructor instructor = new Instructor(user);
        return instructorRepostory.save(instructor);
    }

    @Override
    public Candidate createCandidateTableEntryForUser(User user) {

        Candidate candidate = new Candidate(user);
        return candidateRepository.save(candidate);
    }

    @Override
    public Instructor getInstructorForUser(User user) {
        return instructorRepostory.getInstructorByUser(user);
    }

    @Override
    public Candidate getCandidateForUser(User user) {
        return candidateRepository.getCandidateByUser(user);
    }
}
