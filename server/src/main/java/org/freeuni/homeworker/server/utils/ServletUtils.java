package org.freeuni.homeworker.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class ServletUtils {

    private static final Logger log = LoggerFactory.getLogger(ServletUtils.class);

    public static void setCORSHeaders(HttpServletResponse resp) {
        if (resp.getHeader("Access-Control-Allow-Headers") == null) {
            resp.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, sessionId");
        }
        if (resp.getHeader("Access-Control-Max-Age") == null) {
            resp.addHeader("Access-Control-Max-Age", "60");
        }
        if (resp.getHeader("Access-Control-Allow-Methods") == null) {
            resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        }
        if (resp.getHeader("Access-Control-Allow-Origin") == null) {
            resp.addHeader("Access-Control-Allow-Origin", "*");
        }
    }

    public static void setJSONContentType(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
    }

    public static String readFromRequest(HttpServletRequest request) {
        StringBuilder str = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null){
                str.append(line);
            }
        } catch (Exception e) {
            log.error("Error occurred during reading request content.", e);
        }
        return str.toString();
    }

}
