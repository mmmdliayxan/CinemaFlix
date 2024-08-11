package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {
    boolean existsByEmail(String email);
    Customer findByEmail(String email);

}
