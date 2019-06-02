package postEdit;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "postEditServlet")
public class postEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // JSON string is given through request by name json
        String JSONObject = request.getParameter("json") ;
        //mapper to treat JSON
        ObjectMapper mapper = new ObjectMapper();
        //turns json into java object
        postEditObject obj = mapper.readValue(JSONObject, postEditObject.class);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
