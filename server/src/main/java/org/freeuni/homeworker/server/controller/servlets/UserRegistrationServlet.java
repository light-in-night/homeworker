package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.Constants;
import org.freeuni.homeworker.server.model.managers.users.UserManager;
import org.freeuni.homeworker.server.model.objects.response.Response;
import org.freeuni.homeworker.server.model.objects.response.ResponseStatus;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/register")
public class UserRegistrationServlet extends HttpServlet {


	private static Logger log = LoggerFactory.getLogger(UserRegistrationServlet.class);

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		resp.setHeader("Content-Type", "application/json");
		resp.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		resp.setHeader("Access-Control-Allow-Methods", "POST");
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(req.getReader());
			while ((line = bufferedReader.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (IOException e) {
			log.error("Error occurred during reading request.", e);
		}

		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(Constants.OBJECT_MAPPER);
		try {
			User user = objectMapper.readValue(stringBuilder.toString(), User.class);
			UserManager userManager = (UserManager) getServletContext().getAttribute(Constants.USER_MANAGER);
			Response response = new Response();
			if (userManager.addUser(user)) {
				response.setStatus(ResponseStatus.OK.name());
				response.setMessage("User was successfully added.");
			} else {
				response.setStatus(ResponseStatus.ERROR.name());
				response.setMessage("Error occurred during user addition. See server log for info.");
			}
			resp.getWriter().write(objectMapper.writeValueAsString(response));
		} catch (IOException e) {
			log.error("Error occurred during reading json value from the string.", e);
		}
	}
}
