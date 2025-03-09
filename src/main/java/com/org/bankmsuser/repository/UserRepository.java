package com.org.bankmsuser.repository;

import com.org.bankmsuser.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findUserByPassport(String passport);
    User findUserByPhoneNumber(String phoneNumber);

    List<User> findUsersByCreatedDateBetween(LocalDateTime from, LocalDateTime to);

    List<User> findUsersByPassportContaining(String passportMask);
    List<User> findUsersByPhoneNumberContaining(String phoneMask);


}
