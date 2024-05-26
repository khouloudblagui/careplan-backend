package com.example.careplane.repository;

import com.example.careplane.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.password = ?2 where u.email = ?1 ")
    void updatePassword(String email, String password);

}
