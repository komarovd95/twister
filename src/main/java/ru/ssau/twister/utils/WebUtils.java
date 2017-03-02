package ru.ssau.twister.utils;

import java.util.HashMap;
import java.util.Map;

public final class WebUtils {
    private WebUtils() {}

    public static Map<String, Object> parseQueryString(String queryString) {
        Map<String, Object> queryParametersMap = new HashMap<>();

        String[] queryParameters = queryString.split("&");

        for (String queryParameter : queryParameters) {
            String[] parameterPair = queryParameter.split("=");

            if (parameterPair.length == 1) {
                queryParametersMap.put(parameterPair[0], "");
            } else if (parameterPair.length == 2) {
                queryParametersMap.put(parameterPair[0], (parameterPair[1].indexOf(',') == -1) ? parameterPair[1] :
                        parameterPair[1].split(","));
            }
        }

        return queryParametersMap;
    }
}
