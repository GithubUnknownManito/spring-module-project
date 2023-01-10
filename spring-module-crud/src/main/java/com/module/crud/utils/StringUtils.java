package com.module.crud.utils;

public class StringUtils {


    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static void requiredIsNotBlank(CharSequence cs, String format, Object... args){
        if(isBlank(cs)){
            throw new NullPointerException(String.format(format, args));
        }
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        } else {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        }
    }
}
