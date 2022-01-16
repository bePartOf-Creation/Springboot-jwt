package com.demo.repositories;

import com.demo.models.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
    /**
     * this method find user by name.
     * @param username
     * @return ApplicationUser.
     */
    ApplicationUser findByUserName(String username);
    boolean existsByUserName(String username);

}
