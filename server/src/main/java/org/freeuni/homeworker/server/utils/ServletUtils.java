package org.freeuni.homeworker.server.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;

public class ServletUtils {

    private static final Logger log = LoggerFactory.getLogger(ServletUtils.class);

    public static void setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE");
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
