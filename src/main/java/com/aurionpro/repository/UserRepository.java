package com.aurionpro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import com.aurionpro.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);

    @Query("select u from User u left join fetch u.roles where u.email = :email")
    Optional<User> findByEmailWithRoles(@Param("email") String email);
}
