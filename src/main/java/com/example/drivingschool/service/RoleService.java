package com.example.drivingschool.service;

import com.example.drivingschool.model.Candidate;
import com.example.drivingschool.model.Instructor;
import com.example.drivingschool.model.User;

public interface RoleService {

    Instructor createInstructorTableEntryForUser(User user);

    Candidate createCandidateTableEntryForUser(User user);

    Instructor getInstructorForUser(User user);

    Candidate getCandidateForUser(User user);

}
