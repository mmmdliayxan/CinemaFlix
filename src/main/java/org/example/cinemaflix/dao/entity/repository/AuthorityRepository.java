package org.example.cinemaflix.dao.entity.repository;

import org.example.cinemaflix.dao.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority,String> {
    Authority findByAuthority(String name);
}
