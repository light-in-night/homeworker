package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.model.objects.IllegalRequest.IllegalRequest;
import org.freeuni.homeworker.server.model.objects.IllegalRequest.IllegalRequestTypes;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("RedundantThrows")
@WebServlet(name = "IllegalRequestServlet", urlPatterns = {"/illegalRequest"})
public class IllegalRequestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		response(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		response(req, resp);
	}

	private void response(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ServletUtils.setCORSHeaders(response);
		ServletUtils.setJSONContentType(response);
		IllegalRequest illegalRequest = new IllegalRequest();
		illegalRequest.setType(IllegalRequestTypes.ILLEGAL_REQUEST);
		ObjectMapper objectMapper = (ObjectMapper) getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		response.getWriter().write(objectMapper.writeValueAsString(illegalRequest));
	}
}
