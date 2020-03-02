package com.springboot.usuarios.app.annotation;

import com.springboot.usuarios.app.service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserUniqueValidator implements ConstraintValidator<Unique,String> {

    @Autowired
    private IUsersService iUsersService;

    @Override
    public void initialize(Unique unique) {
        unique.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if(iUsersService != null && iUsersService.findUserByEmail(email).isPresent()) {
            return false;
        }
        return true;
    }
}