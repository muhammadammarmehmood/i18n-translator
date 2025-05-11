package com.i18n.translator.Util;

import java.util.List;

public class ListUtil {
    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }
}
