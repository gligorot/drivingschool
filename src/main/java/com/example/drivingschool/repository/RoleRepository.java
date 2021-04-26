package com.example.drivingschool.repository;

import com.example.drivingschool.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByNameEquals(String name);
}
