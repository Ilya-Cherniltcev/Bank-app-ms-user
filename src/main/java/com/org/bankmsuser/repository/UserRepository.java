package com.org.bankmsuser.repository;

import com.org.bankmsuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User updateUserById(Long id, User user);

    void deleteUserById(Long id);

    User findUserByPassport(String passport);

    List<User> findByRegistrationDateBetween(LocalDate from, LocalDate to);

    Optional<User> findByPhone(String phone);
}
