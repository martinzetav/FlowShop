package com.microservice.auth.repository;

import com.microservice.auth.model.Role;
import com.microservice.auth.model.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleEnum roleName);
}
