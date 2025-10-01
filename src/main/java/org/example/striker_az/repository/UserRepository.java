package org.example.striker_az.repository;

import org.example.striker_az.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByStrikerazId(String strikerazId);
    boolean existsByEmail(String email);

    @Query("SELECT MAX(u.id) FROM User u")
    Long findMaxId();
}