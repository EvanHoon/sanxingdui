/**
 * Created by IntelliJ IDEA.
 * User: Wenhao.rEN
 * DateTime: 2022/1/24 11:46
 **/
package com.evan.utils;

import com.google.gson.Gson;

/**
 * json转换工具
 */
public class GsonUtils {
    private static final Gson gson = new Gson();

    public static String toJsonString(Object object) {
        return object == null ? null : gson.toJson(object);
    }
}
