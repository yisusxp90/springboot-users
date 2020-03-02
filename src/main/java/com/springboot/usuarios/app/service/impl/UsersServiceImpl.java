package com.springboot.usuarios.app.service.impl;

import com.springboot.usuarios.app.model.User;
import com.springboot.usuarios.app.repository.IUsersRepository;
import com.springboot.usuarios.app.service.IJwtTokenService;
import com.springboot.usuarios.app.service.IUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsersServiceImpl implements IUsersService {

    private final Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    private IUsersRepository iUsersRepository;
    @Autowired
    private IJwtTokenService jwtToken;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public Iterable<User> findAll() {
        logger.info("------------Getting all users---------------");
        return iUsersRepository.findAll();
    }

    @Override
    @Transactional
    public User save(User user) {
        logger.info("------------Save or update user---------------");
        final String token = jwtToken.generateToken(user);
        user.setToken(token);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        logger.info("------------Finish Save or update user--------");
        return iUsersRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        logger.info("finding users with id: ", id);
        return iUsersRepository.findById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return iUsersRepository.findUserByEmail(email);
    }
}
