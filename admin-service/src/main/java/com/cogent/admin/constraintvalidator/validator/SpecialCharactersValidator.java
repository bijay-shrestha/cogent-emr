package com.cogent.admin.constraintvalidator.validator;

import com.cogent.admin.constraintvalidator.SpecialCharacters;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Objects;

import static com.cogent.admin.utils.PatternUtils.hasSpecialCharacter;

/**
 * @author smriti on 2019-09-15
 */
public class SpecialCharactersValidator implements ConstraintValidator<SpecialCharacters, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || !hasSpecialCharacter(value);
    }
}
