package ru.abdur.SecurityApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdur.SecurityApp.models.User;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
