package com.cogent.admin.constraintvalidator.validator;

import com.cogent.admin.constraintvalidator.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

import static com.cogent.admin.utils.PatternUtils.isStatusActiveOrInactive;

/**
 * @author smriti on 2019-09-10
 */
public class StatusValidator implements ConstraintValidator<Status, Character> {

    @Override
    public boolean isValid(Character status, ConstraintValidatorContext context) {
        if (Objects.isNull(status)) return false;
        return isStatusActiveOrInactive(status);
    }
}
