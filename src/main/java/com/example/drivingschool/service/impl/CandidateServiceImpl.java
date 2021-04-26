package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.Candidate;
import com.example.drivingschool.repository.CandidateRepository;
import com.example.drivingschool.service.CandidateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;


    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public List<Candidate> getAll() {
        return candidateRepository.findAll();
    }

    @Override
    public Candidate getById(Long candidateId) {
        return candidateRepository.findById(candidateId).orElseThrow();
    }

    @Override
    public List<Candidate> getAllWithoutActiveTraining() {
        return getAll(); // for now
    }

}
