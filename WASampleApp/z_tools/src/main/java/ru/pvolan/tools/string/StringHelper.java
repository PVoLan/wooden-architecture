package ru.pvolan.tools.string;

import java.util.List;

public class StringHelper {


    public static boolean isNullOrEmpty(String str) {
        if (str == null) return true;
        return str.isEmpty();
    }

    public static boolean isNullOrEmptyOrWhitespace(String str) {
        if (str == null) return true;
        return str.trim ().isEmpty();
    }


}
