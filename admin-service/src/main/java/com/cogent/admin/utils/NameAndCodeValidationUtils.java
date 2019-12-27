package com.cogent.admin.utils;

import com.cogent.admin.exception.DataDuplicationException;

import java.util.List;

import static com.cogent.admin.constants.ErrorMessageConstants.*;
import static java.lang.reflect.Array.get;

public class NameAndCodeValidationUtils {
    public static void validatetDuplicityByNameOrCode(List<Object[]> objects,
                                                      String requestName,
                                                      String requestCode,Class clazz) {
        final int NAME = 0;
        final int CODE = 1;

        objects.forEach(object -> {
            boolean isNameExists = requestName.equalsIgnoreCase((String) get(object, NAME));

            boolean isCodeExists = requestCode.equalsIgnoreCase((String) get(object, CODE));

            if (isNameExists && isCodeExists)
                throw new DataDuplicationException(clazz,NAME_AND_CODE_DUPLICATION_MESSAGE,
                        NAME_AND_CODE_DUPLICATION_DEBUG_MESSAGE);

            validateName(isNameExists, requestName,clazz);
            validateCode(isCodeExists, requestCode,clazz);
        });
    }

    private static void validateName(boolean isNameExists, String name,Class clazz) {
        if (isNameExists)
            throw new DataDuplicationException(clazz,NAME_DUPLICATION_MESSAGE + name,
                    NAME_DUPLICATION_DEBUG_MESSAGE + name);
    }

    private static void validateCode(boolean isCodeExists, String code,Class clazz) {
        if (isCodeExists)
            throw new DataDuplicationException(clazz,CODE_DUPLICATION_MESSAGE + code,
                    CODE_DUPLICATION_DEBUG_MESSAGE + code);
    }
}
