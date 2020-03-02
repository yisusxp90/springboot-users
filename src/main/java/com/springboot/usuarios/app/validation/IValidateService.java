package com.springboot.usuarios.app.validation;

import org.springframework.validation.BindingResult;

import java.util.Map;

public interface IValidateService {

    Map<String, Object> validateUpdate(Map<String, Object> errors, BindingResult result);

    Map<String, Object> validateCreate(Map<String, Object> errors, BindingResult result);
}
