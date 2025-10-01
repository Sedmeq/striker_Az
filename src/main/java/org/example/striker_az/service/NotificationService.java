package org.example.striker_az.service;


import org.example.striker_az.dto.*;
import org.example.striker_az.entity.*;
import org.example.striker_az.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void notifyTeamOwner(User owner, User player, Team team) {
        Notification notification = new Notification();
        notification.setUser(owner);
        notification.setTitle("Yeni müraciət");
        notification.setMessage(String.format("%s (%s, %d yaş) '%s' komandasına qoşulmaq istəyir",
                player.getName(), player.getPosition(), player.getAge(), team.getName()));
        notificationRepository.save(notification);
    }

    public void notifyPlayerAccepted(User player, Team team) {
        Notification notification = new Notification();
        notification.setUser(player);
        notification.setTitle("Müraciət qəbul edildi!");
        notification.setMessage(String.format("'%s' komandası sizin müraciətinizi qəbul etdi. " +
                "Əlaqə: %s", team.getName(), team.getOwner().getPhone()));
        notificationRepository.save(notification);
    }

    public void notifyPlayerRejected(User player, Team team) {
        Notification notification = new Notification();
        notification.setUser(player);
        notification.setTitle("Müraciət rədd edildi");
        notification.setMessage(String.format("'%s' komandası sizin müraciətinizi rədd etdi",
                team.getName()));
        notificationRepository.save(notification);
    }

    public List<Notification> getUserNotifications(User user) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(user);
    }
}
