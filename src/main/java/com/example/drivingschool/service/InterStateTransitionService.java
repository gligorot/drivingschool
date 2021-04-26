package com.example.drivingschool.service;

import com.example.drivingschool.model.Request;
import com.example.drivingschool.model.interstatetransitions.InterStateTransition;

public interface InterStateTransitionService {

    InterStateTransition createAndSaveInterStateTransitionFromRequest(Request request);

//    InterStateTransition getInterStateTransitionFromRequest(Request request);
//
//    InterStateTransition saveInterStateTransition(InterStateTransition transition);
}
