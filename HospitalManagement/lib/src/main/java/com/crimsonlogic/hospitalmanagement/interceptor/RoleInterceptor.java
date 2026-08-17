package com.crimsonlogic.hospitalmanagement.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {

        HttpSession session =
                request.getSession(false);

        String role = null;

        if (session != null) {
            role = (String) session.getAttribute("role");
        }

        String uri =
                request.getRequestURI();

        String contextPath =
                request.getContextPath();

        String path =
                uri.substring(contextPath.length());


        // =====================================================
        // NOT LOGGED IN
        // =====================================================

        if (role == null) {

            response.sendRedirect(
                    contextPath + "/login");

            return false;
        }


        role = role.toUpperCase();


        // =====================================================
        // ADMIN
        // ADMIN HAS FULL ACCESS
        // =====================================================

        if (role.equals("ADMIN")) {
            return true;
        }


        // =====================================================
        // DOCTOR AREA
        // =====================================================

        if (path.startsWith("/doctor/")) {

            if (!role.equals("DOCTOR")) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Access denied");

                return false;
            }
        }


        // =====================================================
        // NURSE AREA
        // =====================================================

        if (path.startsWith("/nurse/")) {

            if (!role.equals("NURSE")) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Access denied");

                return false;
            }
        }


        // =====================================================
        // PATIENT AREA
        // =====================================================

        if (path.startsWith("/patient/")) {

            if (!role.equals("PATIENT")) {

                response.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Access denied");

                return false;
            }
        }


        return true;
    }
}