package org.freeuni.homeworker.server.controller.servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.PrintWriter;

public class Utilities {
    public static HttpServletResponse setCORSHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        return response;
    }

    public static HttpServletResponse setJSONContentType(HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        return response;
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
            e.printStackTrace();
        }
        return str.toString();
    }
}
