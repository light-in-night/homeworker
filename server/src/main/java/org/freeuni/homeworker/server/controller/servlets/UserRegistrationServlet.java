package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
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

@WebServlet(urlPatterns = "/register")
public class UserRegistrationServlet extends HttpServlet {


	private static Logger log = LoggerFactory.getLogger(UserRegistrationServlet.class);

	/**
	 * Creates a new user.
	 * Expects a JSON of this format:
	 * {
	 * 	"first_name" : "tornike",
	 * 	"last_name" : "onoprishvili",
	 * 	"gender" : "male",
	 * 	"email" : "tomo@gmail.com",
	 *  "password" : "something"
	 * }
     *
     * returns a resulting JSON that
     * shows if user was added successfully or not.
     * the response will have following structure
     *
     * {
     *     "status" : ERROR (or OK)
     *     "message" : "user with that email already exists"
     * }
	 *
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		Utilities.setCORSHeaders(resp);
		Utilities.setJSONContentType(resp);
		String jsonString = Utilities.readFromRequest(req);
		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		try {
			User user = objectMapper.readValue(jsonString, User.class);
			UserManager userManager = (UserManager) getServletContext().getAttribute(ContextKeys.USER_MANAGER);
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
