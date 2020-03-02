package com.springboot.usuarios.app.service;

import com.springboot.usuarios.app.model.User;

import java.util.Optional;

public interface IUsersService {

    Iterable<User> findAll();

    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findUserByEmail(String email);
}
