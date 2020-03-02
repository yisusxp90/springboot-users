package com.springboot.usuarios.app.validation.impl;

import com.springboot.usuarios.app.validation.IValidateService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ValidateServiceImpl implements IValidateService {

    @Override
    public Map<String, Object> validateUpdate(Map<String, Object> errors, BindingResult result) {
        List<FieldError> errorsToKeep = result.getFieldErrors().stream()
                .filter(fieldError -> !Objects.equals(fieldError.getCode(), "Unique"))
                .collect(Collectors.toList());
        errorsToKeep.forEach(error -> {
            errors.put(error.getField(), error.getField() + " " + error.getDefaultMessage());
        });

        return errors;
    }

    @Override
    public Map<String, Object> validateCreate(Map<String, Object> errors, BindingResult result) {
        result.getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getField() + " " + error.getDefaultMessage());
        });
        return errors;
    }
}
