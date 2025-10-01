package org.example.striker_az.repository;

import org.example.striker_az.entity.Team;
import org.example.striker_az.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
    boolean existsByName(String name);
    List<Team> findByNameContainingIgnoreCase(String name);
    List<Team> findByOrderByCreatedAtDesc();
    List<Team> findByOwner(User owner);
}