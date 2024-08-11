package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.ForgotPassword;
import org.example.cinemaflix.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword,Integer> {
    Optional<ForgotPassword> findByTokenAndUser(String token, User user);

}
