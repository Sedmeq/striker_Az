package org.example.striker_az.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "teams")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference
    private Set<User> players = new HashSet<>();

    @Column(nullable = false)
    private Integer maxPlayers = 11;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Integer getPlayerCount() {
        return players.size();
    }

    public Double getAverageAge() {
        return players.stream()
                .filter(p -> p.getAge() != null)
                .mapToInt(User::getAge)
                .average()
                .orElse(0.0);
    }

    public Integer getAvailableSlots() {
        return maxPlayers - players.size();
    }
}