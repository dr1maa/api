package com.application.api.repository;


import com.application.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByUsername(String username);
    @Query("SELECT u FROM User u WHERE u.id = :id")
    User customFindById(@Param("id") Long id);
}