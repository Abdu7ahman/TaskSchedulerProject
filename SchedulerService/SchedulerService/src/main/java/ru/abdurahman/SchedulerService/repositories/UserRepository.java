package ru.abdurahman.SchedulerService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.abdurahman.SchedulerService.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
