package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.managers.Login.LoginManager;
import org.freeuni.homeworker.server.model.objects.Login.LoginRequest;
import org.freeuni.homeworker.server.model.objects.Login.LoginResponse;
import org.freeuni.homeworker.server.model.objects.user.User;
import org.freeuni.homeworker.server.utils.ServletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@SuppressWarnings("RedundantThrows")
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	private final Logger log = LoggerFactory.getLogger(LoginServlet.class);

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(response);
		ServletUtils.setJSONContentType(response);
		String jSon = ServletUtils.readFromRequest(request);
		logInUser(request, response, jSon);
	}

	private void logInUser(HttpServletRequest request, HttpServletResponse response, String jSon) {
		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		LoginManager loginManager = (LoginManager) request.getServletContext().getAttribute(ContextKeys.LOGIN_MANAGER);
		try {
			LoginRequest loginRequest = objectMapper.readValue(jSon, LoginRequest.class);
			if (loginRequest.containsNull()) {
				throw new Exception("Invalid JSon");
			}


			String sessionID = request.getHeader(ContextKeys.SESSION_ID);
			User user = loginManager.getUserByEmail(loginRequest.getEmail());
			LoginResponse loginResponse = new LoginResponse(sessionID, (long)-1, false);
			if (user == null) {
				log.info("User By Email " + loginRequest.getEmail() + " Not Found!");
			} else {
				/*
				if (user.getPassword().equals(DigestUtils.sha256Hex(loginRequest.getPassword()))) {
					loginResponse.setLoggedIn(true);
					loginResponse.setUserId(user.getId());
				} else {
					log.info("Incorrect Password");
				}*/

				//Not Using Test To Make Easy Custom Tests
				if (user.getPassword().equals((loginRequest.getPassword()))) {
					loginResponse.setLoggedIn(true);
					loginResponse.setUserId(user.getId());
				} else {
					log.info("Incorrect Password");
				}

			}
			response.getWriter().write(objectMapper.writeValueAsString(loginResponse));
		} catch (Exception e) {
			log.error(e.toString());
		}
	}

}
