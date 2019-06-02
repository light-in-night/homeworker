package org.freeuni.demo.server.database.comment;


import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/commentServlet")
public class commentCreateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String JSONObject = request.getParameter("json");
            ObjectMapper objectMapper = new ObjectMapper();
            commentCreateObject commentCreateObject = objectMapper.readValue(JSONObject, commentCreateObject.class);
            new commentManagerSQL().addComment(commentCreateObject.getid(),commentCreateObject.getPostID(),commentCreateObject.getCommentText());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
