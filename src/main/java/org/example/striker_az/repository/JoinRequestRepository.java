package org.example.striker_az.repository;

import org.example.striker_az.entity.JoinRequest;
import org.example.striker_az.entity.*;
import org.example.striker_az.entity.Team;
import org.example.striker_az.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JoinRequestRepository extends JpaRepository<JoinRequest, Long> {
    List<JoinRequest> findByPlayerAndStatus(User player, RequestStatus status);
    List<JoinRequest> findByTeamAndStatus(Team team, RequestStatus status);
    List<JoinRequest> findByTeam_Owner(User owner);
    boolean existsByPlayerAndTeamAndStatus(User player, Team team, RequestStatus status);
}