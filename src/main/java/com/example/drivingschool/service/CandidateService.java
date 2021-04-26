package com.example.drivingschool.service;

import com.example.drivingschool.model.Candidate;

import java.util.List;

public interface CandidateService {
    List<Candidate> getAllWithoutActiveTraining();

    List<Candidate> getAll();

    Candidate getById(Long candidateId);
}
