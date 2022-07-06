package com.maersk.containerservice.service.impl;

import com.maersk.containerservice.exception.ContainerServiceException;
import com.maersk.containerservice.exception.ErrorCodes;
import com.maersk.containerservice.service.ValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Validator;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultValidationService implements ValidationService {

    private final Validator validator;

    public void validate(Object request){
        var errors = validator.validate(request);
        if(errors.size() >0){
            log.error("Validation errors {} for request.", errors.toString(), request.toString());
            throw new ContainerServiceException(HttpStatus.BAD_REQUEST, errors.toString(), ErrorCodes.VALIDATION_ERROR);
        }
    }

}
