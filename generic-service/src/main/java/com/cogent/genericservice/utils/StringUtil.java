package com.cogent.genericservice.utils;

import org.apache.commons.lang.StringUtils;

/**
 * @author smriti on 2019-08-27
 */
public class StringUtil {

    public static String splitByCharacterTypeCamelCase(String name) {
        return StringUtils.join(StringUtils.splitByCharacterTypeCamelCase
                (name.replaceAll("\\d+", "")), " ");
    }
}
