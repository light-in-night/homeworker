package org.freeuni.homeworker.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.model.SimpleObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/service")
public class OurService extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		Map<String, String[]> map = req.getParameterMap(); // getting parameters from the request
		String firstName;
		String lastName;
		if (map.get("firstName") != null) {
			firstName = map.get("firstName")[0];
		} else {
			firstName = "Guga"; // putting default value into firstName if request contained no other value
		}
		if (map.get("lastName") != null) {
			lastName = map.get("lastName")[0];
		} else {
			lastName = "Tkesheladze";
		}
		SimpleObject simpleObject = new SimpleObject(firstName, lastName); // create simple object that will be transformed into json soon
		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(Constants.OBJECT_MAPPER); // getting object mapper from the servlet context
		String result = objectMapper.writeValueAsString(simpleObject); //  turning object into json string
		resp.setContentType("application/json"); // telling the request that content will be json
		resp.getWriter().write(result); // sen result as json to the caller
	}
}
