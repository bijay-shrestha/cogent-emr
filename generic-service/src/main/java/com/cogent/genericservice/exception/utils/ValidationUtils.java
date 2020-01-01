package com.cogent.genericservice.exception.utils;

import com.cogent.genericservice.exception.ConstraintViolationException;

import javax.validation.ConstraintViolation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cogent.genericservice.constants.StringConstant.COMMA_SEPARATED;
import static com.cogent.genericservice.constants.StringConstant.SPACE;
import static com.cogent.genericservice.utils.StringUtil.splitByCharacterTypeCamelCase;
import static org.springframework.util.StringUtils.capitalize;

/**
 * @author smriti on 2019-09-10
 */
public class ValidationUtils {

    public static String getExceptionForMethodArgumentNotValid(
            org.springframework.web.bind.MethodArgumentNotValidException ex) {

        List<String> violations = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> {
                            return capitalize(splitByCharacterTypeCamelCase(
                                    error.getField())) + SPACE + error.getDefaultMessage();
                        }
                ).collect(Collectors.toList());

        return String.join(COMMA_SEPARATED + SPACE, violations);
    }

    /*
    ConstraintViolationException to be thrown when validation on an argument annotated with {@code @Valid}
   (request dtos) fails on Service layer
  */
    public static <T> void validateConstraintViolation(Set<ConstraintViolation<T>> constraintViolations) {
        if (!constraintViolations.isEmpty()) {
            List<String> violations = constraintViolations.stream().map(
                    violation -> capitalize(splitByCharacterTypeCamelCase(
                            violation.getPropertyPath().toString())) + SPACE + violation.getMessage()
            ).collect(Collectors.toList());

            String errorMessages = String.join(COMMA_SEPARATED + SPACE, violations);

            throw new ConstraintViolationException(errorMessages, errorMessages);
        }
    }
}
