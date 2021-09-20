package com.example.webmvc.registration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u from User as u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT u from User as u WHERE u.username = ?1")
    Optional<User> findByUsername(String username);

    @Query("SELECT u from User as u WHERE u.username = ?1")
    Optional<User> getByUsername(String username);
}
