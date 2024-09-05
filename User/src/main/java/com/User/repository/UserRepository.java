package com.User.repository;

import com.User.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByClientId(String clientId);

    User findByEmail(String email);
}
