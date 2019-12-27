package com.cogent.admin.constraintvalidator.validator;

import com.cogent.admin.constraintvalidator.DeleteStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static com.cogent.admin.constants.StatusConstants.DELETED;

/**
 * @author smriti on 2019-09-10
 */
public class DeleteStatusValidator implements ConstraintValidator<DeleteStatus, Character> {
    @Override
    public boolean isValid(Character status, ConstraintValidatorContext context) {
        if (Objects.isNull(status)) return false;
        return status.equals(DELETED);
    }
}
