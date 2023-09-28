package com.application.api.repository;



import com.application.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findByUserId(User userId);

    User findByEmail(String email);

    User findByUsername(String username);
}