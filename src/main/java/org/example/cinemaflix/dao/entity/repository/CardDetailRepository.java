package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.CardDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardDetailRepository extends JpaRepository<CardDetail,Long> {
    CardDetail findByBankAccount(String bankAccount);

    boolean existsByBankAccount(String bankAccount);
}
