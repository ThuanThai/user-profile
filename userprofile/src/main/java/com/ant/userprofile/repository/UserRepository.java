package com.ant.userprofile.repository;

import com.ant.userprofile.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}