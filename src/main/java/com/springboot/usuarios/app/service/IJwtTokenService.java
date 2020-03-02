package com.springboot.usuarios.app.service;

import com.springboot.usuarios.app.model.User;

public interface IJwtTokenService {

    String generateToken(User user);

}
