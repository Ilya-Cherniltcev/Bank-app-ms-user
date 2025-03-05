package com.org.bankmsuser.repository;

import com.org.bankmsuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User updateUserById(Long id, User user);

    void deleteUserById(Long id);

    User findUserByPassport(String passport);
}
