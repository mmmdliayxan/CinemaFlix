package org.example.cinemaflix.dao.entity.repository;

import jakarta.transaction.Transactional;
import org.example.cinemaflix.dao.entity.Customer;
import org.example.cinemaflix.dao.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @EntityGraph(attributePaths = {"authorities"})
    Optional<User> findByUsername(String username);

    Optional<User> findByCustomer(Customer customer);

    @Transactional
    @Modifying
    @Query("update User u set u.password=?1 where u.customer.email=?2")
    void updatePassword(String password,String email);

}
