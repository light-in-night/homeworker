package org.freeuni.homeworker.server.controller.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.freeuni.homeworker.server.controller.listeners.ContextKeys;
import org.freeuni.homeworker.server.utils.JacksonUtils;
import org.freeuni.homeworker.server.utils.ServletUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author : Guram Tkesheladze
 * Tested via : SoapUI
 */
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
		ObjectMapper objectMapper = (ObjectMapper) request.getServletContext().getAttribute(ContextKeys.OBJECT_MAPPER);
		ObjectNode returnNode = objectMapper.createObjectNode();
		JacksonUtils.addStatusError(returnNode, "That request is illegal.");
		response.getWriter().write(returnNode.toString());
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletUtils.setCORSHeaders(resp);
		resp.setStatus(200);
	}
}
