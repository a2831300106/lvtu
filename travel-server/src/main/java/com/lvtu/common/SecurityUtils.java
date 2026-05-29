package com.lvtu.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SecurityUtils {

    public static Long getCurrentUserId() {
        HttpServletRequest request = getRequest();
        if (request == null) return null;
        Object userId = request.getAttribute("userId");
        return userId instanceof Long ? (Long) userId : null;
    }

    public static String getCurrentRole() {
        HttpServletRequest request = getRequest();
        if (request == null) return null;
        Object role = request.getAttribute("role");
        return role instanceof String ? (String) role : null;
    }

    private static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return null;
        return attrs.getRequest();
    }
}
