package com.maersk.containerservice.validation;

import com.maersk.containerservice.model.ContainerRequest;
import com.maersk.containerservice.model.ContainerType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ContainerRequestValidator implements ConstraintValidator<ValidateContainerRequest, ContainerRequest> {


    @Override
    public void initialize (ValidateContainerRequest constraintAnnotation){

    }
    @Override
    public boolean isValid(ContainerRequest containerRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (containerRequest.getQuantity()>100) {
            return false;
        }
        if (!isValidContainer(containerRequest)) {
            return false;
        };
        return true;
    }


    private boolean isValidContainer(ContainerRequest request) {
        return  request.getContainerType().equals(ContainerType.REEFER.name())
                || request.getContainerType().equals(ContainerType.DRY.name());
    }
}
