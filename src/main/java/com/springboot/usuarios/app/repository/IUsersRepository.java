package com.springboot.usuarios.app.repository;

import com.springboot.usuarios.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);
}
