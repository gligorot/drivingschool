package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.TrainingCategory;
import com.example.drivingschool.model.enumerations.TrainingCategoryName;
import com.example.drivingschool.repository.TrainingCategoryRepository;
import com.example.drivingschool.service.TrainingCategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingCategoryServiceImpl implements TrainingCategoryService {

    private final TrainingCategoryRepository trainingCategoryRepository;

    public TrainingCategoryServiceImpl(TrainingCategoryRepository trainingCategoryRepository) {
        this.trainingCategoryRepository = trainingCategoryRepository;
    }

    @Override
    public TrainingCategory createNew(TrainingCategoryName name, Integer requiredTheoryTrainingDays, Integer requiredPracticeTrainingDays) {
        TrainingCategory trainingCategory = new TrainingCategory(requiredTheoryTrainingDays, requiredPracticeTrainingDays, name);
        return trainingCategoryRepository.save(trainingCategory);
    }

    @Override
    public TrainingCategory getByName(TrainingCategoryName name) {
        return trainingCategoryRepository.findByName(name);
    }

    @Override
    public List<TrainingCategory> getAll() {
        return trainingCategoryRepository.findAll();
    }
}
