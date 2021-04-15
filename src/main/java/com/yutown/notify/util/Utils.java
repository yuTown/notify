package com.yutown.notify.util;

import java.util.Collections;
import java.util.List;

/**
 * @author dong jing xi
 * @date 2020/10/22 1:13
 **/
public class Utils {

    public static List<String> emptyStringListIfNull(List<String> list) {
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        return list;
    }


}
