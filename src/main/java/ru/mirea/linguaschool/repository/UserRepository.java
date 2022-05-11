package ru.mirea.linguaschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.linguaschool.model.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
