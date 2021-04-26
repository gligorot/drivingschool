package com.example.drivingschool.service.impl;

import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.interstatetransitions.*;
import com.example.drivingschool.repository.interstatetransition.*;
import com.example.drivingschool.service.InterStateTransitionService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class InterStateTransitionServiceImpl implements InterStateTransitionService {

    private final TheoryInterStateTransitionRepository theoryRepository;
    private final PracticeInterStateTransitionRepository practiceRepository;
    private final TheoryTestInterStateTransitionRepository theoryTestRepository;
    private final RangeTestInterStateTransitionRepository rangeTestRepository;
    private final CityTestInterStateTransitionRepository cityTestRepository;

    public InterStateTransitionServiceImpl(TheoryInterStateTransitionRepository theoryRepository, PracticeInterStateTransitionRepository practiceRepository, TheoryTestInterStateTransitionRepository theoryTestRepository, RangeTestInterStateTransitionRepository rangeTestRepository, CityTestInterStateTransitionRepository cityTestRepository) {
        this.theoryRepository = theoryRepository;
        this.practiceRepository = practiceRepository;
        this.theoryTestRepository = theoryTestRepository;
        this.rangeTestRepository = rangeTestRepository;
        this.cityTestRepository = cityTestRepository;
    }

    @Override
    public InterStateTransition createAndSaveInterStateTransitionFromRequest(Request request) {
        // smelly code, but it has to exist go somewhere
        switch(request.getType()) {
            case THEORY:
                TheoryInterStateTransition tist = new TheoryInterStateTransition(request);
                return theoryRepository.save(tist);
            case PRACTICE:
                PracticeInterStateTransition pist = new PracticeInterStateTransition(request);
                return practiceRepository.save(pist);
            case THEORY_TEST:
                TheoryTestInterStateTransition ttist = new TheoryTestInterStateTransition(request);
                return theoryTestRepository.save(ttist);
            case RANGE_TEST:
                RangeTestInterStateTransition rtist = new RangeTestInterStateTransition(request);
                return rangeTestRepository.save(rtist);
            case CITY_TEST:
                CityTestInterStateTransition ctist = new CityTestInterStateTransition(request);
                return cityTestRepository.save(ctist);
            default:
                throw new Error("No request type defined");
        }
    }
//
//    @Override
//    public InterStateTransition getInterStateTransitionFromRequest(Request request) {
//
//        InterStateTransition ist;
//        // smelly code, but it has to exist go somewhere
//        switch(request.getType()) {
//            case THEORY:
//                ist = new TheoryInterStateTransition(request); break;
//            case PRACTICE:
//                ist = new PracticeInterStateTransition(request); break;
//            case THEORY_TEST:
//                ist = new TheoryTestInterStateTransition(request); break;
//            case RANGE_TEST:
//                ist = new RangeTestInterStateTransition(request); break;
//            case CITY_TEST:
//                ist = new CityTestInterStateTransition(request); break;
//            default:
//                throw new Error("No request type defined");
//        }
//
//        return ist;
//    }
//
//    @Override
//    public InterStateTransition saveInterStateTransition(InterStateTransition transition) {
//        return istRepository.save(transition);
//    }
}
